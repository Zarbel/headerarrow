package net.zarbel.headerarrow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.zarbel.headerarrow.fragment.FragmentExample1;
import net.zarbel.headerarrow.fragment.FragmentExample2;
import net.zarbel.headerarrow.fragment.FragmentExample3;

/**
 * Created by julian on 8/8/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public static final int SIZE = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case DemoFragments.DEMO_FRAGMENT_1:
                return new FragmentExample1();
            case DemoFragments.DEMO_FRAGMENT_2:
                return new FragmentExample2();
            case DemoFragments.DEMO_FRAGMENT_3:
                return new FragmentExample3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return SIZE;
    }
}
