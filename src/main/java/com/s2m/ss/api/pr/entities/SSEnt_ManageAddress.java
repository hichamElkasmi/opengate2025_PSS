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
public class SSEnt_ManageAddress {

	@JsonProperty("bankcode")
	private String bankcode ;
	@JsonProperty("operation")
	private String operation ;
	@JsonProperty("entity")
	private String entity ;
	@JsonProperty("entityid")
	private String entityid ;
	@JsonProperty("address1")
	private String address1 ;
	@JsonProperty("address2")
	private String address2 ;
	@JsonProperty("adr_STR")
	private String adr_STR ;
	@JsonProperty("city")
	private String city ;
	@JsonProperty("phone")
	private String phone ;
	@JsonProperty("zipaddress")
	private String zipaddress ;
	@JsonProperty("fax")
	private String fax ;
	@JsonProperty("branchcode")
	private String branchcode ;
	@JsonProperty("adr_id")
	private String adr_id ;
	@JsonProperty("adr_type")
	private String adr_type;
}
