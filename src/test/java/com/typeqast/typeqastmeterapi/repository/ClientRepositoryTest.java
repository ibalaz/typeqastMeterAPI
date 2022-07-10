package com.typeqast.typeqastmeterapi.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
  @Disabled
  void updateClientsMeter() {
  }
}