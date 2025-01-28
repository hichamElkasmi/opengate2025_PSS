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
public class SSEnt_PosTerminalApplication {
	@JsonProperty("operation")
	private String operation;
	@JsonProperty("terminalid")
	private String terminalid;
	@JsonProperty("terminalname")
	private String terminalname;
	@JsonProperty("merchantid")
	private String merchantid;
	@JsonProperty("currencycode")
	private String currencycode;
	@JsonProperty("configuration")
	private String configuration;
	@JsonProperty("terminalstatus")
	private String terminalstatus;
	@JsonProperty("hsmkeycode")
	private String hsmkeycode;
	@JsonProperty("serialnumber")
	private String serialnumber;
	@JsonProperty("applicationversion")
	private String applicationversion;
	@JsonProperty("institution")
	private String institution;
	@JsonProperty("purchase")
	private String purchase;
	@JsonProperty("voidis")
	private String voidis;
	@JsonProperty("billpayment")
	private String billpayment;
	@JsonProperty("moneytransfer")
	private String moneytransfer;
	@JsonProperty("pinchange")
	private String pinchange;
	@JsonProperty("cashadvance")
	private String cashadvance;
	@JsonProperty("evoucher")
	private String evoucher;
	@JsonProperty("refund")
	private String refund;
	@JsonProperty("balanceinquiry")
	private String balanceinquiry;
	@JsonProperty("ministatement")
	private String ministatement;
}
