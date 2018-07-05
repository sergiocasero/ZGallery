package com.mzelzoghbi.zgallery.view;

import com.mzelzoghbi.zgallery.CustomViewPager;
import com.mzelzoghbi.zgallery.OnImgClick;
import com.mzelzoghbi.zgallery.R;
import com.mzelzoghbi.zgallery.adapters.HorizontalListAdapters;
import com.mzelzoghbi.zgallery.adapters.ViewPagerAdapter;
import com.mzelzoghbi.zgallery.entities.ZColor;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * ZGalleryGridView
 */
public class ZGalleryGridView extends RelativeLayout {

    CustomViewPager mViewPager;

    ViewPagerAdapter adapter;

    RecyclerView imagesHorizontalList;

    LinearLayoutManager mLayoutManager;

    HorizontalListAdapters hAdapter;

    private RelativeLayout mainLayout;

    private int currentPos;

    private ZColor bgColor;

    private ArrayList<String> imageURLs;

    public ZGalleryGridView(Context context) {
        this(context, null);
    }

    public ZGalleryGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ZGalleryGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.z_grid_gallery_view, this, true);
    }

    public void initialize(Activity activity, ZColor bgColor, int currentPos, ArrayList<String> imageURLs) {
        // init layouts
        mainLayout = findViewById(R.id.mainLayout);
        mViewPager = findViewById(R.id.pager);
        imagesHorizontalList = findViewById(R.id.imagesHorizontalList);

        // get intent data
        this.currentPos = currentPos;
        this.bgColor = bgColor;
        this.imageURLs = imageURLs;

        if (bgColor == ZColor.WHITE) {
            mainLayout.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        }

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        // pager adapter
        adapter = new ViewPagerAdapter(activity, imageURLs, null, imagesHorizontalList);
        mViewPager.setAdapter(adapter);
        // horizontal list adaapter
        hAdapter = new HorizontalListAdapters(activity, imageURLs, new OnImgClick() {
            @Override
            public void onClick(int pos) {
                mViewPager.setCurrentItem(pos, true);
            }
        });
        imagesHorizontalList.setLayoutManager(mLayoutManager);
        imagesHorizontalList.setAdapter(hAdapter);
        hAdapter.notifyDataSetChanged();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                imagesHorizontalList.smoothScrollToPosition(position);
                hAdapter.setSelectedItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        hAdapter.setSelectedItem(currentPos);
        mViewPager.setCurrentItem(currentPos);
    }
}
