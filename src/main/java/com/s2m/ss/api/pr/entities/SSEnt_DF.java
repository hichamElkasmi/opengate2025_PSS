package com.s2m.ss.api.pr.entities;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

public class SSEnt_DF {
	 
	private static ResourceBundle bundle;
    
	public static final String DEF_UUID = "99999999-9999-9999-9999-999999999999";
    public static final SSEnt_Header DEFAULT_HEAD = new SSEnt_Header(UUID.fromString(DEF_UUID));
    public static final SSEnt_Status DEFAULT_REJ_STAT = new SSEnt_Status(getMsg("ST_REJECTED"), "","");
    public static final String PACKAGE_SQL = "api_entry_pkg";
    public static final String OK_SS = "00000";
    
    public static SSEnt_InternelError getMsg(String idxError) {
    	String fullmsg;
    	String[] idxMsg = new String[2];
    	SSEnt_InternelError ent_InternelError = null;
    	try {
    		if(bundle == null)
        		bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
    		fullmsg = bundle.getString(idxError);
    		idxMsg = fullmsg.split(";");
    		ent_InternelError = new SSEnt_InternelError(idxMsg[0].trim(), idxMsg[1].trim());
    	}
    	catch(Exception ex) {
    		idxMsg[0] = "10999";
    		idxMsg[1] = "Erreur de traitement des ressources";
    	}
    	
    	return ent_InternelError;
    }
}
