package com.example.zhangchi.blue.ui.statistics;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.zhangchi.blue.BaseFragment;
import com.example.zhangchi.blue.R;
import com.example.zhangchi.blue.anim.Rotate3dAnimation;
import com.example.zhangchi.blue.data.local.Water;
import com.example.zhangchi.blue.ui.chart.ChartActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zhangchi on 2016/5/8.
 */
@ContentView(R.layout.content_main)
public class StatisticsFragment extends BaseFragment implements StatisticsContract.View {
    protected LayoutAnimationController getAnimationController(float fromDegrees, float toDegrees, float centerX,
                                                               float centerY, float depthZ, boolean reverse) {
        int duration = 500;
        AnimationSet set = new AnimationSet(true);

        Animation animation = new Rotate3dAnimation(fromDegrees, toDegrees, centerX, centerY, depthZ, reverse);
        animation.setDuration(duration);
        set.addAnimation(animation);


        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }

    public static int HIGH_INCREASE = 0;
    public static int HIGH_DECREASE = 1;
    public static int LOW_INCREASE = 2;
    public static int LOW_DECREASE = 3;
    @ViewInject(R.id.water_list)
    private SwipeMenuListView waterLv;

    @ViewInject(R.id.statistics_head)
    private LinearLayout statisticsHead;

    @Event(R.id.head_chart)
    private void toChart(View view) {
        statisticsHead.setVisibility(View.GONE);
        Intent intent = new Intent(getContext(), ChartActivity.class);
        startActivity(intent);
    }

    @Event(value = R.id.statistics_over)
    private void menuGone(View view) {
        statisticsHead.setVisibility(View.GONE);
    }

    @Event(value = R.id.head_high_jiang)
    private void highJangSort(View view) {
        statisticsHead.setVisibility(View.GONE);
        float centerX = waterLv.getMeasuredWidth();
        float centerY = waterLv.getChildAt(1).getMeasuredHeight();
        presenter.sortWaters(HIGH_DECREASE);
        waterLv.setLayoutAnimation(getAnimationController(0, 180, centerX / 2, centerY / 2, centerY / 2, false));

    }

    @Event(value = R.id.head_high_zeng)
    private void highZengSort(View view) {
        statisticsHead.setVisibility(View.GONE);
        presenter.sortWaters(HIGH_INCREASE);
    }

    @Event(value = R.id.head_low_jiang)
    private void lowJangSort(View view) {
        statisticsHead.setVisibility(View.GONE);
        presenter.sortWaters(LOW_DECREASE);
    }

    @Event(value = R.id.head_low_zeng)
    private void lowZengSort(View view) {
        statisticsHead.setVisibility(View.GONE);
        presenter.sortWaters(LOW_INCREASE);
    }

    private WatersAdapter adapter;
    private StatisticsContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSwipeListView();

    }

    private void initSwipeListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(250, 128,
                        114)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };
        waterLv.setMenuCreator(creator);

        waterLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                return false;
            }
        });

        waterLv.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showWaters(List<Water> waterList) {
        Log.d("list", String.valueOf(waterList.size()));
        adapter = new WatersAdapter(waterList);
        waterLv.setAdapter(adapter);
    }

    @Override
    public void showWaterDetail(int waterId) {

    }

    @Override
    public void showLoadingWatersError() {

    }

    @Override
    public void showNoWater() {

    }

    @Override
    public void changeHeadView() {
        if (statisticsHead.getVisibility() == View.GONE) {
            statisticsHead.setVisibility(View.VISIBLE);
        } else {
            statisticsHead.setVisibility(View.GONE);
        }

    }

    @Override
    public void setPresenter(StatisticsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    class WatersAdapter extends BaseAdapter {
        private List<Water> waters;
        private LayoutInflater inflater;

        public WatersAdapter(List<Water> waterList) {

            waters = waterList;
        }

        @Override
        public int getCount() {
            return waters.size();
        }

        @Override
        public Object getItem(int position) {
            return waters.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = LayoutInflater.from(parent.getContext());
            Water water = waters.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_statistics, null);
                viewHolder = new ViewHolder();
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.itemNumber.setText(String.valueOf(position + 1));
            viewHolder.itemTime.setText(water.getTime());
            viewHolder.itemRatio.setText(water.getLowRatio() + "%" + "---" + water.getHighRatio() + "%");
            viewHolder.itemHighValue.setText(water.getHighValue() + "mm");
            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.item_number)
            private TextView itemNumber;
            @ViewInject(R.id.item_time)
            private TextView itemTime;
            @ViewInject(R.id.item_ratio)
            private TextView itemRatio;
            @ViewInject(R.id.item_high_value)
            private TextView itemHighValue;
        }
    }

}
