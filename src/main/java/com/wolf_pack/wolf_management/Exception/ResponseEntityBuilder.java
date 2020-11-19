package com.wolf_pack.wolf_management.Exception;

import org.springframework.http.ResponseEntity;

//Entity builder
public class ResponseEntityBuilder {

    public static ResponseEntity<Object> build(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }

}
