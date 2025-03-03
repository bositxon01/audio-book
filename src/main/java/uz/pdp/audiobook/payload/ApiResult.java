package uz.pdp.audiobook.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private boolean success;

    private String message;

    private T data;

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, null, data);
    }

    public static <T> ApiResult<T> success(String message) {
        return new ApiResult<>(true, message, null);
    }

    public static <T> ApiResult<T> success(String message, T data) {
        return new ApiResult<>(true, message, data);
    }

    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(false, message, null);
    }

    public static <T> ApiResult<T> error(String message, T data) {
        return new ApiResult<>(false, message, data);
    }

}
