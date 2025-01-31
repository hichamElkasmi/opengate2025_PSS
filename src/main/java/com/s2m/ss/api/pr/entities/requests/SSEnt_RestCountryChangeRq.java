package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_RestrictedCountries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_RestCountryChangeRq extends SSEnt_HeadInitCrdRq{

	@Valid
	@NotNull(message="ENT_COUNTRIES_NULL")
	@JsonProperty("cardrestrictedcountry")
	private SSEnt_RestrictedCountries cardRestrictedCountry;
}
