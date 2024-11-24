package com.ama.karate.controller.utilsControl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.utils.ExcelReader;
import com.google.gson.JsonObject;

@RestController
public class ExcelControl {

    @Autowired ExcelReader er;

    @GetMapping("/read-excel")
    public String readExcel(@RequestParam String filePath) {
        System.out.println("FILE " + filePath);
        JsonObject json = er.readExcelFile(filePath);
        return json.toString();
    }
    
}
