package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.model.MeasurementCommand;
import com.typeqast.typeqastmeterapi.service.MeasurementService;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class MeasurementControllerTest {

  MeasurementService measurementService = Mockito.mock(MeasurementService.class);
  MeasurementController underTest = new MeasurementController(measurementService);

  @BeforeEach
  void setUp() {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String date = dateFormat.format(LocalDate.now());

    when(measurementService.getMonthlyMeasurement("Ivan", 2020, 1)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.getMeasurementsForClient("Ivan")).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.getAggregateReadingYear("Ivan", 2020)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.getReportPerMonth("Ivan", 2020)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.addNewMeasurement("Ivan", LocalDate.parse(date, dateFormat), 10)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.removeMeasurement("Ivan", LocalDate.parse(date, dateFormat))).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.updateMeasurement("Ivan", LocalDate.parse(date, dateFormat), 20)).thenReturn(ServiceResult.buildValidResult("Success"));
  }

  @Test
  void itShouldReturnValidResponseWhenCallingGetMeasurementsForClient() {
    // when
    ServiceResult result = underTest.getMeasurementsForClient("Ivan");

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldReturnValidResponseWhenCallingGetAggregateReading() {
    // when
    ServiceResult result = underTest.getAggregateReading("Ivan", 2020);

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldReturnValidResponseWhenCallingGetReportPerMonth() {
    // when
    ServiceResult result = underTest.getReportPerMonth("Ivan", 2020);

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldReturnValidResponseWhenCallingGetMonthlyMeasurement() {
    // when
    ServiceResult result = underTest.getMonthlyMeasurement("Ivan", 2020, 1);

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldAddNewMeasurement() {
    // when
    ServiceResult result = underTest.addNewMeasurement(new MeasurementCommand("Ivan",10, null));

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldRemoveMeasurement() {
    // when
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String date = dateFormat.format(LocalDate.now());
    ServiceResult result = underTest.removeMeasurement("Ivan", date);

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldUpdateMeasurement() {
    // when
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String date = dateFormat.format(LocalDate.now());
    ServiceResult result = underTest.updateMeasurement(new MeasurementCommand("Ivan",20, date));

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }
}