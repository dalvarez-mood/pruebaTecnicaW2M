package com.example.spaceshipapi.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.spaceshipapi.controller.SpaceshipController.getSpaceshipById(..)) && args(id)")
    public void getSpaceshipById(Integer id) {}

    @After("getSpaceshipById(id)")
    public void logAfterGetSpaceshipById(Integer id) {
        if (id < 0) {
            logger.warn("Requested spaceship with negative ID: " + id);
        }
    }
}