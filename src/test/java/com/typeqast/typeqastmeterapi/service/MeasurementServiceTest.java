package com.typeqast.typeqastmeterapi.service;

import com.typeqast.typeqastmeterapi.repository.MeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class MeasurementServiceTest {

  @Mock
  private MeasurementRepository measurementRepository;

  private MeasurementService underTest;

  @BeforeEach
  void setUp() {
    underTest = new MeasurementService(measurementRepository);
  }

  @Test
  void getMeasurementsForClient() {
    // when
    underTest.getMeasurementsForClient("Ivan");

    // then
    verify(measurementRepository).getMeasurementsForClient("Ivan");
  }

  @Test
  void getAggregateReadingYear() {
    // when
    underTest.getAggregateReadingYear("Ivan", 2020);

    // then
    verify(measurementRepository).getAggregateReadingYear("Ivan", 2020);
  }

  @Test
  void getReportPerMonth() {
    // when
    underTest.getReportPerMonth("Ivan", 2020);

    // then
    verify(measurementRepository).getReportPerMonth("Ivan", 2020);
  }

  @Test
  void getMonthlyMeasurement() {
    // when
    underTest.getMonthlyMeasurement("Ivan",2020, 1);

    // then
    verify(measurementRepository).getMonthlyMeasurement("Ivan", 2020, 1);
  }

  @Test
  void addNewMeasurement() {
    // when
    underTest.addNewMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 1);

    // then
    verify(measurementRepository).addNewMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 1);
  }

  @Test
  void removeMeasurement() {
    // when
    underTest.removeMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));

    // then
    verify(measurementRepository).removeMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
  }

  @Test
  void updateMeasurement() {
    // when
    underTest.updateMeasurement("Ivan",LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 1);

    // then
    verify(measurementRepository).updateMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 1);
  }
}