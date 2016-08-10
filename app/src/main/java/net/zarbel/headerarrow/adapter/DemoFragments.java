package net.zarbel.headerarrow.adapter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by julian on 8/8/16.
 */
public class DemoFragments {

    public static final int DEMO_FRAGMENT_1 = 0;
    public static final int DEMO_FRAGMENT_2 = 1;
    public static final int DEMO_FRAGMENT_3 = 2;
    public static final int DEMO_FRAGMENT_4 = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            DEMO_FRAGMENT_1,
            DEMO_FRAGMENT_2,
            DEMO_FRAGMENT_3,
            DEMO_FRAGMENT_4
    })
    @interface DemoFragment{};
}
