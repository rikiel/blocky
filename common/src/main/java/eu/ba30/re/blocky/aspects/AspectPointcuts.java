package eu.ba30.re.blocky.aspects;

import org.aspectj.lang.annotation.Pointcut;

class AspectPointcuts {
    @Pointcut("execution(* eu.ba30.re.blocky.service..*Service+.*(..))")
    public void serviceCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.view..*View+.*(..))")
    public void viewCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.view..*Presenter+.*(..))")
    public void presenterCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.service..*Repository+.*(..))")
    public void repositoryCall() {
    }
}
