package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.model.MeasurementCommand;
import com.typeqast.typeqastmeterapi.service.MeasurementService;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Rest controller providing endpoints to manipulate measurement data.
 */
@Tag(name = "Measurement API", description = "Measurement endpoints, for getting measurement reports and adding new measurement for clients")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("measurement")
public class MeasurementController {
  /**
   * The Measurement service.
   */
  MeasurementService measurementService;

  /**
   * Gets measurements for client.
   *
   * @param clientName the client name
   * @return the measurements for client
   */
  @Operation(summary = "This endpoint gets all client measurements")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Fetched all clients measurements",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @GetMapping(path = "/getMeasurement/{clientName}")
  public ServiceResult getMeasurementsForClient(@PathVariable String clientName) {
    log.info("Calling getMeasurementsForClient for client " + clientName);
    return measurementService.getMeasurementsForClient(clientName);
  }

  /**
   * Gets aggregate reading.
   *
   * @param clientName the client name
   * @param year       the year
   * @return the aggregate reading
   */
  @Operation(summary = "This endpoint gets summary for client measurements for provided year")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Fetched all clients measurements",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @GetMapping(path = "/getAggregateReading/{clientName}/{year}")
  public ServiceResult getAggregateReading(@PathVariable String clientName, @PathVariable int year) {
    log.info("Calling getAggregateReading for client {}, year {}", clientName, year);
    return measurementService.getAggregateReadingYear(clientName, year);
  }

  /**
   * Gets report per month.
   *
   * @param clientName the client name
   * @param year       the year
   * @return the report per month
   */
  @Operation(summary = "This endpoint gets all client measurements reported on monthly basis for provided year.")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Fetched clients measurements report for specified year",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @GetMapping(path = "/getReportPerMonth")
  public ServiceResult getReportPerMonth(@RequestParam String clientName, @RequestParam int year) {
    log.info("Calling getReportPerMonth for client {}, year {}", clientName, year);
    return measurementService.getReportPerMonth(clientName, year);
  }

  /**
   * Gets monthly measurement.
   *
   * @param clientName the client name
   * @param year       the year
   * @param month      the month
   * @return the monthly measurement
   */
  @Operation(summary = "This endpoint gets clients measurements for specific month")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Fetched clients measurement for specific month",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @GetMapping(path = "/getMonthlyMeasurement/{year}/{month}")
  public ServiceResult getMonthlyMeasurement(@RequestParam String clientName, @PathVariable int year, @PathVariable int month) {
    log.info("Calling getMonthlyMeasurement for client {}, year {}, month {}", clientName, year, month);
    return measurementService.getMonthlyMeasurement(clientName, year, month);
  }

  /**
   * Endpoint for adding new measurement .
   *
   * @param command the command that contains data for saving new measurement
   * @return the service result
   */
  @Operation(summary = "This endpoint adds new measurement for client")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully added measurement",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available"
      )
  })
  @PostMapping(path = "/addNew")
  public ServiceResult addNewMeasurement(@RequestBody MeasurementCommand command) {
    log.info("Calling addNewMeasurement with params {}", command);
    LocalDate date;
    if (command.getDate() == null) {
      log.info("Setting measurement date to today's date!");
      date = LocalDate.now();
    } else {
      date = LocalDate.parse(command.getDate());
    }
    return measurementService.addNewMeasurement(command.getClientName(), date, command.getValue());
  }

  /**
   * Remove measurement.
   *
   * @param clientName the client name whose measurement we want to delete
   * @param date       the date of measurement that we want to delete
   * @return the service result
   */
  @Operation(summary = "This endpoint removes measurement")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully removed measurement",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available"
      )
  })
  @PostMapping(path = "/remove")
  public ServiceResult removeMeasurement(@RequestParam String clientName, @RequestParam String date) {
    log.info("Calling removeMeasurement for client {} and date {}", clientName, date);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return measurementService.removeMeasurement(clientName, LocalDate.parse(date, dtf));
  }

  /**
   * Update measurement .
   *
   * @param command the command with client name, date and new value
   * @return the service result
   */
  @Operation(summary = "This endpoint updates measurement")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully updated measurement",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available"
      )
  })
  @PostMapping(path = "/update")
  public ServiceResult updateMeasurement(@RequestBody MeasurementCommand command) {
    log.info("Calling updateMeasurement with parameters {}", command);
    LocalDate date;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    if (command.getDate() != null) {
      date = LocalDate.parse(command.getDate(), dtf);
    } else {
      ServiceResult result = new ServiceResult();
      result.success = false;
      result.errorMessageList = List.of("Please provide date for which record is updated");
      return result;
    }
    return measurementService.updateMeasurement(command.getClientName(), date, command.getValue());
  }
}
