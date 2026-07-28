package ru.vasshell.dservice.util;

import org.springframework.data.jpa.domain.PredicateSpecification;
import ru.vasshell.dservice.entity.User;
import ru.vasshell.dservice.entity.User_;

public class UserSpecification {

    public static PredicateSpecification<User> likeFirstName(String firstName){
        return likeName(firstName, User_.FIRST_NAME);
    }

    public static PredicateSpecification<User> likeLastName(String lastName){
        return likeName(lastName, User_.LAST_NAME);
    }

    private static PredicateSpecification<User> likeName(String compareTo, String propertyName){
        return (root, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(propertyName)), "%" + compareTo.toLowerCase() + "%");
    }

    public static PredicateSpecification<User> hasAge(Integer age) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.equal(from.get(User_.AGE), age);
    }

    public static PredicateSpecification<User> hasAgeGreaterThan(Integer ageGt) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.greaterThan(from.get(User_.AGE), ageGt);
    }

    public static PredicateSpecification<User> hasAgeLessThan(Integer ageLt) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.lessThan(from.get(User_.AGE), ageLt);
    }
}