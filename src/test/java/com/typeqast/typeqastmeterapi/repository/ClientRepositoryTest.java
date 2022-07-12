package com.typeqast.typeqastmeterapi.repository;

import com.typeqast.typeqastmeterapi.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClientRepositoryTest {

  @Autowired
  private ClientRepository underTest;


  @Test
  void getAllClients() {
    // given
    String client = "Ivan";

    // when
    List<String> allClients = underTest.getAllClients();

    // then
    assertThat(allClients).isNotNull();
    assertThat(allClients).isInstanceOf(List.class);
    assertThat(allClients).contains(client);
  }

  @Test
  void getAllClientsDetails() {
    // when
    List<Client> allClientDetails = underTest.getAllClientsDetails();

    // then
    assertThat(allClientDetails).isNotNull();
    assertThat(allClientDetails).isInstanceOf(List.class);
  }

  @Test
  void getClientsMeterId() {
    // given
    String clientName = "Ivan";

    // when
    String meterId = underTest.getClientsMeterId(clientName);

    // then
    assertThat(meterId).isNotNull();
    assertThat(meterId).isInstanceOf(String.class);
  }

  @Test
  @Transactional
  void addClient() {
    // given
    Client client = new Client(
        "Ivica",
        "Random adresa 1",
        "Random city",
        12345,
        "Randnom meter id"
    );

    // when
    int rowsAdded = underTest.addClient(client);

    // then
    assertThat(rowsAdded).isNotZero();
  }

  @Test
  @Transactional
  void updateClientsMeter() {
    // given
    String clientName = "Ivica";
    String newMeterId = "New meter id 2";
    Client client = new Client(
        "Ivica",
        "Random adresa 1",
        "Random city",
        12345,
        "Randnom meter id"
    );
    underTest.addClient(client);

    // when
    int rowsUpdated = underTest.updateClientsMeter(clientName, newMeterId);

    // then
    assertThat(rowsUpdated).isNotZero();
  }
}