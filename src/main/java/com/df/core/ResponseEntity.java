package com.df.core;

/**
 * @author MFine
 * @version 1.0
 * @date 2021/11/18 23:20
 **/
public class ResponseEntity<T> {

    private T body;
    private String statusCode;

    public ResponseEntity(T body, String statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public static BodyBuilder status(String status) {
        return new DefaultBuilder(status);
    }

    public static BodyBuilder ok() {
        return status("200");
    }

    public interface BodyBuilder {

        <T> ResponseEntity<T> body(T body);
    }


    private static class DefaultBuilder implements BodyBuilder {

        private String statusCode;

        public DefaultBuilder(String code) {
            this.statusCode = code;
        }


        @Override
        public <T> ResponseEntity<T> body(T body) {
            return new ResponseEntity<>(body, this.statusCode);
        }
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
