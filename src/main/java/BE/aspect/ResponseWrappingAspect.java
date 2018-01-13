package BE.aspect;

import BE.exceptions.BaseException;
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
    public ResponseWrapper wrapResponse(Object response) {
        logger.info("Wrapping response.");
        return new ResponseWrapper(response);
    }

    @AfterThrowing(
            pointcut = "anyControllerPointcut()",
            throwing = "cause")
    public ErrorResponseWrapper wrapException(BaseException cause) {
        logger.info("Exception thrown: " + cause.toString());
        return new ErrorResponseWrapper(cause);
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

