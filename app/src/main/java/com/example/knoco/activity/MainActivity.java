package com.example.knoco.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.knoco.R;
import com.example.knoco.fragment.AddFragment;
import com.example.knoco.fragment.CategoryFragment;
import com.example.knoco.fragment.HomeFragment;
import com.example.knoco.fragment.SearchFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int currentFragment = 1 ;
    private ArrayList<Fragment> arr = new ArrayList<>();
    private MaterialToolbar toolbar ;
    private DrawerLayout drawerLayout ;
    private NavigationView navigationView ;
    FirebaseAuth mauth ;
    private BottomNavigationView bot ;

    public void BindingData(){
        drawerLayout = findViewById(R.id.drawerLayout);
        bot = findViewById(R.id.BotNav);
        mauth = FirebaseAuth.getInstance();
    }

    public void BindingAction(){


        changeFragment(new HomeFragment());
        bot.setSelectedItemId(R.id.home2);


        bot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home2:
                        changeFragment(new HomeFragment());
                        break;
                    case R.id.search:
                        changeFragment(new SearchFragment());
                        break;
                    case R.id.add:
                        changeFragment(new AddFragment());
                        break;
                    case R.id.more:
                        showMoreDialog();
                        break;
                    case R.id.category:
                        changeFragment(new CategoryFragment());
                        break;

                }
                return true;
            }
        });



    }

    private void showMoreDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_menu,null);
        LinearLayout Profile = dialogView.findViewById(R.id.profileLay);
        LinearLayout Logout = dialogView.findViewById(R.id.LogoutLay);
        LinearLayout Us = dialogView.findViewById(R.id.UsLay);

        ImageView clear = dialogView.findViewById(R.id.clear);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(myIntent);            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        Us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(myIntent);
                finish();
            }
        });


        if(dialog.getWindow() !=null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BindingData();
        BindingAction();


    }

    public void changeFragment(Fragment fragment ){
        FragmentManager fragmentManager = getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ViewFrag,fragment);
        fragmentTransaction.commit();
    }
}