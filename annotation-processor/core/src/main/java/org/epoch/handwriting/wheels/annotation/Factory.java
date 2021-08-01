package org.epoch.handwriting.wheels.annotation;

import java.lang.annotation.*;

/**
 * @author Marshal
 * @description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
@Inherited
public @interface Factory {

    /**
     * key
     *
     * @return
     */
    String id();

    /**
     * class type for Group
     *
     * @return
     */
    Class<?> type();

}
