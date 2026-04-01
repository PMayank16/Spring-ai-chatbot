package com.mayankPortfolio.controller;

import com.mayankPortfolio.services.AiServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/ask-ai")
@CrossOrigin(origins = "*")
public class AiController {
    private final AiServices aiServices;

    public AiController(AiServices aiServices) {
        this.aiServices = aiServices;
    }

    @GetMapping
    public ResponseEntity<Mono<String>> askAi(
            @RequestParam(value = "q" , required = true) String query

    ) {
       return ResponseEntity.ok(aiServices.askai(query));
    }

}
