package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * @deprecated Included for backwards compatibility with JUnit 4.4. Will be
 *             removed in the next major release. Please use
 *             {@link BlockJUnit4ClassRunner} in place of
 *             {@link JUnit4ClassRunner}.
 */
@Deprecated
public class MethodValidator {

    private final List<Throwable> errors = new ArrayList<Throwable>();

    private TestClass testClass;

    public MethodValidator(TestClass testClass) {
        this.testClass = testClass;
    }

    public void validateInstanceMethods() {
        validateTestMethods(After.class, false);
        validateTestMethods(Before.class, false);
        validateTestMethods(Test.class, false);

        List<Method> methods = testClass.getAnnotatedMethods(Test.class);
        if (methods.size() == 0) {
            errors.add(new Exception("No runnable methods"));
        }
    }

    public void validateStaticMethods() {
        validateTestMethods(BeforeClass.class, true);
        validateTestMethods(AfterClass.class, true);
    }

    public List<Throwable> validateMethodsForDefaultRunner() {
        validateNoArgConstructor();
        validateStaticMethods();
        validateInstanceMethods();
        return errors;
    }

    public void assertValid() throws InitializationError {
        if (!errors.isEmpty()) {
            throw new InitializationError(errors);
        }
    }

    public void validateNoArgConstructor() {
        try {
            testClass.getConstructor();
        } catch (Exception e) {
            errors.add(new Exception(
                    "Test class should have public zero-argument constructor",
                    e));
        }
    }

    private void validateTestMethods(Class<? extends Annotation> annotation,
            boolean isStatic) {
        List<Method> methods = testClass.getAnnotatedMethods(annotation);

        for (Method each : methods) {
            modifierIsStatic(each, annotation, isStatic);
            modifierIsPublic(each);
            modifierIsPublicAndIsNotInMethod(each);
            typeVoidOfMethod(each);
            lengthOfParametersOfMethod(each);
            /*if (Modifier.isStatic(each.getModifiers()) != isStatic) {
                String state = isStatic ? "should" : "should not";
                errors.add(new Exception("Method " + each.getName() + "() "
                        + state + " be static"));
            }
            if (!Modifier.isPublic(each.getDeclaringClass().getModifiers())) {
                errors.add(new Exception(
                        "Class " + each.getDeclaringClass().getName()
                                + " should be public"));
            }

            if (!Modifier.isPublic(each.getModifiers())) {
                errors.add(new Exception(
                        "Method " + each.getName() + " should be public"));
            }
            if (each.getReturnType() != Void.TYPE) {
                errors.add(new Exception("Method " + each.getName()
                        + "should have a return type of void"));
            }
            if (each.getParameterTypes().length != 0) {
                errors.add(new Exception("Method " + each.getName()
                        + " should have no parameters"));
            }*/
        }
    }

    private void modifierIsStatic(Method m, Class<? extends Annotation> annotation,
                                boolean isStatic){
        if (Modifier.isStatic(m.getModifiers()) != isStatic) {
            String state = isStatic ? "should" : "should not";
            errors.add(new Exception("Method " + m.getName() + "() "
                    + state + " be static"));
        }
    }

    private void modifierIsPublic(Method m){
        if (!Modifier.isPublic(m.getDeclaringClass().getModifiers())) {
            errors.add(new Exception(
                    "Class " + m.getDeclaringClass().getName()
                            + " should be public"));
        }
    }

    private void modifierIsPublicAndIsNotInMethod(Method m){
        if (!Modifier.isPublic(m.getModifiers())) {
            errors.add(new Exception(
                    "Method " + m.getName() + " should be public"));
        }
    }

    private void typeVoidOfMethod(Method m) {
        if (m.getReturnType() != Void.TYPE) {
            errors.add(new Exception("Method " + m.getName()
                    + "should have a return type of void"));
        }
    }

    private void lengthOfParametersOfMethod(Method m){
        if (m.getParameterTypes().length != 0) {
            errors.add(new Exception("Method " + m.getName()
                    + " should have no parameters"));
        }
    }

}
