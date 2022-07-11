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
}
