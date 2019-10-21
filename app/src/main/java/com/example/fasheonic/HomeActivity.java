package com.example.fasheonic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.fasheonic.ui.Profile.ProfileFragment;
import com.example.fasheonic.ui.chat.ChatFragment;
import com.example.fasheonic.ui.history.HistoryFragment;
import com.example.fasheonic.ui.home.HomeFragment;
import com.example.fasheonic.ui.myorderbids.MyOrderBidsFragment;
import com.example.fasheonic.ui.mywishlist.MyWishlistFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    public static String u_name ="";
    FirebaseDatabase mdatabase;
    DrawerLayout drawer;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        mdatabase = FirebaseDatabase.getInstance();
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        updateNavHeader();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_uploadorder, R.id.nav_myorder,
//                R.id.nav_mywishlist, R.id.nav_chat,R.id.nav_history,R.id.nav_settings,R.id.nav_logout)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_search_icon:
               //Search Fires

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.main_notification_icon:

                Toast.makeText(HomeActivity.this, "You selected notification", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.main_cart_icon:

                Toast.makeText(HomeActivity.this, "You selected cart", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            dialogbox();
        }

    }

    public void dialogbox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }



    public void updateNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        final TextView navUsername=headerView.findViewById(R.id.user_prof_name);
        TextView navEmail=headerView.findViewById(R.id.user_prof_email);
////Query for name
         mdatabase.getReference("Users")
                .child(currentUser.getUid()).child("fullname").addValueEventListener(new ValueEventListener() {
           ///"BfZ2GPMnf6VVOTtSbWdQJZRIsg32"
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //     u_name = dataSnapshot.getValue().toString();
                //Log.d("AAA",dataSnapshot.getValue().toString());
                navUsername.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // navUsername.setText(u_name);
        navEmail.setText(currentUser.getEmail());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.nav_home:
                HomeFragment hf = new HomeFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.nav_host_fragment,hf).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_uploadorder:
                startActivity(new Intent(this,UploadOrderActivity.class));
                finish();
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LogSign.class));
                finish();
                break;

            case R.id.nav_chat:
                ChatFragment chatFragment=new ChatFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                getSupportActionBar().setTitle("Chat");
                //pink toolbar Hide
                //getSupportActionBar().hide();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,chatFragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_history:
                HistoryFragment historyFragment=new HistoryFragment();
                FragmentManager fragmentManager3=getSupportFragmentManager();
                getDrawerToggleDelegate();
                getSupportActionBar().setTitle("History");
                //pink toolbar Hide
                //getSupportActionBar().hide();
                fragmentManager3.beginTransaction().replace(R.id.nav_host_fragment,historyFragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_mywishlist:
                MyWishlistFragment myWishlistFragment=new MyWishlistFragment();
                FragmentManager fragmentManager1=getSupportFragmentManager();
                getDrawerToggleDelegate();
                getSupportActionBar().setTitle("My WishList");
                //pink toolbar Hide
                //getSupportActionBar().hide();
                fragmentManager1.beginTransaction().replace(R.id.nav_host_fragment,myWishlistFragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_myorder:
                MyOrderBidsFragment myOrderBidsFragment=new MyOrderBidsFragment();
                FragmentManager fragmentManager2=getSupportFragmentManager();
                getDrawerToggleDelegate();
                getSupportActionBar().setTitle("My OrderBids");
                //pink toolbar Hide
                //getSupportActionBar().hide();
                fragmentManager2.beginTransaction().replace(R.id.nav_host_fragment,myOrderBidsFragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;


            case R.id.nav_settings:
                ProfileFragment profileFragment=new ProfileFragment();
                FragmentManager fragmentManager4=getSupportFragmentManager();
                getDrawerToggleDelegate();
                getSupportActionBar().setTitle("Profile");
                //pink toolbar Hide
                //getSupportActionBar().hide();
                fragmentManager4.beginTransaction().replace(R.id.nav_host_fragment,profileFragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;


        }



        return false;
    }
}
