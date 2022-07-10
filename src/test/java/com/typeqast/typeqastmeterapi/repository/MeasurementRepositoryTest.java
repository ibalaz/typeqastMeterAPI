package com.typeqast.typeqastmeterapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MeasurementRepositoryTest {

  @Autowired
  private MeasurementRepository underTest;

  @Test
  void getMeasurementsForClient() {
    // given
    String clientName = "Ivan";

    // when
    HashMap<String, Integer> measurements = underTest.getMeasurementsForClient(clientName);

    // then
    assertThat(measurements).isNotNull();
    assertThat(measurements).isNotEmpty();
    assertThat(measurements).isInstanceOf(HashMap.class);
    assertThat(measurements).containsKey("JULY - 2020"); // we filled this data in initialization via liquibase
  }

  @Test
  void getAggregateReadingYear() {
    // given
    String clientName = "Ivan";
    int year = 2020;

    // when
    HashMap<String,Integer> measurements = underTest.getAggregateReadingYear(clientName, year);

    // then
    assertThat(measurements).isNotNull()
        .isNotEmpty()
        .containsKey("year")
        .containsValue(2020);

  }

  @Test
  void getMonthlyMeasurement() {
    // given
    String clientName = "Ivan";
    int year = 2020;
    int month = 1;

    // when
    HashMap<String, Integer> measurements = underTest.getMonthlyMeasurement(clientName, year, month);

    // then
    assertThat(measurements).isNotNull()
        .isNotEmpty()
        .containsKey("JANUARY")
        .containsKey("year");
  }

  @Test
  void getReportPerMonth() {
    // given
    String clientName = "Ivan";
    int year = 2020;

    // when
    HashMap<String, Integer> measurements = underTest.getReportPerMonth(clientName, year);

    // then
    assertThat(measurements).isNotNull()
        .isNotEmpty()
        .containsKey("JANUARY")
        .containsKey("year")
        .containsValue(38);
  }

  @Test
  void addNewMeasurement() {
    // given
    String clientName = "Ivan";
    LocalDate date = LocalDate.now();
    int value = 10;

    // when
    int addedRows = underTest.addNewMeasurement(clientName, date, value);

    // then
    assertThat(addedRows).isEqualTo(1);

    // cleanup - making sure we leave database as it was
    underTest.removeMeasurement(clientName,date);
  }

  @Test
  void removeMeasurement() {
    // given
    String clientName = "Ivan";
    LocalDate date = LocalDate.now();
    int value = 10;
    underTest.addNewMeasurement(clientName,date, value); // setup for test, making sure we have something to delete

    // when
    int deletedRows = underTest.removeMeasurement(clientName, date);

    // then
    assertThat(deletedRows).isEqualTo(1);
  }

  @Test
  void updateMeasurement() {
    // given
    String clientName = "Ivan";
    LocalDate date = LocalDate.now();
    int value = 20;
    int newValue = 30;
    underTest.addNewMeasurement(clientName, date, value); // setup for test, making sure we have something to update

    // when
    int updatedRows = underTest.updateMeasurement(clientName, date, newValue);

    // then
    assertThat(updatedRows).isEqualTo(1);

    // cleanup - making sure we leave database as it was
    underTest.removeMeasurement(clientName, date);
  }
}