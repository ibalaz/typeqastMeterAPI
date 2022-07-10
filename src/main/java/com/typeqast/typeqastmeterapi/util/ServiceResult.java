package com.typeqast.typeqastmeterapi.util;

import java.util.List;


/**
 * Helper class to keep service responses consistent.
 */
public class ServiceResult {
  /**
   * The Success.
   */
  public boolean success;
  /**
   * The Error message list.
   */
  public List<String> errorMessageList;
  /**
   * The Result.
   */
  public Object result;
}
