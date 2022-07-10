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

  @Override
  public HashMap<String, Integer> getMeasurementsForClient(String clientName) {
    String sql = "select time, value from Measurement where meter_id = (select meter_id from Client where name= '" + clientName + "');";
    return jdbcTemplate.query(sql,
        (ResultSet rs) -> {
          HashMap<String, Integer> result = new HashMap<>();
          while (rs.next()) {
            result.put(
                LocalDate.parse(rs.getString("time")).getMonth().toString() + " - " + LocalDate.parse(rs.getString("time")).getYear(),
                rs.getInt("value")
            );
          }
          return result;
        });
  }

  @Override
  public HashMap<String, Integer> getAggregateReadingYear(String clientName, int year) {
    String sql = "select  time, value from Measurement where extract(year from time)= " + year + " and meter_id = (select meter_id from Client where name= '" + clientName + "');";
    return jdbcTemplate.query(sql,
        (ResultSet rs) -> {
          int sum = 0;
          HashMap<String, Integer> result = new HashMap<>();
          while (rs.next()) {
             sum = sum + rs.getInt("value");
          }
          result.put("year", year);
          result.put("value", sum);
          return result;
        }
    );
  }

  @Override
  public HashMap<String, Integer> getMonthlyMeasurement(String clientName, int year, int month) {
    String sql = "select time, value from Measurement where extract(year from time)= " + year + " and extract(month from time)= " + month + " and meter_id = (select meter_id from Client where name= '" + clientName + "');";
    return jdbcTemplate.query(sql,
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
        }
    );
  }

  @Override
  public HashMap<String, Integer> getReportPerMonth(String clientName, int year) {
    String sql = "select time, value from Measurement where extract(year from time)= " + year + " and meter_id = (select meter_id from Client where name= '" + clientName + "');";
    return jdbcTemplate.query(sql,
        (ResultSet rs) -> {
          HashMap<String, Integer> result = new HashMap<>();
          result.put("year", year);
          while (rs.next()) {
            result.put(
                LocalDate.parse(rs.getString("time")).getMonth().toString(),
                rs.getInt("value"));
          }
          return result;
    }
    );
  }

  @Override
  public int addNewMeasurement(String clientName, LocalDate date, int value) {
    return jdbcTemplate.update("insert into measurement (meter_id, time, value) values ((select meter_id from client where name = ?), ?, ?);", clientName, date, value);
  }

  @Override
  public int removeMeasurement(String clientName, LocalDate date) {
    return jdbcTemplate.update("delete from measurement where meter_id=(select meter_id from client where name = ?) and time = ?;", clientName, date);
  }

  @Override
  public int updateMeasurement(String clientName, LocalDate date, int newValue) {
    return jdbcTemplate.update("update measurement SET value= ? WHERE meter_id=(select meter_id from client where name = ?) AND time = ?", newValue, clientName, date);
  }
}
