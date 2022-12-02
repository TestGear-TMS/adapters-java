package io.test_gear.listener;

import io.test_gear.models.TestResult;

public interface AdapterListener extends DefaultListener {
    default void beforeTestStop(final TestResult result) {
        //do nothing
    }
}
