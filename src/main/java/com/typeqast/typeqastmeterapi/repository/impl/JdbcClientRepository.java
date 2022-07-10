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


  @Override
  public List<String> getAllClients() {
    return jdbcTemplate.queryForList("select name from client", String.class);
  }

  @Override
  public List<Client> getAllClientsDetails() {
    return jdbcTemplate.query("select * from client",
      (rs) -> {
        List<Client> clientList = new ArrayList<>();
        while (rs.next()) {
          clientList.add(
            new Client(
              rs.getString("name"),
              rs.getString("street_name"),
              rs.getString("city"),
              rs.getInt("post_code"),
              rs.getString("meter_id")
            )
          );
        }
        return clientList;
      }
    );
  }

  @Override
  public String getClientsMeterId(String client) {
    return jdbcTemplate.queryForObject("select meter_id from client where name=" + client, String.class);
  }

  @Override
  public int saveClient(Client client) {
    return jdbcTemplate.update("insert into client (name, street_name, city, post_code, meter_id)  values (?, ?, ?, ?, ?)",
        client.getName(),
        client.getStreetName(),
        client.getCity(),
        client.getPostCode(),
        client.getMeterId());
  }

  @Override
  public int updateClientsMeter(Client client, String newMeter) {
    return jdbcTemplate.update("update client set meter_id = ? where name = ?", newMeter, client.getName());
  }
}
