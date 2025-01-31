package com.s2m.ss.api.pr.entities;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

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
public class SSEnt_RestrictedCountries{

	@Schema(description = "0 : No, 1 : Yes, If the parameter 'restriction' = 1:  the only country where the card could be used is sent, If the parameter  'restriction' = 0: the card could be used at any country. Countries object not sent")
	@NotNull(message="CONT_REST_EMPTY")
	@Range(min=0,max=1,message="CONT_REST_VALUE")
	private Integer restriction;
	@Schema(description = "liste de l'objet country, only if  'restriction' = 1, countries where the card could be used")
	@Valid
	private SSEnt_Country[] countries;
	
}
