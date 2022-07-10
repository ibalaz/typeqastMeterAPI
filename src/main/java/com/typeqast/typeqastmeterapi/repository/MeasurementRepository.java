package com.typeqast.typeqastmeterapi.repository;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * The interface Measurement repository.
 */
public interface MeasurementRepository {
  /**
   * Gets measurements for client.
   *
   * @param clientName the client name
   * @return the measurements for client
   */
  HashMap<String, Integer> getMeasurementsForClient(String clientName);

  /**
   * Gets aggregate reading for client in specific year.
   *
   * @param clientName the client name
   * @param year       the year
   * @return the aggregate reading year
   */
  HashMap<String, Integer> getAggregateReadingYear(String clientName, int year);

  /**
   * Gets monthly measurement.
   *
   * @param clientName the client name
   * @param year       the year
   * @param month      the month
   * @return the monthly measurement
   */
  HashMap<String, Integer> getMonthlyMeasurement(String clientName, int year, int month);

  /**
   * Gets report per month.
   *
   * @param clientName the client name
   * @param year       the year
   * @return the report per month
   */
  HashMap<String, Integer> getReportPerMonth(String clientName, int year);

  /**
   * Add new measurement.
   *
   * @param clientName the client name
   * @param date       time of measurement
   * @param value      value of measurement
   * @return number of inserted rows
   */
  int addNewMeasurement(String clientName, LocalDate date, int value);

  /**
   * Complement to add measurement, used to revert adding if something was wrong
   *
   * @param clientName the client name
   * @param date       time of measurement
   * @return number of rows deleted
   */
  int removeMeasurement(String clientName, LocalDate date);

  /**
   * Update measurement int.
   *
   * @param clientName the client name
   * @param date       the date
   * @param newValue   the new value
   * @return the int
   */
  int updateMeasurement(String clientName, LocalDate date, int newValue);

}
