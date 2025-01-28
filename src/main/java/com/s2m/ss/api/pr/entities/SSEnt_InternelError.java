package com.s2m.ss.api.pr.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SSEnt_InternelError {
    private String codeErreur;
    private String msgErreur;
    
    public String getMsgErreur() {
        return this.msgErreur;
    }
    

}
