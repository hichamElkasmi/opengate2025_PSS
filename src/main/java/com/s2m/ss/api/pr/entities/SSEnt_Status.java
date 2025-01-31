package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.config.SS_LoggingImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(callSuper=true)
public class SSEnt_Status extends SSEnt_StatusBase implements SQLData{

	public SSEnt_Status(SSEnt_InternelError status, SSEnt_InternelError error) {

		assignvalue(status, error);
	}
	
	public SSEnt_Status(SSEnt_InternelError status, String codeErreur, String msgErreur) {

		assignvalue(status, new SSEnt_InternelError(codeErreur, msgErreur));
		
	}
	
	public SSEnt_Status(SSEnt_InternelError status, SSEnt_InternelError error, String msgErreur) {

		assignvalue(status, new SSEnt_InternelError(error.getCodeErreur(), error.getMsgErreur() + msgErreur));
	}
	
	@JsonIgnore
	public void assignvalue(SSEnt_InternelError status, SSEnt_InternelError error) {
		if(status != null) {
			statusCode = status.getCodeErreur();
			statusDsc = status.getMsgErreur();
			if(statusDsc != null) statusDsc = statusDsc.trim();
		}
		if(error != null) {
			errorCode = error.getCodeErreur();
			errorMsg = error.getMsgErreur();
			if(errorMsg != null) errorMsg = errorMsg.trim();
		}
	}
	
	
	@JsonIgnore
	public void assignvalue(SSEnt_InternelError error) {
		if(error != null) {
			errorCode = error.getCodeErreur();
			errorMsg = error.getMsgErreur();
			if(errorMsg != null) errorMsg = errorMsg.trim();
		}
	}
	
	@JsonIgnore
	public boolean isApproved() {
		if (statusCode.equalsIgnoreCase("0")) {
			return true;
		}
		else
			return false;
	}
	
	@JsonIgnore
	public boolean isAuthorized() {
		if (errorCode.equalsIgnoreCase("00000")) {
			return true;
		}
		else
			return false;
	}
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "STATUSSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Status");
		
	     stream.readString();
		errorCode = stream.readString();
		errorMsg = stream.readString();
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end  readSQL SSEnt_Status");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
	}
}
