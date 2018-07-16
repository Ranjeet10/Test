package com.envent.bottlesup.mvp.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.TutorialPagerAdater;
import com.envent.bottlesup.mvp.model.TutorialData;
import com.envent.bottlesup.utils.MetaData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/16/18.
 */

public class Tutorial extends AppCompatActivity {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.skipTV)
    TextView skipTv;
    @BindView(R.id.firstDot)
    View firstDot;
    @BindView(R.id.secondDot)
    View secondDot;
    @BindView(R.id.thirdDot)
    View thirdDot;
    @BindView(R.id.forthDot)
    View fourthDot;
    @BindView(R.id.fiftDot)
    View fifthDot;
    @BindView(R.id.sixthDot)
    View sixthDot;
    @BindView(R.id.seventhDot)
    View seventhDot;
    @BindView(R.id.eightDot)
    View eightDot;
    @BindView(R.id.nextTV)
    TextView nextTV;

    private Unbinder unbinder;

    private int counter = 1;
    private int lastPage = 1;
    private View[] viewList;
    private boolean isFromSplash = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_layout);
        unbinder = ButterKnife.bind(this);

        isFromSplash = getIntent().getBooleanExtra(MetaData.KEY.IS_FROM_SPLASH, false);

        List<TutorialData> tutorials = MetaData.getTutorials();
        viewList = new View[]{firstDot, secondDot, thirdDot, fourthDot, fifthDot, sixthDot, seventhDot, eightDot};
        setSelectedDot(firstDot, viewList);
        TutorialPagerAdater adapter = new TutorialPagerAdater(getSupportFragmentManager(), tutorials);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setSelectedDot(firstDot, viewList);
                        nextTV.setText(getResources().getString(R.string.next));
                        break;
                    case 1:
                        setSelectedDot(secondDot, viewList);
                        nextTV.setText(getResources().getString(R.string.next));

                        break;
                    case 2:
                        setSelectedDot(thirdDot, viewList);
                        nextTV.setText(getResources().getString(R.string.next));

                        break;
                    case 3:
                        setSelectedDot(fourthDot, viewList);
                        nextTV.setText(getResources().getString(R.string.next));
                        break;

                    case 4:
                        setSelectedDot(fifthDot, viewList);
                        nextTV.setText(getResources().getString(R.string.next));
                        break;
                    case 5:
                        setSelectedDot(sixthDot, viewList);
                        nextTV.setText(getResources().getString(R.string.next));
                        break;
                    case 6:
                        setSelectedDot(seventhDot, viewList);
                        nextTV.setText(getResources().getString(R.string.next));
                        break;
                    case 7:
                        setSelectedDot(eightDot, viewList);
                        nextTV.setText(getResources().getString(R.string.finish));
                        break;

                    default:
                }

                if (lastPage > position) {
                    counter--;
                } else {
                    counter++;
                }
                lastPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSelectedDot(View selectedDot, View[] views) {
        int selectedColor = getResources().getColor(R.color.blue);

        for (View tempView : views) {
            if (selectedDot == tempView) {
                configureView(tempView, selectedColor);
            } else {
                configureView(tempView, Color.LTGRAY);
            }
        }
    }

    private void configureView(View view, int color) {
        if (view != null) {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            drawable.setColor(color);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.nextTV)
    public void onNextTvClicked() {
        if (!nextTV.getText().equals("Finish")) {
            viewPager.setCurrentItem(counter);
        } else {
            skipNow();
        }
    }

    private void skipNow() {
        if (!isFromSplash) {
            finish();
        } else {
            startActivity(new Intent(Tutorial.this, LoginRegister.class));
            finish();
        }
    }

    @OnClick(R.id.skipTV)
    public void onSkipTvClicked() {
        skipNow();
    }

}