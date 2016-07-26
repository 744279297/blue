package com.example.zhangchi.blue.ui.chart;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.zhangchi.blue.R;
import com.example.zhangchi.blue.data.local.WaterLocalSource;
import com.example.zhangchi.blue.ui.statistics.StatisticsContract;
import com.example.zhangchi.blue.ui.statistics.StatisticsFragment;
import com.example.zhangchi.blue.ui.statistics.StatisticsPresenter;
import com.example.zhangchi.blue.util.ActivityUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_chart)
public class ChartActivity extends AppCompatActivity {

    @ViewInject(R.id.chart_toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.chart_fab)
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("统计列表");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineChartFragment l = null;
                if (l == null) {
                    // Create the fragment
                    l = new LineChartFragment();
                }
                ActivityUtil.addFragmentToActivity(
                        getSupportFragmentManager(), l, R.id.chart_contentFrame);
            }
        });
        PieChartFragment lineChartFragment =
                (PieChartFragment) getSupportFragmentManager().findFragmentById(R.id.chart_contentFrame);
        if (lineChartFragment == null) {
            // Create the fragment
            lineChartFragment = new PieChartFragment();
        }
        ActivityUtil.addFragmentToActivity(
                getSupportFragmentManager(), lineChartFragment, R.id.chart_contentFrame);
//        presenter = new StatisticsPresenter(WaterLocalSource.getInstance(), lineChartFragment);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


//        presenter = new StatisticsPresenter(WaterLocalSource.getInstance(), lineChartFragment);

            return true;
        }

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
