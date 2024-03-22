package com.ssafy.kkoma.global.util;

import com.ssafy.kkoma.global.error.ErrorResponse;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, data, null);
    }

    public static ApiResult<?> error(ErrorResponse errorResponse) {
        return new ApiResult<>(false, null, errorResponse);
    }

    public static class ApiResult<T> {
        private final boolean success;
        private final T data;
        private final ErrorResponse error;

        private ApiResult(boolean success, T data, ErrorResponse error) {
            this.success = success;
            this.data = data;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }

        public ErrorResponse getError() {
            return error;
        }

        public T getData() {
            return data;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("success", success)
                    .append("data", data)
                    .append("error", error)
                    .toString();
        }
    }

}
