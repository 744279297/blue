package com.example.zhangchi.blue.ui.water;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zhangchi.blue.R;
import com.example.zhangchi.blue.event.SendMsg;
import com.example.zhangchi.blue.ui.statistics.StatisticsContract;
import com.example.zhangchi.blue.util.ActivityUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_water)
public class WaterActivity extends AppCompatActivity {
    @ViewInject(R.id.water_toolbar)
    private Toolbar waterToolbar;
    WaterContract.Presenter presenter;
    WaterFragment waterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        waterFragment =
                (WaterFragment) getSupportFragmentManager().findFragmentById(R.id.water_contentFrame);
        if (waterFragment == null) {
            // Create the fragment
            waterFragment = new WaterFragment();
        }
        ActivityUtil.addFragmentToActivity(
                getSupportFragmentManager(), waterFragment, R.id.water_contentFrame);


        setSupportActionBar(waterToolbar);
        getSupportActionBar().setTitle("water");
        presenter = new WaterPresenter(waterFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            presenter.getBlueTooth(this);
            return true;
        }
        if (id == R.id.action_auto) {
            waterFragment.showControlView();

        }
        if (id == R.id.action_handle) {
            waterFragment.showControl();

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
