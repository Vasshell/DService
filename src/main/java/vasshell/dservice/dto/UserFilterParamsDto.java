package vasshell.dservice.dto;

import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.BindParam;
import vasshell.dservice.entity.User;

public record UserFilterParamsDto(
        @BindParam("first_name") String firstName,
        @BindParam("last_name") String lastName,
        @BindParam("age") Integer age,
        @BindParam("age_gt") Integer ageGreaterThan,
        @BindParam("age_lt") Integer ageLessThan
) { }
