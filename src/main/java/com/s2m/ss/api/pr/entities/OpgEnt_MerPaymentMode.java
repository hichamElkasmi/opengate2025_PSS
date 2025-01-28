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
public class OpgEnt_MerPaymentMode {
	
	@JsonProperty("paymentmodecode")
	private String paymentmodecode;
	@JsonProperty("paymentmodedefault")
	private String paymentmodedefault;
	@JsonProperty("paymentmoderate")
	private String paymentmoderate;
}
