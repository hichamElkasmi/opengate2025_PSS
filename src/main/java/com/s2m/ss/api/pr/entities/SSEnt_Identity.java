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
public class SSEnt_Identity  implements SQLData{
	
	@Schema(description = "1:Civil ID, 2:Passport, 3:Driver License, 4:Employment ID, 5:Residence Card, 6:Family Book, 7:Social Sec. Num.")
	@JsonProperty("identype")
	Integer idenType;
	@JsonProperty("idenvalue")
	String idenValue;
	

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "IDENSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Identity");
		
		idenType = stream.readInt();
		idenValue = stream.readString();

		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Identity");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
	}
}
