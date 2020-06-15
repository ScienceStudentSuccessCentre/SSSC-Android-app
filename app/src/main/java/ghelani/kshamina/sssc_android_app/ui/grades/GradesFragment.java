package ghelani.kshamina.sssc_android_app.ui.grades;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.grades.calculator.CalculatorFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.planner.PlannerFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.TermsFragment;


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
