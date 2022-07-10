package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.service.ClientService;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit test
 */
class ClientControllerTest {
  ClientService clientService = Mockito.mock(ClientService.class);

  ClientController underTest = new ClientController(clientService);

  @BeforeEach
  void setUp() {
    ServiceResult serviceResult = new ServiceResult();
    serviceResult.success = true;
    when(clientService.getAllClients()).thenReturn(serviceResult);
  }

  @Test
  void itShouldCallGetAllClientsAndGetSuccessTrue() {
    // when
    ServiceResult result = underTest.getAllClients();

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }
}