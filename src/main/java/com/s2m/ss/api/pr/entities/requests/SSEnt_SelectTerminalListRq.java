package com.s2m.ss.api.pr.entities.requests;


import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.entities.SSEnt_TerminalSelection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_SelectTerminalListRq extends SSEnt_HeadInitBnkRq{

	@Valid
	private SSEnt_TerminalSelection filter;
}
