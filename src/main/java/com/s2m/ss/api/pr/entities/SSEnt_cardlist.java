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
public class SSEnt_cardlist {

	@JsonProperty("account")
	private String account;
	@JsonProperty("card")	
	private String card;
	@JsonProperty("customer")
	private String customer;
	@JsonProperty("name_on_card")
	private String name_on_card;
	@JsonProperty("institution")
	private String institution;
	@JsonProperty("start")
	private String start;
	@JsonProperty("end")
	private String end;
	@JsonProperty("pan")
	private String pan;
}
