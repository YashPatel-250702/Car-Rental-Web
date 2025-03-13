package com.tekworks.rental.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AopLoggers {

    @Before("execution(* com.tekworks.rental.service.*.*(..))")
    public void loggerBeforeService(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Executing Service method: " + methodName + "() in class: " + className);
    }
    
    @Before("execution(* com.tekworks.rental.controller.*.*(..))")
    public void loggerBeforeController(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Executing Controller method: " + methodName + "() in class: " + className);
    }
    @After("execution(* com.tekworks.rental.service.*.*(..))")
    public void loggerAfterService(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Execution Completed Service method: " + methodName + "() in class: " + className);
    }
    
    @After("execution(* com.tekworks.rental.controller.*.*(..))")
    public void loggerAfterController(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Execution Completed  Controller method: " + methodName + "() in class: " + className);
    }
}
