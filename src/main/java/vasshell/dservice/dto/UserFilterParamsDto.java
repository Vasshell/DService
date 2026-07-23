package vasshell.dservice.dto;

import org.springframework.web.bind.annotation.BindParam;

public record UserFilterParamsDto(
        @BindParam("page_size") Integer pageSize,
        @BindParam("page_num") Integer pageNum,
        @BindParam("first_name") String firstName,
        @BindParam("last_name") String lastName,
        @BindParam("age") Integer age,
        @BindParam("age_gt") Integer ageGreaterThan,
        @BindParam("age_lt") Integer ageLessThan
) {
    public UserFilterParamsDto{
        if (pageSize == null){
            pageSize = 5;
        }
        if (pageNum == null){
            pageNum = 0;
        }
    }
}
