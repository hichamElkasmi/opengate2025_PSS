package com.s2m.ss.api.pr.entities.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_StatusTransaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_CheckTransactionRs extends SSEnt_BaseRs{

	@JsonProperty("transtatus")
	private SSEnt_StatusTransaction tranStatus;

}
