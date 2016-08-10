package net.zarbel.headerarrow;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.zarbel.headerarrow.adapter.PagerAdapter;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    TextView indicator;
    PagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.vpg);
        indicator = (TextView) findViewById(R.id.tv_indicator);

        adapter = new PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        indicator.setText(((viewPager.getCurrentItem()+1)+"/"+adapter.getCount()));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        indicator.setText((position+1+"/"+adapter.getCount()));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
