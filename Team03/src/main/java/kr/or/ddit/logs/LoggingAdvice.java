//package kr.or.ddit.logs;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//
//public class LoggingAdvice {
//	
//	 private List<String> logList = new ArrayList<>();
//	 
//	 public List<String> getLogs() {
//	        return logList;
//    }
//	 
//	 public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//	        String methodName = joinPoint.getSignature().toShortString();
//	        logList.add("Called method: " + methodName + " at " + LocalDateTime.now());
//	        return joinPoint.proceed();
//    }
//}
