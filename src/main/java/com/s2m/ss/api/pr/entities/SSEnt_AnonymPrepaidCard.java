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
public class SSEnt_AnonymPrepaidCard {

	@JsonProperty("bankcode")
	public String  bankcode ;
	@JsonProperty("currencyid")
	public String  currencyid ;
	@JsonProperty("cardprogramcode")
	public String  cardprogramcode ;
	@JsonProperty("prepaidprogramcode")
	public String  prepaidprogramcode ;
	@JsonProperty("quantity")
	public String  quantity;
	@JsonProperty("branchcode")
	public String  branchcode ;
	@JsonProperty("cardcategory")
	public String  cardcategory ;
	@JsonProperty("target_audience")
	public String  target_audience ;
	@JsonProperty("initamount")
	public String  initamount ;
}
