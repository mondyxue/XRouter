package com.mondyxue.xrouter.utils;

import android.support.annotation.NonNull;

import com.mondyxue.xrouter.constant.Type;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public final class TypeUtils{

    private TypeUtils(){}

    public static Object parse(@Type int type, @NonNull String value){
        switch(type){
        case Type.Byte:
            return Byte.parseByte(value);
        case Type.Int:
            return Integer.parseInt(value);
        case Type.Long:
            return Long.parseLong(value);
        case Type.Float:
            return Float.parseFloat(value);
        case Type.Double:
            return Double.parseDouble(value);
        case Type.Boolean:
            return Boolean.parseBoolean(value);
        case Type.String:
            return value;
        case Type.ByteArray:
            String[] splitBytes = value.substring(1, value.length() - 1).split(",");
            byte[] bytes = new byte[splitBytes.length];
            for(int j = 0; j < splitBytes.length; j++){
                bytes[j] = Byte.parseByte(splitBytes[j]);
            }
            return bytes;
        case Type.IntArray:
            String[] splitInts = value.substring(1, value.length() - 1).split(",");
            int[] ints = new int[splitInts.length];
            for(int j = 0; j < splitInts.length; j++){
                ints[j] = Integer.parseInt(splitInts[j]);
            }
            return ints;
        case Type.LongArray:
            String[] splitLongs = value.substring(1, value.length() - 1).split(",");
            long[] longs = new long[splitLongs.length];
            for(int j = 0; j < splitLongs.length; j++){
                longs[j] = Long.parseLong(splitLongs[j]);
            }
            return longs;
        case Type.FloatArray:
            String[] splitFloats = value.substring(1, value.length() - 1).split(",");
            float[] floats = new float[splitFloats.length];
            for(int j = 0; j < splitFloats.length; j++){
                floats[j] = Float.parseFloat(splitFloats[j]);
            }
            return floats;
        case Type.DoubleArray:
            String[] splitDoubles = value.substring(1, value.length() - 1).split(",");
            double[] doubles = new double[splitDoubles.length];
            for(int j = 0; j < splitDoubles.length; j++){
                doubles[j] = Double.parseDouble(splitDoubles[j]);
            }
            return doubles;
        case Type.BooleanArray:
            String[] splitBooleans = value.substring(1, value.length() - 1).split(",");
            boolean[] booleans = new boolean[splitBooleans.length];
            for(int j = 0; j < splitBooleans.length; j++){
                booleans[j] = Boolean.parseBoolean(splitBooleans[j]);
            }
            return booleans;
        case Type.StringArray:
            return value.substring(1, value.length() - 1).split(",");
        default:
            return null;
        }
    }

}
