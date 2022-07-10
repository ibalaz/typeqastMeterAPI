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
    ServiceResult result = new ServiceResult();
    result.success = true;
    when(measurementService.getMonthlyMeasurement("Ivan", 2020, 1)).thenReturn(result);
    when(measurementService.getMeasurementsForClient("Ivan")).thenReturn(result);
    when(measurementService.getAggregateReadingYear("Ivan", 2020)).thenReturn(result);
    when(measurementService.getReportPerMonth("Ivan", 2020)).thenReturn(result);
    when(measurementService.addNewMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 10)).thenReturn(result);
    when(measurementService.removeMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")))).thenReturn(result);
    when(measurementService.updateMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 20)).thenReturn(result);
  }

  @Test
  void itShouldReturnValidResponseGetMeasurementsForClient() throws Exception {
    mockMvc.perform(get("/measurement/getMeasurement/Ivan"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessageList\":null,\"result\":null}"));
    verify(measurementService).getMeasurementsForClient("Ivan");
  }

  @Test
  void itShouldReturnValidResponseGetAggregateReading() throws Exception {
    mockMvc.perform(get("/measurement/getAggregateReading/Ivan/2020"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessageList\":null,\"result\":null}"));
    verify(measurementService).getAggregateReadingYear("Ivan", 2020);
  }

  @Test
  void itShouldReturnValidResponseGetReportPerMonth() throws Exception {
    mockMvc.perform(get("/measurement/getReportPerMonth?clientName=Ivan&year=2020"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessageList\":null,\"result\":null}"));
    verify(measurementService).getReportPerMonth("Ivan", 2020);
  }

  @Test
  void itShouldReturnValidResponseGetMonthlyMeasurement() throws Exception {
    mockMvc.perform(get("/measurement/getMonthlyMeasurement/2020/1?clientName=Ivan"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{ \"success\":true,\"errorMessageList\":null,\"result\":null }"));
    verify(measurementService).getMonthlyMeasurement("Ivan", 2020, 1);
  }

  @Test
  void itShouldAddNewMeasurement() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/measurement/addNew")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"clientName\": \"Ivan\", \"value\":  10 }"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessageList\":null,\"result\":null}"));
    verify(measurementService).addNewMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 10);

  }

  @Test
  void itShouldRemoveMeasurement() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/measurement/remove?clientName=Ivan&date=10-07-2022"))
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\":true,\"errorMessageList\":null,\"result\":null}"));
    verify(measurementService).removeMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
  }

  @Test
  void itShouldUpdateMeasurement() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/measurement/update")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"clientName\":\"Ivan\", \"value\": 20, \"date\":\"10-07-2022\"}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content()
            .json("{\"success\": true, \"errorMessageList\": null, \"result\": null}"));
    verify(measurementService).updateMeasurement("Ivan", LocalDate.parse("10-07-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 20);
  }
}