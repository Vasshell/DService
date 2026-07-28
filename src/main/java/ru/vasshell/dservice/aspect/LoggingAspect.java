/*
package vasshell.dservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {
    @Pointcut("execution(public org.springframework.http.ResponseEntity vasshell.dservice.controller.*.*(..))")
    private void requestProcessors(){}

    @Pointcut("execution(public * vasshell.dservice.repository.*.*(..))")
    private void repositoryReturns(){}

    @Pointcut("execution(public * vasshell.dservice.sender.*.*(..))")
    private void senderMessages(){}

    @Pointcut("execution(public * vasshell.dservice.listener.*.*(..))")
    private void listenerMessages(){}

    @Around("requestProcessors()")
    public Object logAroundRequestProcessors(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.debug("Request for {} - {}", methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();
        log.debug("Response for {} - {}", methodName, result);
        return result;
    }

    @AfterReturning(value = "repositoryReturns()", returning = "result")
    public void logAfterRepositoryReturns(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        log.debug("Repository's {} returned {}", methodName, result);
    }

    @After("senderMessages()")
    public void logSendingMessage(JoinPoint joinPoint){
        doGenericLog(joinPoint);
    }

    @Before("listenerMessages()")
    public void logReceivingMessage(JoinPoint joinPoint){
        doGenericLog(joinPoint);
    }

    private void doGenericLog(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getDeclaringType().getName();
        String methodName = joinPoint.getSignature().getName();
        log.debug("{} has {}", name, methodName);
    }
}
*/
