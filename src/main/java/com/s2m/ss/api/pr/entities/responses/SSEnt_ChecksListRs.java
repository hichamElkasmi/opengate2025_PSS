package com.s2m.ss.api.pr.entities.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.entities.SSEnt_CheckBook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_ChecksListRs extends SSEnt_BaseRs{

	@JsonProperty("checkbooks")
	private List<SSEnt_CheckBook> checkbooks;

}
