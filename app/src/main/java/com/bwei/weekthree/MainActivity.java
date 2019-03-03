package com.bwei.weekthree;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import adapter.MyMentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.MoFragment;
import fragment.PinFragment;
import fragment.ReFragment;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.view)
    ViewPager mView;
    @BindView(R.id.tab)
    TabLayout mTab;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("八维商城");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.bw);
        setSupportActionBar(toolbar);
         ActionBar supportActionBar = getSupportActionBar();
         if (supportActionBar!=null){
             supportActionBar.setDisplayHomeAsUpEnabled(true);
         }

        intiView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbal,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.textTitle:

                break;
            case R.id.textzi:
                Intent intent=new Intent(MainActivity.this,WebActivity.class);
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);

    }

    private void intiView() {
        //创建集合
        List<Fragment> fList = new ArrayList<>();
        fList.add(new ReFragment());
        fList.add(new MoFragment());
        fList.add(new PinFragment());
        MyMentAdapter myMentAdapter=new MyMentAdapter(getSupportFragmentManager(),fList);
        mView.setAdapter(myMentAdapter);
        mTab.setupWithViewPager(mView);
    }
}
