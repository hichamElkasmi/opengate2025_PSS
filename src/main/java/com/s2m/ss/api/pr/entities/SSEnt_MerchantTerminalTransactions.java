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
public class SSEnt_MerchantTerminalTransactions {

	@JsonProperty("merchantId")
	private String merchantId;
	@JsonProperty("terminalId")
	private String terminalId;
	@JsonProperty("transactionType")
	private String transactionType;
	@JsonProperty("authenticationNumber")
	private String authenticationNumber;
	@JsonProperty("status")
	private String status;
	@JsonProperty("institution")
	private String institution;
	@JsonProperty("startDate")
	private String startDate;
	@JsonProperty("endDate")
	private String endDate;
	@JsonProperty("start")
	private String start;
	@JsonProperty("end")
	private String end;
}
