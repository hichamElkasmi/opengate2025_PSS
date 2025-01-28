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
public class SSEnt_AtmMonitoring {

	@JsonProperty("atm_group")
	private String atm_group;
	@JsonProperty("atm_mode")
	private String atm_mode;
	@JsonProperty("terminal")
	private String terminal;
	@JsonProperty("institution")
	private String institution;
}
