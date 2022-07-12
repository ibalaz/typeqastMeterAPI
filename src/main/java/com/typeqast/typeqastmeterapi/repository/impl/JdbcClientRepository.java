package com.typeqast.typeqastmeterapi.repository.impl;

import com.typeqast.typeqastmeterapi.model.Client;
import com.typeqast.typeqastmeterapi.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Jdbc client repository.
 */
@Slf4j
@Repository
public class JdbcClientRepository implements ClientRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * Static string for column names and sql statements, for more readability.
   */
  private static final String NAME = "name";
  private static final String STREET_NAME = "street_name";
  private static final String CITY = "city";
  private static final String POST_CODE = "post_code";
  private static final String METER_ID = "meter_id";

  private static final String GET_CLIENT_NAMES = String.format("SELECT %s FROM client", NAME);
  private static final String GET_CLIENT_DETAILS = "SELECT * FROM client";
  private static final String GET_CLIENT_METER_ID = String.format("SELECT %s FROM client WHERE %s= ?", METER_ID, NAME);
  private static final String INSERT_NEW_CLIENT = String.format("INSERT INTO client (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", NAME, STREET_NAME, CITY, POST_CODE, METER_ID);
  private static final String UPDATE_CLIENT = String.format("UPDATE client SET %s = ? WHERE %s = ?", METER_ID, NAME);



  @Override
  public List<String> getAllClients() {
    return jdbcTemplate.queryForList(GET_CLIENT_NAMES, String.class);
  }

  @Override
  public List<Client> getAllClientsDetails() {
    return jdbcTemplate.query(GET_CLIENT_DETAILS,
      (rs) -> {
        List<Client> clientList = new ArrayList<>();
        while (rs.next()) {
          clientList.add(
            new Client(
              rs.getString(NAME),
              rs.getString(STREET_NAME),
              rs.getString(CITY),
              rs.getInt(POST_CODE),
              rs.getString(METER_ID)
            )
          );
        }
        return clientList;
      }
    );
  }

  @Override
  public String getClientsMeterId(String client) {
    return jdbcTemplate.queryForObject(GET_CLIENT_METER_ID, String.class, client);
  }

  @Override
  public int addClient(Client client) {
    return jdbcTemplate.update(INSERT_NEW_CLIENT,
        client.getName(),
        client.getStreetName(),
        client.getCity(),
        client.getPostCode(),
        client.getMeterId());
  }

  @Override
  public int updateClientsMeter(String clientName, String newMeter) {
    return jdbcTemplate.update(UPDATE_CLIENT, newMeter, clientName);
  }
}
