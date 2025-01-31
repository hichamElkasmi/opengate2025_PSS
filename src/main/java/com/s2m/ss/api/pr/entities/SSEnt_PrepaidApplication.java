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
public class SSEnt_PrepaidApplication {
	
	@JsonProperty("operation")
	private String operation;
	@JsonProperty("customerid")
	private String customerid;
	@JsonProperty("accountnumber")
	private String accountnumber;
	@JsonProperty("applicationid")
	private String applicationid;
	@JsonProperty("cardprogramcode")
	private String cardprogramcode;
	@JsonProperty("prepaidprogramcode")
	private String prepaidprogramcode;
	@JsonProperty("nameoncard")
	private String nameoncard;
	@JsonProperty("firstname")
	private String firstname;
	@JsonProperty("middlename")
	private String middlename;
	@JsonProperty("lastname")
	private String lastname;
	@JsonProperty("encrypted_pin")
	private String encrypted_pin;
	@JsonProperty("middlenamear")
	private String middlenamear;
	@JsonProperty("lastnamear")
	private String lastnamear;
	@JsonProperty("customertype")
	private String customertype;
	@JsonProperty("accounttype")
	private String accounttype;
	@JsonProperty("autoreload")
	private String autoreload;
	@JsonProperty("currencycode")
	private String currencycode;
	@JsonProperty("customeraddress")
	private String customeraddress;
	@JsonProperty("phonenumber")
	private String phonenumber;
	@JsonProperty("citycode")
	private String citycode;
	@JsonProperty("branchcode")
	private String branchcode;
	@JsonProperty("nationalid")
	private String nationalid;
	@JsonProperty("nationalidtype")
	private String nationalidtype;
	@JsonProperty("birthdate")
	private String birthdate;
	@JsonProperty("institution")
	private String institution;
	@JsonProperty("instant")
    private String instant;
	
}
