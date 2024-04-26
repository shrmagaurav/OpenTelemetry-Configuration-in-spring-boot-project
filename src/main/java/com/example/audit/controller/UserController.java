package com.example.audit.controller;


import com.example.audit.database.UserDao;
import com.example.audit.database.UserEntity;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao database;

    private final Tracer tracer;

    public UserController(OpenTelemetry openTelemetry) {
        this.tracer = openTelemetry.getTracer("my-instrumentation");
    }

    @PostMapping
    public UserEntity create(@RequestBody UserEntity entity){
        Span span = tracer.spanBuilder("New user requests").startSpan();
//        span = Span.current();
        span.getSpanContext().getSpanId();
        span.setAttribute("new user insert req with name", entity.getFirstName() + " "+ entity.getLastName());
        span.end();
        return database.insert(entity);
    }

    @GetMapping
    public List<UserEntity> getAll(){
        Span span = tracer.spanBuilder("Get all user Data").startSpan();
        span.setAttribute("someone needing all the data","Request accepted at");
        span.setAttribute("time",new Date().toString());

        span.end();
        return database.getAll();
    }



    @GetMapping("/hello")
    public String hello() {
        // Start a new span
        Span span = tracer.spanBuilder("helloSpan").startSpan();

        try {
            // Simulate some work
            Thread.sleep(100);

            // Set attributes on the span
            span.setAttribute("All set", "yoooo");

            // Simulate an error (for demo purposes)
            boolean isError = false;
            if (isError) {
                span.setStatus(io.opentelemetry.api.trace.StatusCode.ERROR);
            }

            return "Hello, world!";
        } catch (InterruptedException e) {
            // Handle exception
        } finally {
            // End the span
            span.end();
            return "helloworld";
        }
    }
}
