package com.typeqast.typeqastmeterapi.service;

import com.typeqast.typeqastmeterapi.model.Client;
import com.typeqast.typeqastmeterapi.repository.ClientRepository;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Client service.
 */
@Service
@Slf4j
@AllArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;

  /**
   * Gets all clients.
   *
   * @return the all clients
   */
  public ServiceResult getAllClients() {
    try {
      return ServiceResult.buildValidResult(clientRepository.getAllClients());
    } catch (Exception e) {
      log.error("Error getting all clients " + e);
      return ServiceResult.buildErrorResult(List.of("Error getting all clients " + e));
    }
  }

  /**
   * Gets all clients details.
   *
   * @return the all clients details
   */
  public ServiceResult getAllClientsDetails() {
    try {
      return ServiceResult.buildValidResult(clientRepository.getAllClientsDetails());
    } catch (Exception e) {
      log.error("Error getting all clients details " + e);
      return ServiceResult.buildErrorResult(List.of("Error getting all clients details " + e));
    }
  }

  /**
   * Gets clients meterId.
   *
   * @param clientName the client name
   * @return the clients meter id
   */
  public ServiceResult getClientsMeterId(String clientName) {
    try {
      return ServiceResult.buildValidResult(clientRepository.getClientsMeterId(clientName));
    } catch (Exception e) {
      log.error("Error getting clients meter id " + e);
      return ServiceResult.buildErrorResult(List.of("Error getting clients meter id " + e));
    }
  }

  /**
   * Add new client.
   *
   * @param client the client
   * @return the service result
   */
  public ServiceResult addNewClient(Client client) {
    try {
      int rowsAdded = clientRepository.addClient(client);
      if (rowsAdded == 1) {
        return ServiceResult.buildValidResult("Successful adding of new client");
      } else {
        return ServiceResult.buildErrorResult(List.of("Something went wrong, please contact your admin"));
      }
    } catch (Exception e) {
      log.error("Error saving new client " + e);
      return ServiceResult.buildErrorResult(List.of("Error saving new client " + e));
    }
  }

  /**
   * Update clients meterId.
   *
   * @param clientName the client name
   * @param meterId    the meter id
   * @return the service result
   */
  public ServiceResult updateClientsMeter(String clientName, String meterId) {
    try {
      int rowsUpdated = clientRepository.updateClientsMeter(clientName, meterId);
      if (rowsUpdated == 1) {
        return ServiceResult.buildValidResult("Successful updating of clients meter");
      } else {
        return ServiceResult.buildErrorResult(List.of("Something went wrong updating clients meter, please contact your admin"));
      }

    } catch (Exception e) {
      log.error("Error updating clients meter " + e);
      return ServiceResult.buildErrorResult(List.of("Error updating clients meter " + e));
    }
  }
}
