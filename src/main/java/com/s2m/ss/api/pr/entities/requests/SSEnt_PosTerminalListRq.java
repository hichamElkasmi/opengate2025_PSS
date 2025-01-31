package com.s2m.ss.api.pr.entities.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.s2m.ss.api.pr.entities.SSEnt_PosTerminalList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SSEnt_PosTerminalListRq extends SSEnt_BaseRq {
	@Valid
	@NotNull(message="ENT_INITIATOR_NULL")
	protected SSEnt_PosTerminalList filter;
}