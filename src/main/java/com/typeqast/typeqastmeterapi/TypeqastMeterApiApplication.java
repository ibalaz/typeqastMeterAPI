package com.typeqast.typeqastmeterapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Typeqast meter api application.
 */
@SpringBootApplication
@OpenAPIDefinition
public class TypeqastMeterApiApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
		SpringApplication.run(TypeqastMeterApiApplication.class, args);
	}

}
