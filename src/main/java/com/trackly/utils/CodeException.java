package com.trackly.utils;

import com.trackly.enums.RespCode;

public class CodeException extends RuntimeException {
  private final RespCode code;

  public CodeException(RespCode code) {
    this.code = code;
  }

  public RespCode getCode() { return this.code; }
}
