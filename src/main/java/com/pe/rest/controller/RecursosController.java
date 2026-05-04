package com.pe.rest.controller;

import java.util.HashMap;
import java.util.Map;

import com.pe.transversal.properties.RecaptchaProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/v1/api/recurso")
public class RecursosController {

    protected RecaptchaProperties recaptchaProperties;

    @GetMapping(value = "/apigoogle", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> apiGoogle() {
        Map<String, String> res = new HashMap<>();
        res.put("api", recaptchaProperties.siteKey());
        return res;
    }
}
