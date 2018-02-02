package BE.aspects;

import BE.controllers.SystemController;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TimerAspect {

    private static final Logger logger = Logger.getLogger(TimerAspect.class);

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

