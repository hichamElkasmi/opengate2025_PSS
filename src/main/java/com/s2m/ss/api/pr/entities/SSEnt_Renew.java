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
public class SSEnt_Renew {
	
	
	@JsonProperty("card")private String card;
	@JsonProperty("expiry")private String expiry;
	@JsonProperty("renew_fe")	private String renew_fe;
	@JsonProperty("pin_recalcul_fee")	private String pin_recalcul_fee;
	@JsonProperty("pin_generation")	private String pin_generation;
	@JsonProperty("institution")	private String institution;
	
	
}
