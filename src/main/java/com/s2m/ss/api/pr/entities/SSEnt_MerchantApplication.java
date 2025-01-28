
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
public class SSEnt_MerchantApplication {
	@JsonProperty("operation")
	private String operation;
	@JsonProperty("merchantappid")
	private String merchantappid;
	@JsonProperty("institution")
	private String institution;
	@JsonProperty("branch")
	private String branch;
	@JsonProperty("merchantid")
	private String merchantid;
	@JsonProperty("merchantoldnew")
	private String merchantoldnew;
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
	@JsonProperty("ownerrtitle")
	private String ownerrtitle;
	@JsonProperty("ownerfirstname")
	private String ownerfirstname;
	@JsonProperty("ownerlastname")
	private String ownerlastname;
	@JsonProperty("ownerbirthdate")
	private String ownerbirthdate;
	@JsonProperty("ownerbirthlocation")
	private String ownerbirthlocation;
	@JsonProperty("ownernationalid")
	private String ownernationalid;
	@JsonProperty("ownerpassportnumber")
	private String ownerpassportnumber;
	@JsonProperty("ownergender")
	private String ownergender;
	@JsonProperty("ownernationality")
	private String ownernationality;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("mxpaccountnumber")
	private String mxpaccountnumber;
	@JsonProperty("bankaccountnumber")
	private String bankaccountnumber;
	@JsonProperty("accountoldnew")
	private String accountoldnew;
	@JsonProperty("merchantprogram")
	private String merchantprogram;
	@JsonProperty("merchantcategory")
	private String merchantcategory;
	@JsonProperty("category")
	private String category;
	@JsonProperty("paymentmode")
	private OpgEnt_MerPaymentMode[]  paymentmode;
	@JsonProperty("address")
	private OpgEnt_Address[]  address;

}
