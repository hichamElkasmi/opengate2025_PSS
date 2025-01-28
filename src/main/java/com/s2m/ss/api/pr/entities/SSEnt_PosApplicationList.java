package com.s2m.ss.api.pr.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_PosApplicationList {
	
	
	
	@JsonProperty("applicationid")
	private String applicationid;
	@JsonProperty("institution")
    private String institution;
	@JsonProperty("start")
    private String start;
	@JsonProperty("end")
    private String end;
}
