package ch.reserveyourroom.common.logger;

import java.lang.annotation.*;

/**
 * Indicates Log of appropriate type to
 * be supplied at runtime to the annotated field.
 *
 * The injected logger is an appropriate implementation
 * of org.slf4j.Log.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Log { }