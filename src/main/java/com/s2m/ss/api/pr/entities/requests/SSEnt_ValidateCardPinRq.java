package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_ValidateCardPin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SSEnt_ValidateCardPinRq  extends SSEnt_HeadInitCrdRq{
	
	@NotNull(message="ENT_OPR_ACTV_NULL")
	@JsonProperty("details")
	private SSEnt_ValidateCardPin details ;

}
