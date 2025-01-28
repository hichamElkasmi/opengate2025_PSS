package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.s2m.ss.api.pr.entities.SSEnt_PrepaidApplication;
import com.s2m.ss.api.pr.entities.SSEnt_PrepaidApplicationList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SSEnt_PrepaidApplicationListRq  extends SSEnt_BaseRq {
	@Valid
	@NotNull(message="ENT_INITIATOR_NULL")
	protected SSEnt_PrepaidApplicationList filter;
	
	}


