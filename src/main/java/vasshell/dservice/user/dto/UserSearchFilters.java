package vasshell.dservice.user.dto;

import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.BindParam;
import vasshell.dservice.user.User;

public record UserSearchFilters(
        @BindParam("first_name") String firstName,
        @BindParam("last_name") String lastName,
        @BindParam("age") Integer age,
        @BindParam("age_gt") Integer ageGreaterThan,
        @BindParam("age_lt") Integer ageLessThan
) {
    public Specification<User> toSpecification(){
        Specification<User> spec = Specification.unrestricted();

        if (this.firstName != null){
            spec = spec.and(UserSpecification.likeFirstName(this.firstName));
        }
        if (this.lastName != null){
            spec = spec.and(UserSpecification.likeLastName(this.lastName));
        }
        if (this.age != null){
            spec = spec.and(UserSpecification.hasAge(this.age));
        }
        if (this.ageGreaterThan!=null){
            spec = spec.and(UserSpecification.hasAgeGreaterThan(this.ageGreaterThan));
        }
        if (this.ageLessThan!=null){
            spec = spec.and(UserSpecification.hasAgeLessThan(this.ageLessThan));
        }
        return spec;
    }
}

class UserSpecification {

    public static PredicateSpecification<User> likeFirstName(String firstName){
        return funnyThing(firstName, "firstName");
    }

    public static PredicateSpecification<User> likeLastName(String lastName){
        return funnyThing(lastName, "lastName");
    }

    private static PredicateSpecification<User> funnyThing(String compareTo, String propertyName){
        return (root, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(propertyName)), "%" + compareTo.toLowerCase() + "%");
    }

    public static PredicateSpecification<User> hasAge(Integer age) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.equal(from.get("age"), age);
    }

    public static PredicateSpecification<User> hasAgeGreaterThan(Integer ageGt) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.greaterThan(from.get("age"), ageGt);
    }

    public static PredicateSpecification<User> hasAgeLessThan(Integer ageLt) {
        return (from, criteriaBuilder) ->
                criteriaBuilder.lessThan(from.get("age"), ageLt);
    }
}
