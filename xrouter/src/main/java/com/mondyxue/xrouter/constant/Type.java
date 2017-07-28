package com.mondyxue.xrouter.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@IntDef(value = {
        Type.String,
        Type.Boolean,
        Type.Long,
        Type.Int,
        Type.Float,
        Type.Double,
        Type.CharSequence,
        Type.Serializable,
        Type.StringArray,
        Type.StringArrayList
})
@Retention(RetentionPolicy.CLASS)
public @interface Type{

    int String = 0;
    int Boolean = 1;
    int Long = 2;
    int Int = 3;
    int Float = 4;
    int Double = 5;
    int CharSequence = 6;
    int Serializable = 7;
    int StringArray = 8;
    int StringArrayList = 9;

}
