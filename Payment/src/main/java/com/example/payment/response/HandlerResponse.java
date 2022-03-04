package com.example.payment.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class HandlerResponse {
    private int status;
    private String message;
    private HashMap<String,Object> body;

    public HandlerResponse(){}

    public HandlerResponse(int status, String message, HashMap<String, Object> data) {
        this.status = status;
        this.message = message;
        this.body = data;
    }

    public static final class HandlerResponseBuilder {
        private int status;
        private String message;
        private HashMap<String,Object> data;

        private HandlerResponseBuilder() {
        }

        public static HandlerResponseBuilder aHandlerResponse() {
            return new HandlerResponseBuilder();
        }

        public HandlerResponseBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public HandlerResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public HandlerResponseBuilder withData(HashMap<String, Object> data) {
            this.data = data;
            return this;
        }
        public HandlerResponseBuilder addData(String key,Object value){
            if(this.data == null){
                this.data = new HashMap<>();
            }
            this.data.put(key,value);
            return this;
        }

        public HandlerResponse build() {
            HandlerResponse handlerResponse = new HandlerResponse();
            handlerResponse.setStatus(status);
            handlerResponse.setMessage(message);
            handlerResponse.setBody(data);
            return handlerResponse;
        }
    }
}
