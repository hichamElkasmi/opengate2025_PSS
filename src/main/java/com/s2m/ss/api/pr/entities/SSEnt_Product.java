package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

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
public class SSEnt_Product  implements SQLData{

	@NotNull(message="PRD_CODE_EMPTY")
	@Max(value = 9999999999L, message = "PRD_CODE_LENGHT")
	@JsonProperty("productcode")
	private Integer productCode;
	@JsonProperty("productname")
	private String productName;
	@JsonProperty("productdesc")
	private String productDesc;
	@Schema(description = "1:débit, 2:prépayé, 3:crédit")
	@JsonProperty("producttype")
	private Integer productType;
	@JsonProperty("limits")
	List<SSEnt_Limit> limits;
	@JsonProperty("fees")
	List<SSEnt_Fee> fees;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "PRODUCTSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Product");
		
		productCode = stream.readInt();
		productName = stream.readString();
		productDesc = stream.readString();
		productType = stream.readInt();
		try {
			Object[] objects = (Object[]) stream.readArray().getArray();
			limits = Arrays.stream(objects).map(o -> (SSEnt_Limit)o).collect(Collectors.toList());
		}
		catch(Exception e) {
			
		}
		try {

			Object[] objects = (Object[]) stream.readArray().getArray();
			fees = Arrays.stream(objects).map(o -> (SSEnt_Fee)o).collect(Collectors.toList());
		}
		catch(Exception e) {
			
		}
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Product");
		
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
		
	}
}
