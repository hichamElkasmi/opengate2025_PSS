package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.config.SS_LoggingImpl;

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
public class SSEnt_Address implements SQLData {

	@Size(min=0,max=80,message="ENRL_ADR1_LENGHT")
	private String addr1;
	@Size(min=0,max=80,message="ENRL_ADR2_LENGHT")
	private String addr2;
	@Valid
	private SSEnt_Country country;
	@Valid
	private SSEnt_City city;
	@JsonProperty("postalcode")
	@Size(min=1,max=20,message="NRL_ADRPSTL_LENGHT")
	private String postalCode;
	@NotBlank(message="PHONE_EMPTY")
	@Size(min=1,max=20,message="PHONE_LENGHT")
	@JsonProperty("phonenum")
	private String phoneNum;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "ADDRESSSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Address");
		
		addr1 = stream.readString();
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Address");
		
	}
	public void writeSQL(SQLOutput stream) throws SQLException {
	}

}
