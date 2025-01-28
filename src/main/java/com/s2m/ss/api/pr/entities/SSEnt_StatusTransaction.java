package com.s2m.ss.api.pr.entities;

import static com.s2m.ss.api.pr.entities.SSEnt_DF.getMsg;

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
public class SSEnt_StatusTransaction extends SSEnt_StatusBase implements SQLData{
	
	
	
	public SSEnt_StatusTransaction(int index, String codeErreur, String msgErreur) {

		initData(index, codeErreur, msgErreur);
	}
	
	private void initData(int index, String codeErreur, String msgErreur) {
		SSEnt_InternelError status = null;
		switch (index) {
			case 0:
				status = getMsg("ST_PENDING");
				break;
			case 1:
				status = getMsg("ST_VALID");
				break;
			case 2:
				status = getMsg("ST_CANCEL");
				break;
			case 3:
				status = getMsg("ST_ERRORTRX");
				break;
			default:
				break;
		}
		
		assignvalue(status, new SSEnt_InternelError(codeErreur, msgErreur));
	}
	
	public SSEnt_StatusTransaction(SSEnt_InternelError status, SSEnt_InternelError error, String msgErreur) {

		assignvalue(status, new SSEnt_InternelError(error.getCodeErreur(), error.getMsgErreur() + msgErreur));
	}
	
	@JsonIgnore
	private void assignvalue(SSEnt_InternelError status, SSEnt_InternelError error) {
		if(status != null) {
			statusCode = status.getCodeErreur();
			statusDsc = status.getMsgErreur();
			if(statusDsc != null) statusDsc = statusDsc.trim();
		}
		if(error != null) {
			errorCode = error.getCodeErreur();
			errorMsg = error.getMsgErreur();
			if(errorCode != null) errorCode = errorCode.trim();
			if(errorMsg != null) errorMsg = errorMsg.trim();
		}
	}
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "TRANSSTATUSSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_StatusTransaction");
		
		statusCode =  String.format("%.0f", stream.readDouble());
		errorCode = stream.readString();
		errorMsg = stream.readString();
		initData(Integer.valueOf(statusCode), errorCode, errorMsg);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_StatusTransaction");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
	}
}
