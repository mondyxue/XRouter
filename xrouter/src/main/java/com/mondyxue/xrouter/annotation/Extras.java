package com.mondyxue.xrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This for the parameter type: Map<String,String>
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Extras{

    boolean serializable() default true;

}
