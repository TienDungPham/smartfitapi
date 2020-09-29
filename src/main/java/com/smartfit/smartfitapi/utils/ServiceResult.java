package com.smartfit.smartfitapi.utils;

import lombok.Data;

@Data
public class ServiceResult<T> {
    private ResultCode code;
    private T data;

    public enum ResultCode {
        SUCCESS,
        FAIL,
        AUTHENTICATED,
        UNAUTHENTICATED,
        CONFLICT,
        NOTFOUND,
        UNKNOWN
    }
}
