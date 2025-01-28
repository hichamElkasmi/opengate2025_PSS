package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.entities.SSEnt_DF;
import com.s2m.ss.api.pr.entities.SSEnt_Header;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_BaseRq  {
	
	@Valid
	@NotNull(message="ENT_HEADER_NULL")
	public SSEnt_Header header;

	public SSEnt_Header getHeader() {
		if(header == null)
			header = SSEnt_DF.DEFAULT_HEAD;
		return header;
	}
	
}