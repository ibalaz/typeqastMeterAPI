package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.model.Client;
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
    Client client = new Client(
        "Ivica",
        "Random adresa 1",
        "Random city",
        12345,
        "Randnom meter id"
    );
    when(clientService.getAllClients()).thenReturn(ServiceResult.buildValidResult("Success"));
    when(clientService.getAllClientsDetails()).thenReturn(ServiceResult.buildValidResult("Success"));
    when(clientService.getClientsMeterId("Ivan")).thenReturn(ServiceResult.buildValidResult("Success"));
    when(clientService.addNewClient(client)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(clientService.updateClientsMeter("Silva", "RandomMeterID")).thenReturn(ServiceResult.buildValidResult("Success"));
  }

  @Test
  void itShouldCallGetAllClientsAndGetSuccessTrue() {
    // when
    ServiceResult result = underTest.getAllClients();

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldReturnValidResponseForGetAllClientsDetails() {
    // when
      ServiceResult result = underTest.getAllClientsDetails();

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldReturnValidResponseForGetClientsMeterId() {
    // given
    String clientName = "Ivan";

    // when
    ServiceResult result = underTest.getClientsMeter(clientName);

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldReturnValidResponseForAddNewClient() {
    //given
    Client client = new Client(
        "Ivica",
        "Random adresa 1",
        "Random city",
        12345,
        "Randnom meter id"
    );

    // when
    ServiceResult result = underTest.addNewClient(client);

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }

  @Test
  void itShouldReturnValidResponseForUpdateClientsMeter() {
    // given
    String clientName = "Silva";
    String newMeterId = "RandomMeterID";

    // when
    ServiceResult result = underTest.updateClientMeter(clientName, newMeterId);

    // then
    assertThat(result).isNotNull();
    assertThat(result.success).isTrue();
  }
}