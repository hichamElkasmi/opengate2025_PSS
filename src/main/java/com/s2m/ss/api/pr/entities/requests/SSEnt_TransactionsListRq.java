package com.s2m.ss.api.pr.entities.requests;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.entities.SSEnt_Filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_TransactionsListRq extends SSEnt_HeadInitCrdRq{

	@Valid
	@NotNull(message="ENT_FILTER_NULL")
	private SSEnt_Filter filter;
}
