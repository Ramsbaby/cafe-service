package com.kakaopay.cafeservice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Getter
public class ErrorResponse {

    private final int code;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, Object> data;

    private final String msg;

    private ErrorResponse(Builder builder) {

        this.code = builder.code;
        this.data = builder.data;
        this.msg = builder.msg;
    }

    public static class Builder {

        private int code;
        private Map<String, Object> data;
        private String msg;

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setData(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }

        public Builder setFieldErrorData(List<ObjectError> objectErrorList) {
            List<Map<String, String>> messageErrorList = new ArrayList<>();

            if (objectErrorList != null && !objectErrorList.isEmpty()) {
                messageErrorList.addAll(objectErrorList.stream()
                    .map(this::getFieldErrorMapData)
                    .collect(Collectors.toList()));
            }

            Map<String, Object> fieldErrorData = new LinkedHashMap<>();

            fieldErrorData.put("errors", messageErrorList);

            this.data = fieldErrorData;

            return this;
        }

        private Map<String, String> getFieldErrorMapData(ObjectError error) {

            Map<String, String> data = new LinkedHashMap<>();

            data.put("field", ((FieldError) error).getField());
            data.put("message", error.getDefaultMessage());

            return data;
        }
    }
}

