package com.s2m.ss.api.pr.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SSEnt_Terminal {

	protected String iden;
	protected String label;
	@Schema(description = "1:Active, 2:Inactive, 3:Blocked")
	protected Integer activation;

}
