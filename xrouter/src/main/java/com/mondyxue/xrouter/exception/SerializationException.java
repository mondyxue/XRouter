package com.mondyxue.xrouter.exception;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public class SerializationException extends RuntimeException{

    public SerializationException(Object value){
        this(null, value);
    }
    public SerializationException(String key, Object value){
        super("Unsupported parameter: {" + (key == null ? value : key + ":" + value) + "}, "
              + "You should implement 'SerializationService' to " + "support object to json string"
        );
    }

}
