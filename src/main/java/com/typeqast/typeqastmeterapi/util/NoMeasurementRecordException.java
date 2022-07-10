package com.typeqast.typeqastmeterapi.util;

/**
 * Custom exception for no record to remove or update.
 */
public class NoMeasurementRecordException extends RuntimeException{
  /**
   * Instantiates a new No measurement record exception.
   *
   * @param errorMessage the error message
   * @param err          the error
   */
  public NoMeasurementRecordException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
