package com.bezkoder.springjwt.payload.response;

public class MesgResponse {
  private String message;

  public MesgResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
