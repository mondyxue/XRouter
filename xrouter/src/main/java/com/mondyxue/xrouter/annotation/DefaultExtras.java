package com.mondyxue.xrouter.annotation;

import com.mondyxue.xrouter.constant.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br>Created by MondyXue
 * <br>E-MAIL: mondyxue@gmial.com
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultExtras{

    @Type int[] type() default Type.String;
    String[] key();
    String[] value();

}
