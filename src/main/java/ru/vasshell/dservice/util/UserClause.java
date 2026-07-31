package ru.vasshell.dservice.util;

import lombok.Getter;
import org.springframework.data.domain.Sort;
import ru.vasshell.dservice.dto.UserFilterDto;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Getter
public class UserClause {
    private final String clause;
    private final List<Object> args;

    private UserClause(String clause, List<Object> args){
        this.clause = clause;
        this.args = args;
    }
    public static UserClause getFilteringClause(UserFilterDto filters){
        if (filters.isNotFiltered()){
            return new UserClause("", List.of());
        }
        StringJoiner clause = new StringJoiner("\nAND ", "WHERE ", "");
        List<Object> args = new ArrayList<>();
        if (filters.getFirstName() != null) {
            clause.add("first_name = ?");
            args.add(filters.getFirstName());
        }
        if (filters.getLastName() != null) {
            clause.add("last_name = ?");
            args.add(filters.getLastName());
        }
        if (filters.getAge() != null) {
            clause.add("age = ?");
            args.add(filters.getAge());
        }
        if (filters.getAgeGreaterThan() != null) {
            clause.add("age > ?");
            args.add(filters.getAgeGreaterThan());
        }
        if (filters.getAgeLessThan() != null) {
            clause.add("age < ?");
            args.add(filters.getAgeLessThan());
        }

        return new UserClause(clause.toString(), args);
    }

    public static String getSortingClause(Sort sort) {
        if (sort.isUnsorted()){
            return "ORDER BY id ASC";
        }
        StringJoiner clause = new StringJoiner(", ", "ORDER BY ", "");
        for (Sort.Order order : sort) {
            if (verifySortingOrder(order)) clause.add(order.getProperty() + " " + order.getDirection());
        }
        return clause.toString();
    }

    private static boolean verifySortingOrder(Sort.Order order){
        String property = order.getProperty();
        return property.equalsIgnoreCase("id") ||
                property.equalsIgnoreCase("first_name") ||
                property.equalsIgnoreCase("last_name") ||
                property.equalsIgnoreCase("age");
    }
}
