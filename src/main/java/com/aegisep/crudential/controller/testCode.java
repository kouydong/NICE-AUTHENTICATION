package com.aegisep.crudential.controller;

import com.aegisep.crudential.dto.NiceDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Controller
public class testCode {
    @GetMapping(value="/nice/1")
    public String getEncryptedToken(Model model) throws Exception{

        NiceDto niceDto = new NiceDto();
        niceDto.setAuthType("444");
        model.addAttribute("niceDto", niceDto);

        return "/nice/niceAuthOk";
    }


    @GetMapping(value="/2")
    public String checking2() {
        //model.addAttribute("data" , "어서오세요");
        /*
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Result");
        modelAndView.addObject("response", 넘길_값);
         */
        System.out.println("this is the move666");
        return "niceUserInfo";
    }


    @PostMapping(value="/3")
    public String getAuth() {

        return "niceAuthOk";
    }
}
