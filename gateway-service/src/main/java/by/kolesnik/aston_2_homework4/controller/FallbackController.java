package by.kolesnik.aston_2_homework4.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/user-fallback")
    public Mono<String> userServiceFallback() {
        return Mono.just("The user-service is temporarily unavailable. Please try again later.");
    }
}
