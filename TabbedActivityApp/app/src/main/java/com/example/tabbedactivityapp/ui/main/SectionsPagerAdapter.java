package com.example.tabbedactivityapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tabbedactivityapp.FragmentBudget;
import com.example.tabbedactivityapp.FragmentExpense;
import com.example.tabbedactivityapp.FragmentOther;
import com.example.tabbedactivityapp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragmentToReturn = null;

        switch (position) {
            case 0:
                //fragmentToReturn = Budget.newInstance(position + 1);
                fragmentToReturn = new FragmentBudget();
                break;
            case 1:
                //fragmentToReturn =  Expense.newInstance(position + 1);
                fragmentToReturn = new FragmentExpense();
                break;
            case 2:
                //fragmentToReturn =  Other.newInstance(position + 1);
                fragmentToReturn = new FragmentOther();
                break;
        }
        return fragmentToReturn;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Returns the number of tabs
        return TAB_TITLES.length;
    }
}