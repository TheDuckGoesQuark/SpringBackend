package BE.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ResponseWrappingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void anyControllerPointcut() {
        logger.info("I work1");
    }

    @Pointcut("execution(* *(..))")
    public void anyMethodPointcut() {logger.info("I work2");}

    @AfterReturning(
            value = "anyControllerPointcut() && anyMethodPointcut()",
            returning = "response")
    public Object wrapResponse(Object response) {
        logger.info("Hello World");
        // Do whatever logic needs to be done to wrap it correctly.
        return response;
    }

    @AfterThrowing(
            value = "anyControllerPointcut() && anyMethodPointcut()",
            throwing = "cause")
    public Object wrapException(Exception cause) {
        logger.info("Exception thrown");
        // Do whatever logic needs to be done to wrap it correctly.
        return cause;
    }
}

