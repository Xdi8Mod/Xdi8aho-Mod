package io.github.qwerty770.mcmod.xdi8.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Elements marked {@code @StableApi} are stable to be directly
 * used by other mods. <br />
 * These elements won't be deleted or moved.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PACKAGE, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface StableApi {
    String since() default "3.0.0";
}
