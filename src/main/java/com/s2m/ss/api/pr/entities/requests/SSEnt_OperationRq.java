package com.s2m.ss.api.pr.entities.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.entities.SSEnt_CardOperations;
import com.s2m.ss.api.pr.entities.SSEnt_Header;
import com.s2m.ss.api.pr.entities.SSEnt_InitiatorCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_OperationRq extends SSEnt_HeadInitCrdRq{

	private SSEnt_CardOperations cardOperation;
	
	public SSEnt_OperationRq(SSEnt_CardOperations cardOperation, SSEnt_InitiatorCard initiatorCard,
			SSEnt_Header header ) {
		this.cardOperation = cardOperation;
		this.initiator = initiatorCard;
		this.header = header;
	}
}
