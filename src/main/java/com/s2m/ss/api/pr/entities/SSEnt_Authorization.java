package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

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
public class SSEnt_Authorization implements SQLData{

	@JsonProperty("authidres")
	private String authIdRes;
	@JsonProperty("retrefnbr")
	private String retRefNbr;
	@JsonProperty("serverdate")
	private Long serverDate;
	@JsonProperty("adddata")
	protected String addData;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "AUTHORIZATIONSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Authorization");
		
		authIdRes = stream.readString();
		retRefNbr = stream.readString();
		
		try{
            Instant instant = (stream.readTimestamp()).toInstant() ;
            serverDate = instant.getEpochSecond();
        }catch (Exception ex) {
        	serverDate = null;
        }
        
		addData = stream.readString();
		if(addData!=null) {
			try{
				Date dateInst = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(addData);
	            Instant instant = dateInst.toInstant();
	            addData = String.valueOf(instant.getEpochSecond());
	        }catch (Exception ex) {
	        	addData = null;
	        }
		}
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Authorization");
        
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
