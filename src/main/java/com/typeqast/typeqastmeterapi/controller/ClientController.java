package com.typeqast.typeqastmeterapi.controller;

import com.typeqast.typeqastmeterapi.model.Client;
import com.typeqast.typeqastmeterapi.service.ClientService;
import com.typeqast.typeqastmeterapi.util.ServiceResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
   * @return {List} list of all client names
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
   * @return {List} list of all clients details
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
    return clientService.getAllClientsDetails();
  }

  /**
   * Gets clients meterId.
   *
   * @param clientName the client name
   * @return the clients meterid
   */
  @Operation(summary = "This endpoint gets clients meterId")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Fetched clients meter id",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @GetMapping("/getClientsMeter")
  public ServiceResult getClientsMeter(@RequestParam String clientName) {
    log.info("Calling getClientsMeter");
    return clientService.getClientsMeterId(clientName);
  }

  /**
   * Add new client.
   *
   * @param client the client
   * @return the service result
   */
  @Operation(summary = "This endpoint adds new client")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "New client added",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @PostMapping("/addNewClient")
  public ServiceResult addNewClient(@RequestBody Client client) {
    log.info("Calling addNewCLient with param {}", client);
    return clientService.addNewClient(client);
  }

  /**
   * Update client meter id.
   *
   * @param clientName the client name
   * @param newMeter   the new meter
   * @return the service result
   */
  @Operation(summary = "This endpoint updates clients meterId")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successful update of clients meter id",
          content = {@Content(mediaType = "application/json")}
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not available",
          content = @Content
      )
  })
  @PostMapping("/updateClientsMeter")
  public ServiceResult updateClientMeter (@RequestParam String clientName, @RequestParam String newMeter) {
    log.info("calling updateClientsMeter client {}, new meter id {}", clientName, newMeter);
    return clientService.updateClientsMeter(clientName, newMeter);
  }
}
