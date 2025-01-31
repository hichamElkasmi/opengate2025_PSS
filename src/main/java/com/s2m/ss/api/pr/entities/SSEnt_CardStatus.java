package com.s2m.ss.api.pr.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SSEnt_CardStatus  {

	@Schema(description = "0:inactive, 1:active")
	private Short activation;
	@Schema(description = "0:opposée, 1:non opposée")
	private Short opposition;
	@Schema(description = "0:inactive, 1:active")
	private Short moto;
	@Schema(description = "0:inactive, 1:active")
	private Short ecom;
	@Schema(description = "1:Ready For Personalization, 2:Personalized, 3:In Instance, 4:Ready For Validation, 5:Rejected")
	private Short perso;
	
}
