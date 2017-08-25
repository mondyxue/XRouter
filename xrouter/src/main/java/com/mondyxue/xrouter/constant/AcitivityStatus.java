package com.mondyxue.xrouter.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@IntDef(flag = true, value = {
        AcitivityStatus.UNKNOWN,
        AcitivityStatus.ON_CREATED,
        AcitivityStatus.ON_STARTED,
        AcitivityStatus.ON_RESUMED,
        AcitivityStatus.ON_PAUSED,
        AcitivityStatus.ON_STOPPED,
        AcitivityStatus.ON_SAVEINSTANCESTATE,
        AcitivityStatus.ON_DESTROYED
})
@Retention(RetentionPolicy.SOURCE)
public @interface AcitivityStatus{

    int UNKNOWN = 0;
    int ON_CREATED = 1;
    int ON_STARTED = 1 << 2;
    int ON_RESUMED = 1 << 3;
    int ON_PAUSED = 1 << 4;
    int ON_STOPPED = 1 << 5;
    int ON_SAVEINSTANCESTATE = 1 << 6;
    int ON_DESTROYED = 1 << 7;
}
