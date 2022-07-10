package com.typeqast.typeqastmeterapi.service;

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
}