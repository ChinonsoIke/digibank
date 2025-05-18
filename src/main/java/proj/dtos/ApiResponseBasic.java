package proj.dtos;

public class ApiResponseBasic {
    public String message;
    public String code;

    public ApiResponseBasic(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
