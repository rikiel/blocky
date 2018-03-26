package eu.ba30.re.blocky.aspects;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Defined pointcuts for repository, service, view and presenter calls
 */
class AspectPointcuts {
    @Pointcut("execution(* eu.ba30.re.blocky.service..*Repository+.*(..))")
    protected void repositoryCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.service..*Service+.*(..))")
    protected void serviceCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.view..*View+.*(..))")
    protected void viewCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.view..*Presenter+.*(..))")
    protected void presenterCall() {
    }
}
