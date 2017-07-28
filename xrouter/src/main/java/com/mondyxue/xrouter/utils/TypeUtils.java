package com.mondyxue.xrouter.utils;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public final class TypeUtils{

    private TypeUtils(){}

    public static Boolean parseBoolean(Object value, Boolean def){
        if(value != null){
            if(value instanceof Boolean){
                return ((Boolean) value);
            }else if(value instanceof String){
                return Boolean.parseBoolean(((String) value));
            }
        }
        return def;
    }
    public static Double parseDouble(Object value, Double def){
        if(value != null){
            if(value instanceof Double){
                return ((Double) value);
            }else if(value instanceof String){
                return Double.parseDouble(((String) value));
            }
        }
        return def;
    }
    public static Float parseFloat(Object value, Float def){
        if(value != null){
            if(value instanceof Float){
                return ((Float) value);
            }else if(value instanceof String){
                return Float.parseFloat(((String) value));
            }
        }
        return def;
    }
    public static Long parseLong(Object value, Long def){
        if(value != null){
            if(value instanceof Long){
                return ((Long) value);
            }else if(value instanceof String){
                return Long.parseLong(((String) value));
            }
        }
        return def;
    }
    public static int parseInt(Object value, Integer def){
        if(value != null){
            if(value instanceof Integer){
                return ((Integer) value);
            }else if(value instanceof String){
                return Integer.parseInt(((String) value));
            }
        }
        return def;
    }

    public static boolean isFundamentalType(Object value){
        return (value instanceof String)
               || (value instanceof Boolean)
               || (value instanceof Long)
               || (value instanceof Integer)
               || (value instanceof Float)
               || (value instanceof Double);
    }

}
