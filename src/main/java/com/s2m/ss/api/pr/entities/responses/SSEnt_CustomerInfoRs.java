package com.s2m.ss.api.pr.entities.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.entities.SSEnt_Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_CustomerInfoRs extends SSEnt_BaseRs{

	private SSEnt_Customer customer;
}
