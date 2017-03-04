package develop.beta1139.memoryleaktest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomo on 2017/03/04.
 */

public class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mList;

    public TabLayoutPagerAdapter(FragmentActivity fragmentActivity, List<String> list) {
        super(fragmentActivity.getSupportFragmentManager());
        mList = list;
    }

    @Override
    public Fragment getItem(int i) {
        return BlankFragment.newInstance(mList.get(i), "");
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }
}
