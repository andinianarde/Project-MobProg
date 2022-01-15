package com.example.projectmobprog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectmobprog.ui.Fragment_1;
import com.example.projectmobprog.ui.Fragment_2;
import com.example.projectmobprog.ui.tab.Tab1;
import com.example.projectmobprog.ui.tab.Tab2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectmobprog.databinding.ActivityMainMenuBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenuActivity extends AppCompatActivity {

    private ActivityMainMenuBinding binding;
    private TextView mTextMessage;
    FrameLayout frameLayout;
    LinearLayout layout_tab;
    TabLayout tab;
    ViewPager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = (item) -> {
      switch (item.getItemId()){
          case R.id.navigation_home:
              layout_tab.setVisibility(View.GONE);
              frameLayout.setVisibility(View.VISIBLE);
              load_fragment_bottom(new Fragment_1());
              return true;
          case R.id.navigation_notifications:
              layout_tab.setVisibility(View.VISIBLE);
              frameLayout.setVisibility(View.GONE);
              return true;
          case R.id.navigation_profile:
              layout_tab.setVisibility(View.GONE);
              frameLayout.setVisibility(View.VISIBLE);
              load_fragment_bottom(new Fragment_2());
              return true;
      }
      return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        layout_tab = (LinearLayout) findViewById(R.id.layout_tab);
        frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
        load_fragment_bottom(new Fragment_1());

        tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        Tab_adapter tab_adapter = new Tab_adapter(getSupportFragmentManager(),tab.getTabCount());
        viewPager.setAdapter(tab_adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });

    }

    Boolean load_fragment_bottom(Fragment fragment){
        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_frame,fragment).commit();
            return true;
        }else{
            return false;
        }
    }
}

class Tab_adapter extends FragmentStatePagerAdapter{

    int jumlahTab;

    public Tab_adapter(@NonNull FragmentManager fm, int jmlTab) {
        super(fm);
        this.jumlahTab = jmlTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return jumlahTab;
    }
}