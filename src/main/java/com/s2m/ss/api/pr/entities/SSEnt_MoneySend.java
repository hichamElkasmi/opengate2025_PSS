package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

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
public class SSEnt_MoneySend extends SSEnt_Request implements SQLData{

	@NotBlank(message="PHONE_EMPTY")
	@Size(min=1,max=20,message="PHONE_LENGHT")
	@JsonProperty("phonenum")
	private String phoneNum;
	@NotBlank(message="SECRET_EMPTY")
	@Size(min=1,max=20,message="SECRET_LENGHT")
	@JsonProperty("secretcode")
	private String secretCode;
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		
		return "SMONEYSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_MoneySend");

		stream.writeDouble(amount);
		stream.writeString(label);
		stream.writeString(phoneNum);
		stream.writeString(secretCode);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_MoneySend");
	}	
}
