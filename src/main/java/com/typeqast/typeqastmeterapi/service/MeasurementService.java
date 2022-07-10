package com.typeqast.typeqastmeterapi.service;

import com.typeqast.typeqastmeterapi.repository.MeasurementRepository;
import com.typeqast.typeqastmeterapi.util.NoMeasurementRecordException;
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
    ServiceResult result = new ServiceResult();
    try {
      result.result = measurementRepository.getMeasurementsForClient(clientName);
      result.success = true;
    } catch (Exception e) {
      log.error("Error getting measurements for client {}, error {}", clientName, e);
      result.success = false;
      result.errorMessageList = List.of("Error getting measurements for client " + clientName + ", error " +  e);
    }
    return result;
  }

  /**
   * Gets aggregate reading year.
   *
   * @param clientName the client name
   * @param year       the year
   * @return the aggregate reading year
   */
  public ServiceResult getAggregateReadingYear(String clientName, int year) {
    ServiceResult result = new ServiceResult();
    try {
      result.result = measurementRepository.getAggregateReadingYear(clientName, year);
      result.success = true;
    } catch (Exception e) {
      log.error("Error getting aggregate reading for client {}, year {}, error {}", clientName, year, e);
      result.success = false;
      result.errorMessageList = List.of("Error getting aggregate reading for client " + clientName +", year "+ year +", error " + e);
    }
    return result;
  }

  /**
   * Gets report per month.
   *
   * @param clientName the client name
   * @param year       the year
   * @return the report per month
   */
  public ServiceResult getReportPerMonth(String clientName, int year) {
    ServiceResult result = new ServiceResult();
    try {
      result.result = measurementRepository.getReportPerMonth(clientName, year);
      result.success = true;
    } catch (Exception e) {
      log.error("Error getting monthly report for client {}, year {}, error {}", clientName, year, e);
      result.success = false;
      result.errorMessageList = List.of("Error getting monthly reading for client " + clientName +", year "+ year +", error " + e);
    }
    return result;
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
    ServiceResult result = new ServiceResult();
    try {
      result.result = measurementRepository.getMonthlyMeasurement(clientName, year, month);
      result.success = true;
    } catch (Exception e) {
      log.error("Error getting monthly measurement for client {}, year {}, month {}, error {}", clientName, year, month, e);
      result.success = false;
      result.errorMessageList = List.of("Error getting monthly measurement for client " + clientName +", year "+ year +", month "+ month +", error " + e);
    }
    return result;
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
    ServiceResult result = new ServiceResult();
    try {
      int rowsAdded = measurementRepository.addNewMeasurement(clientName, date, value);
      if (rowsAdded == 1) {
        result.success = true;
        result.result = "Adding successful";
      } else {
        result.success = false;
        result.result = "Something went wrong, reverting changes";
        int rowsDeleted = measurementRepository.removeMeasurement(clientName, date);
        if (rowsAdded != rowsDeleted) {
          throw new Exception("Something went wrong with reverting added measurement, contact your admin");
        }
      }
    } catch (DuplicateKeyException e) {
      log.error("Error adding new measurement, measurement for that date already exits.");
      result.success = false;
      result.errorMessageList = List.of("Error adding new measurement, measurement for that date already exits.");
    } catch (Exception e) {
      log.error("Error adding new measurement " + e);
      result.success = false;
      result.errorMessageList = List.of("Error adding new measurement " + e);
    }
    return result;
  }

  /**
   * Remove measurement service result.
   *
   * @param clientName the client name
   * @param date       the date
   * @return the service result
   */
  public ServiceResult removeMeasurement(String clientName, LocalDate date) {
    ServiceResult result = new ServiceResult();
    try {
      int rowsDeleted = measurementRepository.removeMeasurement(clientName, date);
      if (rowsDeleted == 1) {
        result.success = true;
        result.result = "Removing successful";
      } else if (rowsDeleted == 0) {
        throw new NoMeasurementRecordException("There is no record with this combination clientName " + clientName + " date " + date, new RuntimeException());
      } else {
        throw new Exception("Something went wrong deleting measurement, contact your admin");
      }
    } catch (Exception e) {
      log.error("Error removing measurement " + e);
      result.success = false;
      result.errorMessageList = List.of("Error removing measurement " + e);
    }
    return result;
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
    ServiceResult result = new ServiceResult();
    try {
      int rowsUpdated = measurementRepository.updateMeasurement(clientName, date, newValue);
      if (rowsUpdated == 1) {
        result.success = true;
        result.result = "Updating successful";
      } else {
        throw new Exception("Something went wrong with update, contact your admin");
      }
    } catch (Exception e) {
      log.error("Error updating measurement" + e);
      result.success = false;
      result.errorMessageList = List.of("Error updating measurement" + e);
    }
    return result;
  }

}
