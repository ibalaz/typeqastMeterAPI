package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.service.ClientService;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(ClientController.class)
class ClientControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  ClientService clientService;


  @BeforeEach
  void setUp() {
    ServiceResult result = new ServiceResult();
    result.success = true;
    when(clientService.getAllClients()).thenReturn(result);
  }

  @Test
  void itShouldReturnValidResponseForGetAllClients() throws Exception {

    mockMvc.perform(get("/client/getAll"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessageList\":null,\"result\":null}"));
    verify(clientService).getAllClients();
  }
}