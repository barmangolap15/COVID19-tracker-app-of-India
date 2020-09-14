package com.example.covid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.covid.R;
import com.example.covid.adapter.VPagerAdapter;
import com.example.covid.fragments.PageFragment1;
import com.example.covid.fragments.PageFragment2;
import com.example.covid.fragments.PageFragment3;
import com.example.covid.fragments.PageFragment4;
import com.example.covid.fragments.PageFragment5;
import com.example.covid.fragments.PageFragment6;
import com.example.covid.fragments.PageFragment7;
import com.example.covid.fragments.PageFragment8;
import com.example.covid.fragments.PageFragment9;
import com.example.covid.model.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class SymptomsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment1());
        list.add(new PageFragment2());
        list.add(new PageFragment3());
        list.add(new PageFragment4());
        list.add(new PageFragment5());
        list.add(new PageFragment6());
        list.add(new PageFragment7());
        list.add(new PageFragment8());
        list.add(new PageFragment9());

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new VPagerAdapter(getSupportFragmentManager(),list);

        viewPager.setAdapter(pagerAdapter);
    }
}