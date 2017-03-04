package develop.beta1139.memoryleaktest;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabLayoutPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initTabLayout();
        initViewPager();
        initTab();

        initButton();

        printHeap();
    }

    private void initButton() {
        Button button = (Button) findViewById(R.id.init_tab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTab();
            }
        });
    }

    private void initTab() {
        List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.add("fifth");
        list.add("sixth");
        list.add("seventh");
        list.add("eightth");
        list.add("nineth");
        list.add("tenth");

        mAdapter = new TabLayoutPagerAdapter(this, list);

        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);

        // ViewPagerをTabLayoutを設定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initViewPager() {
        if (mViewPager != null) {
            return;
        }

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Memory leak Test");
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void initTabLayout() {
        if (mTabLayout != null) {
            return;
        }

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void printHeap() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                Log.v("Runtime", "==============================");
                Log.v("Runtime", "totalMemory[KB] = " + (int)(runtime.totalMemory()/1024));
                Log.v("Runtime", "freeMemory[KB] = " + (int)(runtime.freeMemory()/1024));
                Log.v("Runtime", "usedMemory[KB] = " + (int)( (runtime.totalMemory() - runtime.freeMemory())/1024) );
                Log.v("Runtime", "maxMemory[KB] = " + (int)(runtime.maxMemory()/1024));
                Log.v("Runtime", "==============================");

                printHeap();
            }
        }, 1000);
    }
}
