package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.time.Instant;

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
public class SSEnt_MiniStatement implements SQLData{
	
	@JsonProperty("originamount")
	protected Double originAmount;
	@JsonProperty("origincurrency")
	protected SSEnt_Currency originCurrency;
	@JsonProperty("datetime")
	protected Long dateTime;
	@JsonProperty("shortlabel")
	protected String shortLabel;
	@JsonProperty("retrefnbr")
	protected String retRefNbr;
	@JsonProperty("autcode")
	protected Long autCode;
	@JsonProperty("transtatus")
	protected SSEnt_StatusTransaction tranStatus;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "TRANSACTIONSTRUCT";
	}
	
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Transaction");
		
			originAmount = stream.readDouble();
			stream.readDouble();
			originCurrency = new SSEnt_Currency(stream.readInt(),stream.readString());
			stream.readInt();
			stream.readString();
			try{
	            Instant instant = (stream.readTimestamp()).toInstant() ;
	            dateTime = instant.getEpochSecond();
	        }catch (Exception ex) {
	        	dateTime = null;
	        }
			stream.readString();
			shortLabel = stream.readString();
			retRefNbr = stream.readString();
			autCode =  stream.readLong();
			tranStatus = new SSEnt_StatusTransaction(stream.readInt(), stream.readString(), stream.readString());
			stream.readInt();
			
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Transaction");
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
	}
	
}
