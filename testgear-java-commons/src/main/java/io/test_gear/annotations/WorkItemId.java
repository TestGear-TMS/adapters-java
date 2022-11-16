package io.test_gear.annotations;

import io.test_gear.models.LinkType;
import io.test_gear.services.Adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @deprecated This annotation is no longer acceptable to compute time between versions.
 * <p> Use {@link WorkItemIds} instead.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Deprecated
public @interface WorkItemId {
    String value();
}


