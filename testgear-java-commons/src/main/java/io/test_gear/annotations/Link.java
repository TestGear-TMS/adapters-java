package io.test_gear.annotations;

import io.test_gear.models.LinkType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Link {
    String url();

    String title() default "";

    String description() default "";

    LinkType type();
}
