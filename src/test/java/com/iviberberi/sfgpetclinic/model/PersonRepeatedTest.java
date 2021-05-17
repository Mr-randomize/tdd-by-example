package com.iviberberi.sfgpetclinic.model;

import com.iviberberi.sfgpetclinic.ModelRepeatedTests;
import org.junit.jupiter.api.*;

public class PersonRepeatedTest implements ModelRepeatedTests {

    @Disabled
    @RepeatedTest(value = 10, name = "{displayName}: {currentRepetition} - {totalRepetitions}")
    @DisplayName("My Repeated Test")
    void myRepeatedTest() {
        //todo
    }

    @Disabled
    @RepeatedTest(5)
    void myRepeatedTestWithDI(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println(testInfo.getDisplayName() + ": " + repetitionInfo.getCurrentRepetition());
    }

    @Disabled
    @RepeatedTest(value = 5, name = "{displayName}: {currentRepetition} | {totalRepetitions}")
    @DisplayName("My Assignment Repeated Test")
    void myAssignmentRepeated(TestInfo testInfo, RepetitionInfo repetitionInfo) {

    }
}
