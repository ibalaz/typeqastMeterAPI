package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.model.Client;
import com.typeqast.typeqastmeterapi.service.ClientService;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
  void itShouldReturnValidResponseForGetAllClients() throws Exception {

    mockMvc.perform(get("/client/getAll"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(clientService).getAllClients();
  }

  @Test
  void itShouldReturnValidResponseForGetAllClientsDetails() throws Exception {
    mockMvc.perform(get("/client/getAllDetails"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(clientService).getAllClientsDetails();
  }

  @Test
  void itShouldReturnValidResponseForGetClientsMeterId() throws Exception {
    mockMvc.perform(get("/client/getClientsMeter?clientName=Ivan"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(clientService).getClientsMeterId("Ivan");
  }

  @Test
  void itShouldReturnValidResponseForAddNewClient() throws Exception {
    Client client = new Client(
        "Ivica",
        "Random adresa 1",
        "Random city",
        12345,
        "Randnom meter id"
    );
    mockMvc.perform(post("/client/addNewClient")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Ivica\", \"streetName\": \"Random adresa 1\", \"city\": \"Random city\", \"postCode\": 12345, \"meterId\": \"Randnom meter id\" }"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(clientService).addNewClient(client);
  }

  @Test
  void itShouldReturnValidResponseForUpdateClientsMeter() throws Exception {
    mockMvc.perform(post("/client/updateClientsMeter?clientName=Silva&newMeter=RandomMeterID"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(clientService).updateClientsMeter("Silva", "RandomMeterID");
  }
}