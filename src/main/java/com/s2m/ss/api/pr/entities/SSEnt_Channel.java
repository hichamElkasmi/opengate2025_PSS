package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.NotNull;

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
@ToString(callSuper=true)
public class SSEnt_Channel  implements SQLData{
	
	@Schema(description = "1:GAB, 2:TPE, 3:ECOM, 4:Mobile, 5:kiosque, 6:Autre canal")
	@NotNull(message="CHN_CODE_EMPTY")
	@Range(min=0,max=9,message = "CHN_CODE_VALUE")
	@JsonProperty("channelcode")
	private Integer channelCode;
	@JsonProperty("channelname")
	private String channelName;
	@Schema(description = "0:inactive, 1:active")
	@NotNull(message="CHN_STATE_EMPTY")
	@Range(min=0,max=1,message = "CHN_STATE_VALUE")
	@JsonProperty("channelstatus")
	private Integer channelStatus;

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "CHANNELSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Channel");
		
		channelCode = stream.readInt();
		channelName = stream.readString();
		channelStatus = stream.readInt();

		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Channel");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Channel");
		stream.writeInt(channelCode);
		stream.writeString(channelName);
		stream.writeInt(channelStatus);
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Channel");
	}
}
