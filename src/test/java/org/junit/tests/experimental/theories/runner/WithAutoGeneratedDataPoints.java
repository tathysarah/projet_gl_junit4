package org.junit.tests.experimental.theories.runner;

import static org.junit.Assert.assertEquals;
import static org.junit.tests.experimental.theories.TheoryTestUtils.potentialAssignments;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

public class WithAutoGeneratedDataPoints {

    private enum ENUM {
        VALUE, OTHER_VALUE, THIRD_VALUE
    }

    @RunWith(Theories.class)
    public static class TheoryTestClassWithAutogeneratedParameterValues {

        public void theory(ENUM e) {
        }

        public void theory(boolean b) {
        }

    }

    @Test
    public void shouldAutomaticallyGenerateEnumDataPoints() throws Throwable {
        assertEquals(ENUM.values().length,
                potentialAssignments(
                        TheoryTestClassWithAutogeneratedParameterValues.class
                                .getMethod("theory", ENUM.class)).size());
    }

    @Test
    public void shouldAutomaticallyGenerateBooleanDataPoints()
            throws Throwable {
        assertEquals(2,
                potentialAssignments(
                        TheoryTestClassWithAutogeneratedParameterValues.class
                                .getMethod("theory", boolean.class)).size());
    }

    @RunWith(Theories.class)
    public static class TheoryTestClassWithSpecificEnumDataPoint {

        @DataPoint
        public static ENUM value = ENUM.OTHER_VALUE;

        public void theory(ENUM e) {
        }

    }

    @Test
    public void shouldNotAutogenerateEnumDataPointsWhenSpecificDataPointGiven()
            throws Throwable {
        assertEquals(1,
                potentialAssignments(
                        TheoryTestClassWithSpecificEnumDataPoint.class
                                .getMethod("theory", ENUM.class)).size());
    }

    @RunWith(Theories.class)
    public static class TheoryTestClassWithSpecificBooleanDataPoint {

        @DataPoint
        public static boolean value = true;

        public void theory(boolean b) {
        }

    }

    @Test
    public void shouldNotAutogenerateBooleanDataPointsWhenSpecificDataPointGiven()
            throws Throwable {
        assertEquals(1,
                potentialAssignments(
                        TheoryTestClassWithSpecificBooleanDataPoint.class
                                .getMethod("theory", boolean.class)).size());
    }

}
