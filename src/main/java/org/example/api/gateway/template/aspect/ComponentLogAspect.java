package org.example.api.gateway.template.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ComponentLogAspect {

    @Around("execution(* org.example.api.gateway.template.service..*.*(..))"
            + "|| execution(* org.example.api.gateway.template.controller..*.*(..))"
            + "|| execution(* org.example.api.gateway.template.client..*.*(..))")
    public Object aroundComponent(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object returnObj;
        final Logger log;
        final String methodName;
        final MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
        methodName = methodSign.getMethod().getName();

        final Object[] args = joinPoint.getArgs();

        log = LoggerFactory.getLogger(methodSign.getDeclaringType());
        log.info("method: {}, input: {}", methodName, Arrays.toString(args));
        returnObj = joinPoint.proceed();
        log.info("method: {}, return: {}", methodName, returnObj);

        return returnObj;
    }
}
