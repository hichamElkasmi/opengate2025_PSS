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
public class SSEnt_MerchantUpdateNoApplication {
	
	@JsonProperty("operation")
	private String operation;
	@JsonProperty("merchantid")
	private String merchantid;
	@JsonProperty("institution")
	private String institution;
	@JsonProperty("branch")
	private String branch;
	@JsonProperty("corporatename")
	private String corporatename;
	@JsonProperty("nameDBA")
	private String nameDBA;
	@JsonProperty("companytype")
	private String companytype;
	@JsonProperty("licence")
	private String licence;
	@JsonProperty("serialnumber")
	private String serialnumber;
	@JsonProperty("commercialregister")
	private String commercialregister;
	@JsonProperty("capitalamount")
	private String capitalamount;
	@JsonProperty("socialsecuritynumber")
	private String socialsecuritynumber;
	@JsonProperty("taxidentity")
	private String taxidentity;
	@JsonProperty("contractnumber")
	private String contractnumber;
	@JsonProperty("merchantprogram")
	private String merchantprogram;
	@JsonProperty("paymentmode")
	private OpgEnt_MerPaymentMode[] paymentmode;
	@JsonProperty("address")
	private OpgEnt_Address[]  address;
	
}
