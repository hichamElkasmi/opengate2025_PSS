package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

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
public class SSEnt_Bank  implements SQLData{

	@NotNull(message="BANK_EMPTY")
	@Max(value = 99999999L, message = "BANK_LENGHT")
	@JsonProperty("bankcode")
	private Integer bankcode;
	@JsonProperty("bankiden")
	private String bankiden;
	@JsonProperty("bankname")
	private String bankname;
	@JsonProperty("bankdescriptions")
	private String bankdescriptions;
	@JsonProperty("bankaddparams")
	private String bankaddparams;
	

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "BANKSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Country");
		
		bankcode = stream.readInt();
		bankiden = stream.readString();
		bankname = stream.readString();
		bankdescriptions = stream.readString();
		bankaddparams = stream.readString();
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Country");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Country");
		stream.writeInt(bankcode);
		stream.writeString(null);
		stream.writeString(null);
		stream.writeString(null);
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Country");
	}

}
