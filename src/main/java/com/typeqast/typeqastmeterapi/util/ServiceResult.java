package com.typeqast.typeqastmeterapi.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


/**
 * Helper class to keep service responses consistent.
 */
@Getter
public class ServiceResult {
  private ServiceResult (Object result, List<String> errorMessages) {
    if (errorMessages == null ||errorMessages.isEmpty()) {
      this.errorMessages = new ArrayList<>();
      this.success = true;
      this.result = result;
    } else {
      this.errorMessages = errorMessages;
      this.success = false;
      this.result = null;
    }
  }

  public static ServiceResult buildValidResult(Object result) {
    return new ServiceResult(result, null);
  }

  public static ServiceResult buildErrorResult(List<String> errorMessages) {
    return new ServiceResult(null, errorMessages);
  }

  public boolean success;

  public List<String> errorMessages;

  public Object result;
}
