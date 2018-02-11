package com.javalec.ex;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAop {
	
	@Pointcut("within(com.javalec.ex.*)")
	private void pointcutMethod(){
	}
	
	@Around("pointcutMethod()")
	public Object loggerAop(ProceedingJoinPoint joinPoint) throws Throwable{
		
		String signaturestr = joinPoint.getSignature().toShortString();
		System.out.println(signaturestr + " is start.");
		long st = System.currentTimeMillis();
		
		try {
			Object obj = joinPoint.proceed();
			return obj;
		} finally {
			long et = System.currentTimeMillis();
			System.out.println(signaturestr + " is finished.");
			System.out.println("걸린 시간 : " + (et-st));
		}
		
	}
	
	@Before("within(com.javalec.ex.*)")
	public void beforadvice(){
		System.out.println("beforAdvice()");
	}
	

}
