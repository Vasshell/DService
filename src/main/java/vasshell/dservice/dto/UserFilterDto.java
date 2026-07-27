package vasshell.dservice.dto;

public record UserFilterDto(
        Integer pageSize,
        Integer pageNum,
        String firstName,
        String lastName,
        Integer age,
        Integer ageGreaterThan,
        Integer ageLessThan
) {
    public UserFilterDto {
        if (pageSize == null){
            pageSize = 5;
        }
        if (pageNum == null){
            pageNum = 0;
        }
    }
}
