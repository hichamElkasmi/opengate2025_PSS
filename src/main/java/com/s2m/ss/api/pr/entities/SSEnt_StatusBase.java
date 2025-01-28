package com.s2m.ss.api.pr.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class SSEnt_StatusBase {
	
	@Schema(description = "Code d'état et description de la réponse :\r\n"
			+ "		0: Approuvée\r\n"
			+ "		1: En attente d'approbation\r\n"
			+ "		2: Non approuvée\r\n"
			+ "		3: Erreur  (avec code erreur)\r\n"
			+ "Code d'état et description en cas d'une transaction :\r\n"
			+ "		0: En attente de validation\r\n"
			+ "		1: Validée\r\n"
			+ "		2: Annulée\r\n"
			+ "		3: Rejetée (avec code erreur)")
	@JsonProperty("statuscode")
	protected String statusCode;
	@JsonProperty("statusdsc")
	protected String statusDsc;
	@JsonProperty("errorcode")
	protected String errorCode;
	@JsonProperty("errormsg")
	protected String errorMsg;
	
}
