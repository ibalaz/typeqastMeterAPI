package com.typeqast.typeqastmeterapi.service;

import com.typeqast.typeqastmeterapi.repository.MeasurementRepository;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Measurement service.
 */
@Service
@Slf4j
@AllArgsConstructor
public class MeasurementService {

  private final MeasurementRepository measurementRepository;

  /**
   * Gets measurements for client.
   *
   * @param clientName the client name
   * @return the measurements for client
   */
  public ServiceResult getMeasurementsForClient(String clientName) {
    try {
      return ServiceResult.buildValidResult(measurementRepository.getMeasurementsForClient(clientName));
    } catch (Exception e) {
      log.error("Error getting measurements for client {}, error {}", clientName, e);
      return ServiceResult.buildErrorResult(List.of("Error getting measurements for client " + clientName + ", error " +  e));
    }
  }

  /**
   * Gets aggregate reading year.
   *
   * @param clientName the client name
   * @param year       the year
   * @return the aggregate reading year
   */
  public ServiceResult getAggregateReadingYear(String clientName, int year) {

    try {
      return ServiceResult.buildValidResult(measurementRepository.getAggregateReadingYear(clientName, year));

    } catch (Exception e) {
      log.error("Error getting aggregate reading for client {}, year {}, error {}", clientName, year, e);
      return ServiceResult.buildErrorResult(List.of("Error getting aggregate reading for client " + clientName +", year "+ year +", error " + e));
    }
  }

  /**
   * Gets report per month.
   *
   * @param clientName the client name
   * @param year       the year
   * @return the report per month
   */
  public ServiceResult getReportPerMonth(String clientName, int year) {
    try {
      return ServiceResult.buildValidResult(measurementRepository.getReportPerMonth(clientName, year));
    } catch (Exception e) {
      log.error("Error getting monthly report for client {}, year {}, error {}", clientName, year, e);
      return ServiceResult.buildErrorResult(List.of("Error getting monthly reading for client " + clientName +", year "+ year +", error " + e));
    }
  }

  /**
   * Gets monthly measurement.
   *
   * @param clientName the client name
   * @param year       the year
   * @param month      the month
   * @return the monthly measurement
   */
  public ServiceResult getMonthlyMeasurement(String clientName, int year, int month) {
    try {
      return ServiceResult.buildValidResult(measurementRepository.getMonthlyMeasurement(clientName, year, month));
    } catch (Exception e) {
      log.error("Error getting monthly measurement for client {}, year {}, month {}, error {}", clientName, year, month, e);
      return ServiceResult.buildErrorResult(List.of("Error getting monthly measurement for client " + clientName +", year "+ year +", month "+ month +", error " + e));
    }
  }

  /**
   * Add new measurement
   *
   * @param clientName the client name
   * @param date       date of measurement
   * @param value      value of measurement
   * @return success status of adding measurement
   */
  public ServiceResult addNewMeasurement(String clientName, LocalDate date, int value) {
    try {
      int rowsAdded = measurementRepository.addNewMeasurement(clientName, date, value);
      if (rowsAdded == 1 ) {
        return ServiceResult.buildValidResult("Successful adding of new measurement");
      } else {
        return ServiceResult.buildErrorResult(List.of("Something was wrong in adding new measurement, contact your admin!"));
      }
    } catch (DuplicateKeyException e) {
      log.error("Error adding new measurement, measurement for that date already exists.");
      return ServiceResult.buildErrorResult(List.of("Error adding new measurement, measurement for that date already exists."));
    } catch (Exception e) {
      log.error("Error adding new measurement " + e);
      return ServiceResult.buildErrorResult(List.of("Error adding new measurement " + e));
    }
  }

  /**
   * Remove measurement service result.
   *
   * @param clientName the client name
   * @param date       the date
   * @return the service result
   */
  public ServiceResult removeMeasurement(String clientName, LocalDate date) {
    try {
      return ServiceResult.buildValidResult(measurementRepository.removeMeasurement(clientName, date));
    } catch (Exception e) {
      log.error("Error removing measurement " + e);
      return ServiceResult.buildErrorResult(List.of("Error removing measurement " + e));
    }
  }

  /**
   * Update measurement service result.
   *
   * @param clientName the client name
   * @param date       the date
   * @param newValue   the new value
   * @return the service result
   */
  public ServiceResult updateMeasurement(String clientName, LocalDate date, int newValue) {
    try {
      return ServiceResult.buildValidResult(measurementRepository.updateMeasurement(clientName, date, newValue));
    } catch (Exception e) {
      log.error("Error updating measurement" + e);
      return ServiceResult.buildErrorResult(List.of("Error updating measurement" + e));
    }
  }

}
