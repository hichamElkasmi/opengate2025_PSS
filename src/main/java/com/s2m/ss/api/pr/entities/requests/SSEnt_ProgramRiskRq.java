package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_ProgramRisk;


public class SSEnt_ProgramRiskRq extends SSEnt_BaseRq {

	@Valid
	@NotNull(message="ENT_INITIATOR_NULL")
	@JsonProperty("filter")
	protected SSEnt_ProgramRisk  filter;
}
