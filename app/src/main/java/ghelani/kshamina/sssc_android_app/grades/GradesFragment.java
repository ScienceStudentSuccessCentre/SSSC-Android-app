package ghelani.kshamina.sssc_android_app.grades;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.grades.calculator.CalculatorFragment;
import ghelani.kshamina.sssc_android_app.grades.planner.PlannerFragment;
import ghelani.kshamina.sssc_android_app.grades.terms.TermsFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradesFragment extends Fragment {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private String[] tabTitles;

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.grades_viewpager);
        tabTitles = new String[] {
                getString(R.string.terms_tab),
                getString(R.string.calculator_tab),
                getString(R.string.planner_tab)
        };
        pagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);



        return view;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                return new TermsFragment();
            } else if (i == 1) {
                return new CalculatorFragment();
            } else {
                return new PlannerFragment();
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
