//package kr.or.ddit.logs.AOP;
//
//import java.util.logging.Logger;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.hibernate.validator.internal.util.logging.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class LoggingAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
//
//    // Pointcut: 특정 패키지 내의 모든 메서드 실행 전후에 AOP를 적용
//    @Pointcut("execution(* kr.or.ddit..*.*(..))")
//    public void applicationPackagePointcut() {
//        // 패키지 내 모든 메서드에 적용
//    }
//
//    // 메서드 실행 전 로그 찍기
//    @Before("applicationPackagePointcut()")
//    public void logMethodCall() {
//        logger.info("Method is about to be called.");
//    }
//}