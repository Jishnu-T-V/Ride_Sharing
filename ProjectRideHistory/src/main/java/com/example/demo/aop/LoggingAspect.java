package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("execution(* com.example.demo.service.RideHistoryServiceImpl.*(..))")
	public void serviceMethods() {
	}

	@Before("serviceMethods()")
	public void logBefore(JoinPoint joinPoint) {
		logger.info("A method in RideHistoryService is about to be executed. Method: {},Arguments: {}",
				joinPoint.getSignature().getName(), joinPoint.getArgs());
	}

	@AfterReturning(pointcut = "serviceMethods()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("A method in RideHistoryService has executed successfully. Method:{}, Result: {}",
				joinPoint.getSignature().getName(), result);
	}

	@AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		logger.error("An error occurred in RideHistoryService. Method: {} ,Error: {}",
				joinPoint.getSignature().getName(), error.getMessage());
	}
}
