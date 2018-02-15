package io.sixhours.rx.server;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private final int statusCode;
    private final Map<String, String> headers;
    private byte[] body;

    private Response(Integer statusCode, Map<String, String> headers, byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public static class Builder {
        private Integer statusCode;
        private Map<String, String> headers = new HashMap<>();
        private byte[] body;

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder header(String name, String value) {
            this.headers.put(name, value);
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public Response build() {
            if (statusCode == null) {
                statusCode = 200;
            }
            if (body != null) {
                headers.put("Content-Length", String.valueOf(body.length));
            } else {
                headers.put("Content-Length", "0");
            }
            return new Response(statusCode, headers, body);
        }
    }
}
