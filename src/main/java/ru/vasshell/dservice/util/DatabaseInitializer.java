package ru.vasshell.dservice.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Objects;

public class DatabaseInitializer {
    public static void verifyDbAndSchema(String url, String username, String password) {
        ParsedUrl parsedUrl = parseUrl(url);
        try (Connection connection = DriverManager.getConnection(parsedUrl.baseUrl(), username, password)) {
            verifyDb(parsedUrl.dbName(), connection);
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        verifySchema(parsedUrl, username, password);
    }

    private static void verifySchema(ParsedUrl url, String username, String password) {
        String dbUrl = url.baseUrl() + (url.dbName() == null ? "" : url.dbName());
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password); 
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS %s".formatted(url.schemaName()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void verifyDb(String dbName, Connection connection){
        try(ResultSet catalogs = connection.getMetaData().getCatalogs();
            Statement statement = connection.createStatement()) {
            if (dbName!= null && !dbInCatalogs(catalogs, dbName)) {
                statement.executeUpdate("CREATE DATABASE %s".formatted(dbName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean dbInCatalogs(ResultSet catalogs, String dbName) throws SQLException {
        while (catalogs.next()){
            if (Objects.equals(catalogs.getString(1), dbName)) return true;
        }
        return false;
    }

    private record ParsedUrl(String baseUrl, String dbName, String schemaName) { }
    private static ParsedUrl parseUrl(String url){
        URI uri;
        try {
            uri = new URI(url.substring(5));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String baseUrl = "jdbc:" + uri.getScheme() + "://" + uri.getAuthority() + "/";
        String path = uri.getPath();
        String dbName = (path != null && path.length() > 1) ? path.substring(1) : null;
        String schemaName = null;
        String query = uri.getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=", 2);
                if (pair.length == 2 && pair[0].equals("currentSchema")) {
                    schemaName = pair[1];
                    break;
                }
            }
        }
        return new ParsedUrl(baseUrl, dbName, schemaName);
    }

}
