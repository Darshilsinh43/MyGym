package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Dashboard_activity extends AppCompatActivity {
 MeowBottomNavigation mBottomNavigation;
 int HOME_MENU = 1;
 int PROFILE_MENU=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mBottomNavigation = findViewById(R.id.dashboard);
        mBottomNavigation.add(new MeowBottomNavigation.Model(HOME_MENU,R.drawable.ic_home_black_24dp));
        mBottomNavigation.add(new MeowBottomNavigation.Model(PROFILE_MENU,R.drawable.baseline_manage_accounts_24));

        mBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                if (item.getId()==HOME_MENU){
                mBottomNavigation.show(HOME_MENU,true);
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.dashboard_relative,new HomeFragment()).commit();
                }
                else if (item.getId()==PROFILE_MENU) {
                mBottomNavigation.show(PROFILE_MENU,true);
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.dashboard_relative,new Account_activity()).commit();
                }
                else {

                }

            }
        });

        mBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });

        mBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.dashboard_relative,new HomeFragment()).commit();
        mBottomNavigation.show(HOME_MENU,true);
    }
}