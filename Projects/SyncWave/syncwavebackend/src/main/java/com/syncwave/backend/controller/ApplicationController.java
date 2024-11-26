package com.syncwave.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.syncwave.backend.lib.constants.SecurityConstants.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
public class ApplicationController {

    @GetMapping
    public ResponseEntity<String> getRoot() {
        return ResponseEntity.status(HttpStatus.OK).body("Welcome to SyncWave Backend :)");
    }
}
