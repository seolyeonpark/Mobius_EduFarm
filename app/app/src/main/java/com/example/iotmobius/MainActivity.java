package com.example.iotmobius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    DiaryFragment DiaryFragment;
    UserFragment UserFragment;
    HomeFragment HomeFragment;
    ItemFragment ItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DiaryFragment = new DiaryFragment();
        UserFragment = new UserFragment();
        HomeFragment = new HomeFragment();
        ItemFragment = new ItemFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment).commit();
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_user:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, UserFragment).commit();
                        return true;
                    case R.id.tab_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment).commit();
                        return true;
                    case R.id.tab_diary:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, DiaryFragment).commit();
                        return true;
                    case R.id.tab_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, ItemFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.LinearLayout;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class MainActivity extends AppCompatActivity {
//
//
//    LinearLayout home_ly;
//    BottomNavigationView bottomNavigationView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        init(); //객체 정의
//        SettingListener(); //리스너 등록
//
//        //맨 처음 시작할 탭 설정
//        bottomNavigationView.setSelectedItemId(R.id.tab_home);
//
//
//    }
//
//    private void init() {
//        home_ly = findViewById(R.id.home_ly);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//    }
//
//    private void SettingListener() {
//        //선택 리스너 등록
//        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
//    }
//
//    //하단 탭 설정
//    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.tab_home: {
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.home_ly, new HomeFragment())
//                            .commit();
//                    return true;
//                }
//                case R.id.tab_diary: {
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.home_ly, new DiaryFragment())
//                            .commit();
//                    return true;
//                }
//                case R.id.tab_user: {
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.home_ly, new UserFragment())
//                            .commit();
//                    return true;
//                }
//                case R.id.tab_item: {
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.home_ly, new ItemFragment())
//                            .commit();
//                    return true;
//                }
//            }
//
//            return false;
//        }
//    }
//}