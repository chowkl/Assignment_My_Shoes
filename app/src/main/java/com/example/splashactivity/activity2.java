package com.example.splashactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashactivity.Model.ProductList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity2 extends AppCompatActivity {
    private String mOrderMessage;
    private TextView appbarText;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        appbarText = findViewById(R.id.appbarText);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search,menu);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.home:
                            Toast.makeText(activity2.this,"home", Toast.LENGTH_SHORT).show();
                            appbarText.setVisibility(View.GONE);
                            getSupportActionBar().setDisplayShowTitleEnabled(true);
                            getSupportActionBar().setTitle("My Shoes");
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.product:
                            Toast.makeText(activity2.this,"ProductList", Toast.LENGTH_SHORT).show();
                            appbarText.setVisibility(View.GONE);
                            getSupportActionBar().setDisplayShowTitleEnabled(true);
                            getSupportActionBar().setTitle("ProductList");
                            selectedFragment = new ProductFragment();
                            break;
                        case R.id.cart:
                            Toast.makeText(activity2.this,"Cart", Toast.LENGTH_SHORT).show();
                            appbarText.setVisibility(View.GONE);
                            getSupportActionBar().setDisplayShowTitleEnabled(true);
                            getSupportActionBar().setTitle("My Cart");
                            selectedFragment = new CartFragment();
                            break;
                        case R.id.account:
                            Toast.makeText(activity2.this,"Account", Toast.LENGTH_SHORT).show();
                            appbarText.setVisibility(View.GONE);
                            getSupportActionBar().setDisplayShowTitleEnabled(true);
                            getSupportActionBar().setTitle("Account");
                            selectedFragment = new AccountFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,

                            selectedFragment).commit();
                    return true;
                }
            };

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void showProduct(View view) {
        mOrderMessage = "product clicked";
        displayToast(mOrderMessage);
        Intent intent = new Intent(this, singleProduct.class);
        startActivity(intent);
    }
}