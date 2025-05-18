package proj.dtos;

public class ApiResponse<T> {
    public T data;
    public String message;
    public String code;

    public ApiResponse(T data, String message, String code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }
}

