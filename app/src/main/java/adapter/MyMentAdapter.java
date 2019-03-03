package adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyMentAdapter extends FragmentPagerAdapter {
   private List<Fragment> fList;
    private  String[] titles=new String[]{"热销新品","魔力时尚","品质生活"};

    public MyMentAdapter(FragmentManager fm, List<Fragment> fList) {
        super(fm);
        this.fList = fList;
    }


    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
