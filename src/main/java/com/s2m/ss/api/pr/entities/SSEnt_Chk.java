package com.s2m.ss.api.pr.entities;

import static com.s2m.ss.api.pr.entities.SSEnt_DF.*;

import com.s2m.ss.api.pr.config.SS_LoggingImpl;
import com.s2m.ss.api.pr.entities.requests.SSEnt_TransactionsListRq;

import lombok.Getter;
import lombok.Setter;

public class SSEnt_Chk {

	@Getter
	@Setter
	SSEnt_InternelError errVal;

	SS_LoggingImpl log;

	public SSEnt_Chk() {
		log = new SS_LoggingImpl();
		log.setClazz(getClass());
	}


	public SSEnt_Status checkTransactionsList(SSEnt_TransactionsListRq transactionsListReq) {
		log.getLog().trace("start checkTransactionsList");
		log.logDebug("transactionsListReq: ",transactionsListReq);

		SSEnt_Status status = new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN"));
		
		if(!(checkDataFilter(transactionsListReq.getFilter()))){
			log.getLog().trace("error checkTransactionsList");
			status = new SSEnt_Status(getMsg("ST_REJECTED"), getErrVal());
		}
		log.getLog().trace("end checkTransactionsList");
		log.logDebug("status: ",status);
		return status;
	}
	

	private boolean checkDataFilter(SSEnt_Filter sSEnt_Filter) {
		log.getLog().trace("start checkDataFilter");

		if ((sSEnt_Filter.getAmountMin()!=null) && (sSEnt_Filter.getAmountMax()!=null) &&
				(sSEnt_Filter.getAmountMin() > sSEnt_Filter.getAmountMax())) {
			errVal = getMsg("AMOUNT_DIFF_VALUE");
			log.getLog().error(errVal.getCodeErreur() + errVal.getMsgErreur());
			return false;
		}
		if ((sSEnt_Filter.getStartDate()!=null) && (sSEnt_Filter.getEndDate()!=null) &&
				(sSEnt_Filter.getStartDate() > sSEnt_Filter.getEndDate())) {
			errVal = getMsg("DATE_DIFF_VALUE");
			log.getLog().error(errVal.getCodeErreur() + errVal.getMsgErreur());
			return false;
		}
		
		log.getLog().trace("end checkDataFilter");

		return true;
	}


}
