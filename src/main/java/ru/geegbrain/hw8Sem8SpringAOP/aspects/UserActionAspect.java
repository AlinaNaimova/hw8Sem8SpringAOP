package ru.geegbrain.hw8Sem8SpringAOP.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserActionAspect {

    @Before("@annotation(ru.geegbrain.hw8Sem8SpringAOP.aspects.TrackUserAction)")
    public void trackUserAction(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String user = "localhost";
        System.out.println("User: " + user + " Action: " + methodName + " Args: " + args.toString());
    }
}
