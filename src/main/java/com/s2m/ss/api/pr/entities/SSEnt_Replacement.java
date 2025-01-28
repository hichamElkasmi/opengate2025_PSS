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
public class SSEnt_Replacement {
	 
	@JsonProperty("card")private String card;
	@JsonProperty("expiry")private String expiry;
	@JsonProperty("replacement_fee") private String replacement_fee;
	@JsonProperty("pin_recalcul_fee") private String pin_recalcul_fee;
	@JsonProperty("new_pan") private String new_pan;
	@JsonProperty("pin_generation") private String pin_generation;
	@JsonProperty("replacement_reason") private String replacement_reason;
	@JsonProperty("name_in_card") private String name_in_card;
	@JsonProperty("institution")private String institution;
	@JsonProperty("destination") private String destination;
	
}
