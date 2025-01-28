package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_TerminalsByMerchant;

public class SSEnt_TerminalsByMerchantRq extends SSEnt_BaseRq {

	@Valid
	@NotNull(message = "ENT_INITIATOR_NULL")
	@JsonProperty("initiator")
	public SSEnt_TerminalsByMerchant initiator ; 
}
