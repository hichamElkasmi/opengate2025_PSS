package com.s2m.ss.api.pr.entities;

import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.time.Instant;

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
public class SSEnt_Transaction extends SSEnt_MiniStatement {
	
	@JsonProperty("convertedamount")
	private Double convertedAmount;
	@JsonProperty("accountcurrency")
	private SSEnt_Currency accountCurrency;	
	@JsonProperty("longlabel")
	private String longLabel;
	@Schema(description = "0:débit, 1:crédit")
	@JsonProperty("action")
	protected Integer sensTransaction;
	
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
		convertedAmount = stream.readDouble();
		originCurrency = new SSEnt_Currency(stream.readInt(),stream.readString());
		accountCurrency = new SSEnt_Currency(stream.readInt(),stream.readString());

		try{
            Instant instant = (stream.readTimestamp()).toInstant() ;
            dateTime = instant.getEpochSecond();
        }catch (Exception ex) {
        	dateTime = null;
        }
		
		longLabel = stream.readString();
		shortLabel = stream.readString();
		retRefNbr = stream.readString();
		autCode =  stream.readLong();
		tranStatus = new SSEnt_StatusTransaction(stream.readInt(), stream.readString(), stream.readString());
		sensTransaction = stream.readInt();
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Transaction");
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
	}
	
}
