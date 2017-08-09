package com.mondyxue.xrouter.annotation;

import android.support.annotation.AnimRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This for navigation's transition
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 * @see android.app.Activity#overridePendingTransition(int, int)
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transition{

    @AnimRes int enterAnim();
    @AnimRes int exitAnim();

}
