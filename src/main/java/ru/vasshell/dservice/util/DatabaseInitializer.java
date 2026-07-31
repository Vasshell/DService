package ru.vasshell.dservice.util;

import java.sql.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseInitializer {
    public static void verifyDb(String url, String username, String password) {
        Pattern pattern = Pattern.compile("^([^/]*/+[^/]+/)([^?]+)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            String cleanUrl = matcher.group(1);
            String dbName = matcher.group(2);
            try (Connection connection = DriverManager.getConnection(cleanUrl, username, password);
                 ResultSet catalogs = connection.getMetaData().getCatalogs();
                 Statement statement = connection.createStatement()){
                if (!dbInCatalogs(catalogs, dbName)) statement.executeUpdate("CREATE DATABASE %s".formatted(dbName));
                statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS service");
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        } else throw new IllegalArgumentException("Incorrect database URL");
    }

    private static boolean dbInCatalogs(ResultSet catalogs, String dbName) throws SQLException {
        while (catalogs.next()){
            if (Objects.equals(catalogs.getString(1), dbName)) return true;
        }
        return false;
    }
}
