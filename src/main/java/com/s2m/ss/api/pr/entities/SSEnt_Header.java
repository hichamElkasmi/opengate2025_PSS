package com.s2m.ss.api.pr.entities;


import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class SSEnt_Header {
	
	@Schema(description = "identification de message en format UUID", example = "8876d2d5-3046-4c8f-8cc9-d661fa8a6d4c", required = true)
	@JsonProperty("idmsg")
	@NotNull(message="HEAD_ID_EMPTY")
	public UUID idMsg;

}
