package com.trackly.enums;

public enum RespCode {
  OK(200),
  CREATED(201),
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
  NOT_FOUND(404),
  CONFLICT(409),
  INTERNAL_SERVER_ERROR(500);

  private final int code;

  RespCode(int code) { this.code = code; }

  public int getCode() { return code; }
}
