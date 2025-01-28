package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Timestamp;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Range;

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
public class SSEnt_Filter implements SQLData {

	@Range(min=1,max=99,message="FILTER_NTRANS_VALUE")
	@JsonProperty("nbrtrans")
	private Integer nbrTrans;
	@JsonProperty("startdate")
	private Long startDate;
	@JsonProperty("enddate")
	private Long endDate;
    @DecimalMin(value = "0.0", inclusive = true,message="AMOUNT_MAX_VALUE")
    @Digits(integer=12, fraction=2,message="AMOUNT_MAX_LENGHT")
	@JsonProperty("amountmin")
	private Double amountMin;
    @DecimalMin(value = "0.0", inclusive = true,message="AMOUNT_MIN_VALUE")
    @Digits(integer=12, fraction=2,message="AMOUNT_MIN_LENGHT")
	@JsonProperty("amountmax")
	private Double amountMax;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "FILTERSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {

	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {		
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_Filter");

		stream.writeDouble(nbrTrans==null?-1:nbrTrans);
		
        if(startDate == null)
            stream.writeTimestamp(null);
        else
            stream.writeTimestamp(new Timestamp(startDate*1000));//+" 00:00:00.000"));

        if(endDate == null)
            stream.writeTimestamp(null);
        else
            stream.writeTimestamp(new Timestamp(endDate*1000));//+" 00:00:00.000"));
        
		stream.writeDouble((amountMin==null?-1:amountMin));
		stream.writeDouble((amountMax==null?-1:amountMax));	

		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_Filter");
	}
	
}
