package com.s2m.ss.api.pr.entities;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

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
public class SSEnt_Currency {
	
	@Schema(description = "code ISO numérique de la devise")
	@NotNull(message="REQ_CURRENCY_EMPTY")
	@Range(min=0,max=999,message="REQ_CURRENCY_LENGHT")
	@JsonProperty("currencyisonum")
	private Integer currencyIsoNum;
	@Schema(description = "code ISO Alpha de la devise (EUR, BIF, USD, …) ")
	@JsonProperty("currencyisoalpha")
	private String currencyIsoAlpha;
}
