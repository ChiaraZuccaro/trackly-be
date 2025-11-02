package com.trackly.utils;

import org.springframework.http.ResponseEntity;

import com.trackly.enums.RespCode;

public class ApiResp<T> {

  private final int code;
  private final String message;
  private final boolean error;
  private final T data;

  public ApiResp(RespCode code, String message, boolean error, T data) {
    this.code = code.getCode();
    this.message = message;
    this.error = error;
    this.data = data;
  }

  public ApiResp(RespCode code, String message) {
    this(code, message, false, null);
  }

  public ApiResp(RespCode code, String message, boolean error) {
    this(code, message, error, null);
  }

  public ResponseEntity<ApiResp<T>> send() {
    return ResponseEntity.status(code).body(this); // status numerico
  }

  public int getCode() { return code; }  // ora restituisce int
  public String getMessage() { return message; }
  public boolean isError() { return error; }
  public T getData() { return data; }
}
