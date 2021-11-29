package com.df.business.controller;

import com.df.anno.Autowired;
import com.df.anno.GetMapping;
import com.df.anno.RestController;
import com.df.business.service.SimpleService;
import com.df.core.ResponseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * @author MFine
 * @version 1.0
 * @date 2021/11/17 21:08
 **/
@RestController
public class ControllerTest {

    @Autowired
    private SimpleService simpleService;


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body(simpleService.hello());
    }

    @GetMapping("/test2")
    public ResponseEntity<List<String>> test2() {
        return ResponseEntity.ok().body(Arrays.asList("1", "2"));
    }


}
