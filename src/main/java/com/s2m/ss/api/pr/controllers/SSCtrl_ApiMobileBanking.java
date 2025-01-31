package com.s2m.ss.api.pr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SSCtrl_ApiMobileBanking{
	
    @RequestMapping(value = {
        "",
        "/",
        "/index",
        "/home",
        "/acceuil"
    })
    public String homePage() {
        return "redirect:/spec";
    }

    @RequestMapping(value = "/errors")
    public String pingPage() {
        return "index";
    }
    

}
