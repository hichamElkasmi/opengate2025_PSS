package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.entities.SSEnt_CustomerList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_CustomerListRq extends SSEnt_BaseRq {


	@Valid
	@NotNull(message="ENT_INITIATOR_NULL")
	protected SSEnt_CustomerList filter;
	
}
