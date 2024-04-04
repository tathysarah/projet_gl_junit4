package org.junit.runners.parameterized;

import org.junit.runner.Runner;
import org.junit.runners.model.InitializationErrorException;

/**
 * A {@code ParametersRunnerFactory} creates a runner for a single
 * {@link TestWithParameters}.
 * 
 * @since 4.12
 */
public interface ParametersRunnerFactory {
    /**
     * Returns a runner for the specified {@link TestWithParameters}.
     * 
     * @throws InitializationErrorException
     *             if the runner could not be created.
     */
    Runner createRunnerForTestWithParameters(TestWithParameters test)
            throws InitializationErrorException;
}
