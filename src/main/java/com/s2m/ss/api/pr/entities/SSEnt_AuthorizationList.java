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
public class SSEnt_AuthorizationList {

	@JsonProperty("institution")
	private String institution;
	@JsonProperty("idtrans")
	private String idtrans;
	@JsonProperty("startdate")
	private String startdate;
	@JsonProperty("enddate")
	private String enddate;
	@JsonProperty("transactiontype")
	private String transactiontype;
	@JsonProperty("start")
	private String start;
	@JsonProperty("end")
	private String end;
	
	
}
