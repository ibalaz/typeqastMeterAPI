package com.typeqast.typeqastmeterapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * The type Measurement command.
 */
@Data
@AllArgsConstructor
public class MeasurementCommand {
  /**
   * The Client name.
   */
  @NotEmpty(message = "Valid client name is required.")
  String clientName;
  /**
   * The Value.
   */
  @NotEmpty(message = "Measurement value is required")
  int value;

  /**
   * The Date.
   */
  String date;
}
