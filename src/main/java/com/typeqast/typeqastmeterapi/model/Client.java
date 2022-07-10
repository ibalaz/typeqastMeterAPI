package com.typeqast.typeqastmeterapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Client model
 */
@Data
@AllArgsConstructor
public class Client {
  private String name;
  private String streetName;
  private String city;
  private int postCode;
  private String meterId;
}
