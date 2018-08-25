package com.example.welcome.katicha6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context =context;
    }

    //Array
    public String[] slide_heading={
             "First Page Heading",
            "Second page Heading",
            "Third page Heading"
    };

    public String[] slide_descp= {
            "This is the first slide page discription.slide for second page",
            "This is the second slide page description.slide for third page",
            "This is the third slide page description.slide finished"

    };
    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slidedescrition=view.findViewById(R.id.slide_descp);

        slideHeading.setText(slide_heading[position]);
        slidedescrition.setText(slide_descp[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
