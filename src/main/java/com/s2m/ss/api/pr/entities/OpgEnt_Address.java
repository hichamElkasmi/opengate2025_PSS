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
public class OpgEnt_Address {

	@JsonProperty("addresstype")
	private String addresstype;
	@JsonProperty("addresscorresp")
	private String addresscorresp;
	@JsonProperty("address1")
	private String address1;
	@JsonProperty("address2")
	private String address2;
	@JsonProperty("addressstre")
	private String addressstre;
	@JsonProperty("addresszipd")
	private String addresszipd;
	@JsonProperty("phonenumber1")
	private String phonenumber1;
	@JsonProperty("phonenumber2")
	private String phonenumber2;
	@JsonProperty("fax")
	private String fax;
	@JsonProperty("email")
	private String email;
	@JsonProperty("city")
	private String city;
	@JsonProperty("postalcode")
	private String postalcode;
	@JsonProperty("country")
	private String country;
	
}
