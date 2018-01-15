package BE.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class TimerAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Performance tracking for controllers.
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("within(BE.controllers.*)")
    public Object timeExecution(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info(pjp.getSignature().toShortString() + " Finish: " + (end - start) + "ms");
        return retVal;
    }
}

