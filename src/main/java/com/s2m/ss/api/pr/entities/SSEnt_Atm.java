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
public class SSEnt_Atm extends SSEnt_Terminal  implements SQLData{

	@JsonProperty("atmaddr")
	private String Address;
	@Schema(description = "1:IN SERVICE, 2:OUT OF SERVICE, 3:SUSPENDED, 4:SUPERVISOR, 5:CRITICAL, 6:OFF LINE")
	@JsonProperty("onlinestatus")
	private Integer status;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "ATMSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Atm");
		
		iden = stream.readString();
		label = stream.readString();
		activation = stream.readInt();
		Address = stream.readString();
		status = stream.readInt();

		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Atm");
		
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
		
	}
}
