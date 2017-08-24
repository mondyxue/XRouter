package com.mondyxue.xrouter.data;

import android.os.Bundle;

/**
 * A bundle wrapper for parsing bundle extras
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
public interface IBundleWrapper{

    <T> IBundleWrapper put(String key, T value);
    IBundleWrapper put(Bundle bundle);

    <T> T get(String key);
    <T> T get(String key, T def);

    Bundle getBundle();

    boolean isEmpty();

    void clear();

}
