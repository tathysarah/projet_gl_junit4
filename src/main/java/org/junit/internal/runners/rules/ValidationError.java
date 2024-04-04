package org.junit.internal.runners.rules;

import java.lang.annotation.Annotation;

import org.junit.runners.model.FrameworkMember;

class ValidationErrorException extends Exception {

    private static final long serialVersionUID = 3176511008672645574L;

    public ValidationErrorException(FrameworkMember<?> member,
            Class<? extends Annotation> annotation, String suffix) {
        super(String.format("The @%s '%s' %s", annotation.getSimpleName(),
                member.getName(), suffix));
    }
}
