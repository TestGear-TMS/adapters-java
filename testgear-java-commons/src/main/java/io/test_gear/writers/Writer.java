package io.test_gear.writers;

import io.test_gear.models.ClassContainer;
import io.test_gear.models.MainContainer;
import io.test_gear.models.TestResult;

public interface Writer {
    void startLaunch();

    void finishLaunch();

    void writeTest(TestResult testResult);

    void writeClass(ClassContainer container);

    void writeTests(MainContainer container);
}
