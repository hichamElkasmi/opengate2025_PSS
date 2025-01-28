package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_Header;
import com.s2m.ss.api.pr.entities.SSEnt_InitiatorCardAccount;
import com.s2m.ss.api.pr.entities.SSEnt_Transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_TransfertRq extends SSEnt_HeadInitCrdAccRq{
	
	@Valid
	@NotNull(message="ENT_TRANS_NULL")
	@JsonProperty("transfer")
	private SSEnt_Transfer transferTrx;
	
	public SSEnt_TransfertRq (SSEnt_Header header, SSEnt_InitiatorCardAccount initiator,
			SSEnt_Transfer transferTrx) {
		
		this.header = header;
		this.initiator = initiator;
		this.transferTrx = transferTrx;
	}
}
