package com.example.zhangchi.blue.ui.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.zhangchi.blue.R;
import com.example.zhangchi.blue.data.local.WaterLocalSource;
import com.example.zhangchi.blue.ui.chart.ChartActivity;
import com.example.zhangchi.blue.util.ActivityUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class StatisticsActivity extends AppCompatActivity {

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    private StatisticsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("统计列表");
        StatisticsFragment statisticsFragment =
                (StatisticsFragment) getSupportFragmentManager().findFragmentById(R.id.statistics_ContentFrame);
        if (statisticsFragment == null) {
            // Create the fragment
            statisticsFragment = new StatisticsFragment();
        }
        ActivityUtil.addFragmentToActivity(
                getSupportFragmentManager(), statisticsFragment, R.id.statistics_ContentFrame);
        presenter = new StatisticsPresenter(WaterLocalSource.getInstance(), statisticsFragment);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        MenuItem searchItem = menu.findItem(R.id.action_add);
         SearchView searchView = (SearchView) searchItem.getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_add) {
//            presenter.loadHeadView();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
            finish();
        }
    }
}
