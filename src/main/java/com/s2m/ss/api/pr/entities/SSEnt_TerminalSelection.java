package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import org.hibernate.validator.constraints.Range;

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
public class SSEnt_TerminalSelection implements SQLData {

	@Schema(description = "0:POS, 1:ATM")
	@Range(min=0,max=1,message="SLCT_TERTYPE_VALUE")
	@JsonProperty("type")
	private Integer selectType;
	@Schema(description = "1:Active, 2:Inactive, 3:Blocked")
	@Range(min=1,max=3,message="SLCT_TERSTATUT_VALUE")
	@JsonProperty("activation")
	private Integer selectstatus;
	
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "SELECTIONSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {

	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {		
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_TerminalSelection");

		stream.writeInt(selectType==null?-1:selectType);
		stream.writeInt(selectstatus==null?-1:selectstatus);

		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_TerminalSelection");
	}
	
}
