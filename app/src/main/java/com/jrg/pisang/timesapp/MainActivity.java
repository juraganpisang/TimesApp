package com.jrg.pisang.timesapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {

    SpaceNavigationView navigationView;
    ActionBar actionBar;

    final Fragment fragment0 = new NewsFragment();
    final Fragment fragment1 = new KanalFragment();
    final Fragment fragment2 = new MediaFragment();
    final Fragment fragment3 = new SettingFragment();

    public final FragmentManager fragmentManager = getSupportFragmentManager();

    public Fragment active = fragment0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //action bar and its propertoes
        actionBar = getSupportActionBar();

        navigationView = findViewById(R.id.space);

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment1, "1").hide(fragment1).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment0, "0").commit();

        //Add Space Navigation items.
        //
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_android_black));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_android_black));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_android_black));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_android_black));

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(MainActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
                navigationView.setCentreButtonSelectable(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0 :
                        fragmentManager.beginTransaction().hide(active).show(fragment0).commit();
                        active = fragment0;
//                        actionBar.setTitle("HEADLINE");
                        break;
                    case 1:
                        fragmentManager.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
//                        actionBar.setTitle("KANAL");
                        break;
                    case 2:
                        fragmentManager.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
//                        actionBar.setTitle("MEDIA");
                        break;
                    case 3:
                        fragmentManager.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
//                        actionBar.setTitle("SETTING");
                        break;
                }
//                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
//                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
