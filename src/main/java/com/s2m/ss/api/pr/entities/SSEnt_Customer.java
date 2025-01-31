package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.Instant;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class SSEnt_Customer implements SQLData{

	@Size(min=1,max=40,message="ENRL_EN_FSTNAME_LENGHT")
	@JsonProperty("firstname")
	String firstName;
	@Size(min=1,max=40,message="ENRL_EN_MDLNAME_LENGHT")
	@JsonProperty("middlename")
	String middleName;
	@Size(min=1,max=40,message="ENRL_EN_LSTNAME_LENGHT")
	@JsonProperty("lastname")
	String lastName;
	@Schema(description = "1:MASCULIN, 2:FÉMININ")
	@Range(min=1,max=2,message="ENRL_GENDER_VALUE")
	Integer gendre;
	@Schema(description = "1:Mr, 2:Mlle, 3:Mme, 4:Dr")
	@Range(min=1,max=4,message="ENRL_TITEL_VALUE")
	Integer titel;
	@JsonProperty("dateob")
	Long dateOfBirth;
	@JsonProperty("placeob")
	String placeOfBirth;
	@Size(min=1,max=128,message="ENRL_MAIL_LENGHT")
	@Email(message="ENRL_MAIL_VALUE")
	String email;
	@Valid
	SSEnt_Identity identity;
	@Valid
	SSEnt_Branch branch;
	@Valid
	SSEnt_Bank bank;
	@NotNull(message="ENT_ADRESSE_NULL")
	@Valid
	SSEnt_Address address;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "CUSTOMERSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Customer");
			try {
			firstName = stream.readString();
			middleName = stream.readString();
			lastName = stream.readString();
			gendre = stream.readInt();
			titel = stream.readInt();
			try{
	            Instant instant = (stream.readTimestamp()).toInstant() ;
	            dateOfBirth = instant.getEpochSecond();
	        }catch (Exception ex) {
	        	dateOfBirth = null;
	        }
			stream.readString();
			placeOfBirth = stream.readString();	
			email = stream.readString();
			identity = new SSEnt_Identity(stream.readInt(),stream.readString());
			
			branch = new SSEnt_Branch();
			branch.setBranchIden(stream.readString());
			branch.setBranchCorpName(stream.readString());	

			bank = new SSEnt_Bank();
			bank.setBankcode(stream.readInt());
			bank.setBankname(stream.readString());				
			
			address = new SSEnt_Address();
			address.setAddr1(stream.readString());
			address.setAddr2(stream.readString());	
			
			SSEnt_Country ent_Country = new SSEnt_Country();
			ent_Country.setCountryCode(stream.readInt());
			ent_Country.setCountryName(stream.readString());	
			address.setCountry(ent_Country);
			
			SSEnt_City ent_City = new SSEnt_City();
			ent_City.setCityCode(stream.readInt());
			ent_City.setCityName(stream.readString());				
			address.setCity(ent_City);
				
			address.setPostalCode(stream.readString());
			address.setPhoneNum(stream.readString());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Customer");
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_Customer");
			try {
				stream.writeString(firstName);
				stream.writeString(middleName);
				stream.writeString(lastName);
				stream.writeInt((gendre == null) ? -1 : gendre);
				stream.writeInt((titel == null) ? -1 : titel);		
				if(dateOfBirth == null )
		            stream.writeTimestamp(null);
		        else 
		            stream.writeTimestamp(new Timestamp(dateOfBirth*1000));//+" 00:00:00.000"));
				stream.writeString(null);
				stream.writeString(placeOfBirth);	
				stream.writeString(email);	
				stream.writeInt((identity == null) ? -1 : identity.getIdenType());
				stream.writeString(null);
				stream.writeString((branch == null) ? null : branch.getBranchIden());
				stream.writeString(null);
				stream.writeInt((bank == null) ? -1 : bank.getBankcode());
				stream.writeString(null);
				
				if(address!=null) {
					stream.writeString(address.getAddr1());
					stream.writeString(address.getAddr2());
					stream.writeInt((address.getCountry() == null) ? -1 : address.getCountry().getCountryCode());
					stream.writeString(null);
					stream.writeInt((address.getCity() == null) ? -1 : address.getCity().getCityCode());
					stream.writeString(null);
					stream.writeString(address.getPostalCode());
					stream.writeString(address.getPhoneNum());
				}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_Customer");
	}
}
