package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_CreditLimit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_SetCreditLimitRq extends SSEnt_HeadInitCrdRq{
	
	@Valid
	@NotNull(message="ENT_CRDLIMIT_NULL")
	@JsonProperty("creditlimit")
	private SSEnt_CreditLimit creditLimit;
}
