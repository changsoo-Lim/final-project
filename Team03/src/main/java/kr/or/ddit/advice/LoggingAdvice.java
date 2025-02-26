package kr.or.ddit.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingAdvice {
	public void before(JoinPoint point) {
		long start = System.currentTimeMillis();
		Object target = point.getTarget();
		String targetName = target.getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		Object[] args = point.getArgs();
		log.info("=-=-=-=-=Before-=-=-=-=-={}.{}({})",targetName,methodName,args);
	}
	public void after(JoinPoint pointm, Object retValue) {
		long end = System.currentTimeMillis();
		log.info("=-=-=-=-=after-=-=-=: 반환값 : {} -=-=",retValue);
	}
	
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long start = System.currentTimeMillis();
		Object target = point.getTarget();
		String targetName = target.getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		Object[] args = point.getArgs();
		log.info("=-=-=-=-=Before-=-=-=-=-={}.{}({})",targetName,methodName,args);
		
		Object retValue = point.proceed(args);
		
		long end = System.currentTimeMillis();
		log.info("=-=-=-=-=after-=소요시간 : {}ms-=-=: 반환값 : {} -=-=", (end-start), retValue);
		
		return retValue;
	}
}
