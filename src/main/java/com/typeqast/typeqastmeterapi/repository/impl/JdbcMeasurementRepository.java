package com.typeqast.typeqastmeterapi.repository.impl;

import com.typeqast.typeqastmeterapi.repository.MeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * The type Jdbc measurement repository.
 */
@Slf4j
@Repository
public class JdbcMeasurementRepository implements MeasurementRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private static final String NAME = "name";
  private static final String METER_ID = "meter_id";
  private static final String TIME = "time";
  private static final String VALUE = "value";
  private static final String YEAR = "year";
  private static final String MONTH = "month";


  private static final String GET_MEASUREMENT_FOR_CLIENT = String.format("SELECT %s, %s FROM measurement WHERE %s = (SELECT %s FROM client WHERE %s= ?);", TIME, VALUE, METER_ID, METER_ID, NAME);
  private static final String GET_MEASUREMENT_FOR_CLIENT_AND_YEAR =
      String.format("SELECT %s, %s FROM measurement WHERE EXTRACT(%s from %s)= ? AND %s = (SELECT %s FROM client where %s= ?);",TIME, VALUE, YEAR, TIME, METER_ID, METER_ID, NAME);
  private static final String GET_MEASUREMENT_FOR_CLIENT_YEAR_AND_MONTH =
      String.format("SELECT %s, %s FROM measurement WHERE EXTRACT(%s from %s)= ? AND EXTRACT(%s FROM %s) = ? AND %s = (SELECT %s FROM client where %s= ?);",TIME, VALUE, YEAR, TIME, MONTH, TIME, METER_ID, METER_ID, NAME);

  private static final String INSERT_NEW_MEASUREMENT = String.format("INSERT INTO measurement (%s, %s, %s) VALUES ((SELECT %s FROM client WHERE %s = ?), ?, ?);", METER_ID, TIME, VALUE, METER_ID, NAME);
  private static final String REMOVE_MEASUREMENT = String.format("DELETE FROM measurement WHERE %s=(SELECT %s FROM client WHERE %s = ?) AND %s = ?;", METER_ID, METER_ID, NAME, TIME);
  private static final String UPDATE_MEASUREMENT = String.format("UPDATE measurement SET %s= ? WHERE %s=(SELECT %s FROM client WHERE %s = ?) AND %s = ?;", VALUE, METER_ID, METER_ID, NAME, TIME);


  @Override
  public HashMap<String, Integer> getMeasurementsForClient(String clientName) {
    return jdbcTemplate.query(GET_MEASUREMENT_FOR_CLIENT,
        (ResultSet rs) -> {
          HashMap<String, Integer> result = new HashMap<>();
          while (rs.next()) {
            result.put(
                LocalDate.parse(rs.getString(TIME)).getMonth().toString() + " - " + LocalDate.parse(rs.getString(TIME)).getYear(),
                rs.getInt(VALUE)
            );
          }
          return result;
        },
        clientName);
  }

  @Override
  public HashMap<String, Integer> getAggregateReadingYear(String clientName, int year) {
    return jdbcTemplate.query(GET_MEASUREMENT_FOR_CLIENT_AND_YEAR,
        (ResultSet rs) -> {
          int sum = 0;
          HashMap<String, Integer> result = new HashMap<>();
          while (rs.next()) {
             sum = sum + rs.getInt("value");
          }
          result.put("year", year);
          result.put("value", sum);
          return result;
        },
        year, clientName
    );
  }

  @Override
  public HashMap<String, Integer> getMonthlyMeasurement(String clientName, int year, int month) {
    return jdbcTemplate.query(GET_MEASUREMENT_FOR_CLIENT_YEAR_AND_MONTH,
      (ResultSet rs) -> {
        HashMap<String, Integer> result = new HashMap<>();
        while (rs.next()) {
          result.put(
              LocalDate.parse(rs.getString("time")).getMonth().toString(),
              rs.getInt("value")
          );
        }
        result.put("year", year);
        return result;
      },
        year, month, clientName
    );
  }

  @Override
  public HashMap<String, Integer> getReportPerMonth(String clientName, int year) {
    return jdbcTemplate.query(GET_MEASUREMENT_FOR_CLIENT_AND_YEAR,
      (ResultSet rs) -> {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("year", year);
        while (rs.next()) {
          result.put(
              LocalDate.parse(rs.getString("time")).getMonth().toString(),
              rs.getInt("value"));
        }
        return result;
      },
        year, clientName
    );
  }

  @Override
  public int addNewMeasurement(String clientName, LocalDate date, int value) {
    return jdbcTemplate.update(INSERT_NEW_MEASUREMENT, clientName, date, value);
  }

  @Override
  public int removeMeasurement(String clientName, LocalDate date) {
    return jdbcTemplate.update(REMOVE_MEASUREMENT, clientName, date);
  }

  @Override
  public int updateMeasurement(String clientName, LocalDate date, int newValue) {
    return jdbcTemplate.update(UPDATE_MEASUREMENT, newValue, clientName, date);
  }
}
