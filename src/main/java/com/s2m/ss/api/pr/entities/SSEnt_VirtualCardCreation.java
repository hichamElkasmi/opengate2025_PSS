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
public class SSEnt_VirtualCardCreation {
	@JsonProperty("merchantId")
	private String applicationid;
	@JsonProperty("customerid")
	private String customerid;
	@JsonProperty("accountnumber")
	private String accountnumber;
	@JsonProperty("cardprogramcode")
	private String cardprogramcode;
	@JsonProperty("nameoncard")
	private String nameoncard;
	@JsonProperty("firstname")
	private String firstname;
	@JsonProperty("middlename")
	private String middlename;
	@JsonProperty("lastname")
	private String lastname;
	@JsonProperty("firstnamear")
	private String firstnamear;
	@JsonProperty("middlenamear")
	private String middlenamear;
	@JsonProperty("lastnamear")
	private String lastnamear;
	@JsonProperty("authoreload")
	private String authoreload;
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
	@JsonProperty("bankaccounttype")
	private String bankaccounttype;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("title")
	private String title;
	@JsonProperty("maritalstatus")
	private String maritalstatus;
	@JsonProperty("country")
	private String country;
	@JsonProperty("debitprogram")
	private String debitprogram;
	@JsonProperty("addresstype")
	private String addresstype;
	@JsonProperty("zipaddress")
	private String zipaddress;
	@JsonProperty("fax")
	private String fax;
	@JsonProperty("language")
	private String language;
	@JsonProperty("birthdatelocation")
	private String birthdatelocation;
	@JsonProperty("corporatename")
	private String corporatename;
	@JsonProperty("email")
	private String email;
	@JsonProperty("option")
	private String option;
	@JsonProperty("cardNumber")
	private String cardNumber;
}
