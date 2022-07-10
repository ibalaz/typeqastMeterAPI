package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.service.ClientService;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller providing endpoints to manipulate client data.
 */
@Tag(name = "Client API", description = "Client endpoints, for getting client names or details")
@Slf4j
@RestController()
@RequestMapping("client")
@AllArgsConstructor
public class ClientController {

  /**
   * The Client service.
   */
  ClientService clientService;

  /**
   * Gets all client names.
   *
   * @return {List<String>} all client names
   */
  @Operation(summary = "This endpoint gets all client names")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Fetched all clients",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @GetMapping(path="/getAll")
  public ServiceResult getAllClients() {
    log.info("Calling getAllClients");
    return clientService.getAllClients();
  }

  /**
   * Gets all clients details.
   *
   * @return {List<Client>} the all clients details
   */
  @Operation(summary = "This endpoint gets all client details")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Fetched all clients details",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @GetMapping("/getAllDetails")
  public ServiceResult getAllClientsDetails() {
    log.info("Calling getAllClientsDetails");
    return  clientService.getAllClientsDetails();
  }
}
