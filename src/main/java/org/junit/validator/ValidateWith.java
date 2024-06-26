package org.junit.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows for an {@link AnnotationValidator} to be attached to an annotation.
 *
 * <p>
 * When attached to an annotation, the validator will be instantiated and
 * invoked by the {@link org.junit.runners.ParentRunner}.
 * </p>
 *
 * @since 4.12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
public @interface ValidateWith {
    Class<? extends AnnotationValidator> value();
}
