package com.s2m.ss.api.pr.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_PosTerminalList {
	

	@JsonProperty("terminalid")
	private String terminalid; 

	@JsonProperty("institution")
    private String institution;

	@JsonProperty("start")
    private String start;

	@JsonProperty("end")
	private String end;
	
	
}
