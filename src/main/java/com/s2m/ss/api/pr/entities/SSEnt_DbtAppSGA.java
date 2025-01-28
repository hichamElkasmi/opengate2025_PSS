package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class SSEnt_DbtAppSGA implements SQLData{

	@Size(min=1,max=24,message="FEEACCOUNT_LENGHT")
    @Pattern(regexp = "[0-9]+",message="FEEACCOUNT_VALUE")
	@JsonProperty("feeaccount")
	private String feeAccount;
	@NotBlank(message = "CRD_NAME_EMPTY")
	@Size(min=1,max=40,message="CRD_NAME_LENGHT")
	@JsonProperty("nameprinted")
	private String namePrinted;
	@Valid
	@NotNull(message="ENT_PRODUCT_NULL")
	@JsonProperty("product")
	private SSEnt_Product product;
	@JsonProperty("cardecom")
	@Valid
	@NotNull(message="ENT_OPR_ECOM_NULL")
	private SSEnt_CardOperations cardEcom;
	@NotBlank(message = "PHONE_EMPTY")
	@Size(min=1,max=20,message="LEN_PHONE_20")
	@JsonProperty("phonenum")
	private String phoneNum;
	@NotBlank(message = "BRANCH_EMPTY")
	@Size(min=1,max=20,message="BRANCH_LENGHT")
	@JsonProperty("branchiden")
	private String branchIden;
	
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "DEBITSGASTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {

	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_DbtAppSGA");
		stream.writeString(feeAccount);
		stream.writeString(namePrinted);
		stream.writeInt(product.getProductCode());
		stream.writeInt(cardEcom.getOperation());
		stream.writeString(getPhoneNum());
		stream.writeString(getBranchIden());
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_DbtAppSGA");
	}
}
