package com.typeqast.typeqastmeterapi.service;

import com.typeqast.typeqastmeterapi.model.Client;
import com.typeqast.typeqastmeterapi.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class ClientServiceTest {

  @Mock
  private ClientRepository clientRepository;

  private ClientService underTest;

  @BeforeEach
  void setUp() {
    underTest = new ClientService(clientRepository);
  }

  @Test
  void getAllClients() {
    // when
    underTest.getAllClients();

    // then
    verify(clientRepository).getAllClients();
  }

  @Test
  void getAllClientsDetails() {
    // when
    underTest.getAllClientsDetails();

    // then
    verify(clientRepository).getAllClientsDetails();
  }

  @Test
  void getClientsMeterId() {
    // when
    String clientName = "Ivan";
    underTest.getClientsMeterId(clientName);

    // then
    verify(clientRepository).getClientsMeterId(clientName);
  }

  @Test
  void addNewClient() {
    // when
    Client client = new Client(
        "Ivica",
        "Random adresa 1",
        "Random city",
        12345,
        "Randnom meter id"
    );
    underTest.addNewClient(client);

    // then
    verify(clientRepository).addClient(client);
  }

  @Test
  void updateClientsMeter() {
    // given
    String clientName = "Silva";
    String newMeterId = "New ID";

    // when
    underTest.updateClientsMeter(clientName, newMeterId);

    // then
    verify(clientRepository).updateClientsMeter(clientName, newMeterId);
  }
}