package com.s2m.ss.api.pr.entities.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_Atm;
import com.s2m.ss.api.pr.entities.SSEnt_Pos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_TerminalsListRs extends SSEnt_BaseRs{

	@JsonProperty("posterminals")
	private List<SSEnt_Pos> posTerminal;
	@JsonProperty("atmterminals")
	private List<SSEnt_Atm> atmTerminal;


}
