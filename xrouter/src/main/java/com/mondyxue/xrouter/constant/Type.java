package com.mondyxue.xrouter.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@IntDef(flag = true, value = {

        Type.Byte,
        //        Type.Short,
        Type.Int,
        Type.Long,
        Type.Float,
        Type.Double,
        Type.Boolean,
        //        Type.Char,
        Type.String,
        //        Type.CharSequence,

        Type.ByteArray,
        //        Type.ShortArray,
        Type.IntArray,
        Type.LongArray,
        Type.FloatArray,
        Type.DoubleArray,
        Type.BooleanArray,
        //        Type.CharArray,
        Type.StringArray
        //        Type.CharSequenceArray

})
@Retention(RetentionPolicy.SOURCE)
public @interface Type{

    int Byte = 1;
    //    int Short = 1 << 1;
    int Int = 1 << 2;
    int Long = 1 << 3;
    int Float = 1 << 4;
    int Double = 1 << 5;
    int Boolean = 1 << 6;
    //    int Char = 1 << 7;
    int String = 1 << 8;
    //    int CharSequence = 1 << 9;

    int ByteArray = 1 << 10;
    //    int ShortArray = 1 << 11;
    int IntArray = 1 << 12;
    int LongArray = 1 << 13;
    int FloatArray = 1 << 14;
    int DoubleArray = 1 << 15;
    int BooleanArray = 1 << 16;
    //    int CharArray = 1 << 17;
    int StringArray = 1 << 18;
    //    int CharSequenceArray = 1 << 19;

}
