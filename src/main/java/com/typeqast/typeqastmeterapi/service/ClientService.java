package com.typeqast.typeqastmeterapi.service;

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
    ServiceResult result = new ServiceResult();
    try {
      result.result = clientRepository.getAllClients();
      result.success = true;
    } catch (Exception e) {
      log.error("Error getting all clients " + e);
      result.success = false;
      result.errorMessageList = List.of("Error getting all clients " + e);
    }
    return result;
  }

  /**
   * Gets all clients details.
   *
   * @return the all clients details
   */
  public ServiceResult getAllClientsDetails() {
    ServiceResult result = new ServiceResult();
    try {
      result.result = clientRepository.getAllClientsDetails();
      result.success = true;
    } catch (Exception e) {
      log.error("Error getting all clients details " + e);
      result.success = false;
      result.errorMessageList = List.of("Error getting all clients details " + e);
    }
    return result;
  }
}
