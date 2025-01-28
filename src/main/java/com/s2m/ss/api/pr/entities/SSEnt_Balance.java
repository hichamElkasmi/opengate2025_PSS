package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.config.SS_LoggingImpl;

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
public class SSEnt_Balance implements SQLData{

	Double available;
	@JsonProperty("actualbalance")
	Double actualBalance;
	@Schema(description = "0 : débiteur, 1 : créditeur")
	@JsonProperty("typeavailable")
	Integer typeAvailable;
	@Schema(description = "0 : débiteur, 1 : créditeur")
	@JsonProperty("typeactual")
	Integer typeActual;
	@JsonProperty("currency")
	private SSEnt_Currency currency;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "BALANCESTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Balance");
		
		available = stream.readDouble();
		actualBalance = stream.readDouble();
		currency = new SSEnt_Currency(stream.readInt(),stream.readString());
		typeAvailable = stream.readInt();
		typeActual = stream.readInt();
		stream.readString();
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Balance");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {

	}
}
