package com.s2m.ss.api.pr.entities;


import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.s2m.ss.api.pr.config.SS_LoggingImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SSEnt_CreditLimit extends SSEnt_Request implements SQLData{

	@Override
	public String getSQLTypeName() throws SQLException {
		return "CREDITSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_CreditLimit");

		stream.writeDouble(amount);
		stream.writeString(label);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_CreditLimit");
		
	}
	
}
