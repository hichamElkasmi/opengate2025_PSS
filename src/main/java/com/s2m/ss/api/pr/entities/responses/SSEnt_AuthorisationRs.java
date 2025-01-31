package com.s2m.ss.api.pr.entities.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.entities.SSEnt_Authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_AuthorisationRs extends SSEnt_BaseRs{
	
	public SSEnt_AuthorisationRs(SSEnt_BaseRs ent_BaseRs) {
		header = ent_BaseRs.getHeader();
		status = ent_BaseRs.getStatus();
	}
	private SSEnt_Authorization authorization;

}
