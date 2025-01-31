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
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SSEnt_Merchant {

	@JsonProperty("agentiden")
	private String agentIden;
	@JsonProperty("agentname")
	private String agentName;
	@JsonProperty("agentphone")
	private String agentPhone;
	@JsonProperty("agentaddr")
	private String agentAddr;

}
