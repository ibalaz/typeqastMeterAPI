package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.service.MeasurementService;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeasurementController.class)
class MeasurementControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  MeasurementService measurementService;

  @BeforeEach
  void setUp() {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String date = dateFormat.format(LocalDate.now());

    when(measurementService.getMonthlyMeasurement("Ivan", 2020, 1)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.getMeasurementsForClient("Ivan")).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.getAggregateReadingYear("Ivan", 2020)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.getReportPerMonth("Ivan", 2020)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.addNewMeasurement("Ivan", LocalDate.parse(date, dateFormat), 10)).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.removeMeasurement("Ivan", LocalDate.parse(date, dateFormat))).thenReturn(ServiceResult.buildValidResult("Success"));
    when(measurementService.updateMeasurement("Ivan", LocalDate.parse(date, dateFormat), 20)).thenReturn(ServiceResult.buildValidResult("Success"));
  }

  @Test
  void itShouldReturnValidResponseGetMeasurementsForClient() throws Exception {
    mockMvc.perform(get("/measurement/getMeasurement/Ivan"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(measurementService).getMeasurementsForClient("Ivan");
  }

  @Test
  void itShouldReturnValidResponseGetAggregateReading() throws Exception {
    mockMvc.perform(get("/measurement/getAggregateReading/Ivan/2020"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(measurementService).getAggregateReadingYear("Ivan", 2020);
  }

  @Test
  void itShouldReturnValidResponseGetReportPerMonth() throws Exception {
    mockMvc.perform(get("/measurement/getReportPerMonth?clientName=Ivan&year=2020"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(measurementService).getReportPerMonth("Ivan", 2020);
  }

  @Test
  void itShouldReturnValidResponseGetMonthlyMeasurement() throws Exception {
    mockMvc.perform(get("/measurement/getMonthlyMeasurement/2020/1?clientName=Ivan"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{ \"success\":true,\"errorMessages\":[],\"result\":\"Success\" }"));
    verify(measurementService).getMonthlyMeasurement("Ivan", 2020, 1);
  }

  @Test
  void itShouldAddNewMeasurement() throws Exception {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String date = dateFormat.format(LocalDate.now());

    mockMvc.perform(MockMvcRequestBuilders.post("/measurement/addNew")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"clientName\": \"Ivan\", \"value\":  10 }"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(measurementService).addNewMeasurement("Ivan", LocalDate.parse(date, dateFormat), 10);

  }

  @Test
  void itShouldRemoveMeasurement() throws Exception {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String date = dateFormat.format(LocalDate.now());

    mockMvc.perform(MockMvcRequestBuilders.post("/measurement/remove?clientName=Ivan&date=" + date))
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessages\":[],\"result\":\"Success\"}"));
    verify(measurementService).removeMeasurement("Ivan",LocalDate.parse(date, dateFormat));
  }

  @Test
  void itShouldUpdateMeasurement() throws Exception {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String date = dateFormat.format(LocalDate.now());

    mockMvc.perform(MockMvcRequestBuilders.post("/measurement/update")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"clientName\":\"Ivan\", \"value\": 20, \"date\":\""+ date + "\"}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\": true, \"errorMessages\": [], \"result\": \"Success\"}"));
    verify(measurementService).updateMeasurement("Ivan", LocalDate.parse(date, dateFormat), 20);
  }
}