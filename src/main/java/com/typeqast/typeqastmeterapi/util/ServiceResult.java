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

  /**
   * Valid ServiceResult builder, for positive responses.
   *
   * @param result {Object} - data object with response from database or message
   * @return {ServiceResult} - positive ServiceResult status
   */
  public static ServiceResult buildValidResult(Object result) {
    return new ServiceResult(result, null);
  }

  /**
   * Error ServiceResult builder, for error handling.
   *
   * @param errorMessages {List} - list of error messages and exceptions
   * @return {ServiceResult} - error ServiceResult status
   */
  public static ServiceResult buildErrorResult(List<String> errorMessages) {
    return new ServiceResult(null, errorMessages);
  }

  /**
   * Success status.
   */
  public boolean success;

  /**
   * List of error messages.
   */
  public List<String> errorMessages;

  /**
   * Object with results
   */
  public Object result;
}
