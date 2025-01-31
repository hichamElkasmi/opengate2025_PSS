package com.s2m.ss.api.pr.entities;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class SSEnt_Request{

	@NotNull(message="REQ_AMOUNT_EMPTY")
    @DecimalMin(value = "0.0", inclusive = false,message="REQ_AMOUNT_VALUE")
    @Digits(integer=12, fraction=2,message="REQ_AMOUNT_LENGHT")
	protected Double amount;
	@Size(min=0,max=50,message="FEEACCOUNT_LENGHT")
	protected String label;
	
}
