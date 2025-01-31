package com.s2m.ss.api.pr.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class SSEnt_VoidTrx extends SSEnt_Initiator{


	@NotBlank(message="RRN_EMPTY")
	@Size(min=1,max=12,message="RRN_LENGHT")
    @Pattern(regexp = "[0-9]+",message="RRN_DIGITS")
	@JsonProperty("rrnbr")
	private String rrnbr;
	
}
