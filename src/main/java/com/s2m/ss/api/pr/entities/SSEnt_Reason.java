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
public class SSEnt_Reason  implements SQLData{

	@NotNull(message = "RSN_CODE_EMPTY")
	@Max(value = 9999999999L, message = "RSN_CODE_LENGHT")
	@JsonProperty("reasoncode")
	private Integer reasonCode;
	@JsonProperty("reasoniden")
	private String reasonIden;
	@JsonProperty("reasonname")
	private String reasonName;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "REASONSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Reason");
		
		reasonCode = stream.readInt();
		reasonIden = stream.readString();
		reasonName = stream.readString();
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Reason");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Reason");
		
		stream.writeInt(reasonCode);
		stream.writeString(null);
		stream.writeString(null);
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Reason");
	}
}
