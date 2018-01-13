package BE.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ResponseWrappingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("within(BE.Controllers.*)")
    public void anyControllerPointcut() {}

    @Pointcut("execution(* *(..))")
    public void anyMethodPointcut() {}

    @AfterReturning(
            pointcut = "anyControllerPointcut()",
            returning = "response")
    public Object wrapResponse(Object response) {
        logger.info("Hello World");
        // Do whatever logic needs to be done to wrap it correctly.
        return response;
    }

    @AfterThrowing(
            pointcut = "anyControllerPointcut()",
            throwing = "cause")
    public Object wrapException(Exception cause) {
        logger.info("Exception thrown");
        // Do whatever logic needs to be done to wrap it correctly.
        return cause;
    }

    /**
     * Performance tracking for controllers.
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("within(BE.Controllers.*)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info(pjp.getSignature().toShortString() + " Finish: " + (end - start) + "ms");
        return retVal;
    }
}

