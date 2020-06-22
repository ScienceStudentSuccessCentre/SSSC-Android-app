package ghelani.kshamina.sssc_android_app.ui.grades;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.grades.calculator.CalculatorFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.planner.PlannerFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradesFragment extends Fragment {
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private String[] tabTitles;

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grades, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // Set tab titles
        tabTitles = new String[] {
                getString(R.string.terms_tab),
                getString(R.string.calculator_tab),
                getString(R.string.planner_tab)
        };

        //Create viewpager to add swipe navigation
        viewPager = (ViewPager2) view.findViewById(R.id.grades_viewpager);
        pagerAdapter = new ScreenSlidePagerAdapter(getActivity());
        viewPager.setAdapter(pagerAdapter);

        //Link viewpager to TabLayout
        TabLayout tabLayout = view.findViewById(R.id.gradesTabLayout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 1:
                    return new CalculatorFragment();
                case 2:
                    return new PlannerFragment();
                default:
                    return new TermsFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}