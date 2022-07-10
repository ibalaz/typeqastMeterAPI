package com.typeqast.typeqastmeterapi.repository;

import com.typeqast.typeqastmeterapi.model.Client;

import java.util.List;

/**
 * The interface Client repository.
 */
public interface ClientRepository {
  /**
   * Gets all clients.
   *
   * @return the all clients
   */
  List<String> getAllClients();

  /**
   * Gets all clients details.
   *
   * @return the all clients details
   */
  List<Client> getAllClientsDetails();

  /**
   * Gets clients meter id.
   *
   * @param client the client
   * @return the clients meter id
   */
  String getClientsMeterId(String client);

  /**
   * Save client int.
   *
   * @param client the client
   * @return the int
   */
  int saveClient(Client client);

  /**
   * Update clients meter int.
   *
   * @param client   the client
   * @param newMeter the new meter
   * @return the int
   */
  int updateClientsMeter(Client client, String newMeter);
}
