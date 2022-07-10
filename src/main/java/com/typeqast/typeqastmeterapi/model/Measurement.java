package com.typeqast.typeqastmeterapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Measurement model
 */
@Data
@AllArgsConstructor
public class Measurement {
  private String meter_id;
  private Date time;
  private Number value;
}
