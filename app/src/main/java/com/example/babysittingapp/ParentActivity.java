package com.example.babysittingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.babysittingapp.service.StaticData;
import com.example.babysittingapp.ui.home.HomeFragment;
import com.example.babysittingapp.ui.home.PostDetailFragment;
import com.example.babysittingapp.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.babysittingapp.databinding.ActivityParentBinding;

public class ParentActivity extends AppCompatActivity {

    private ActivityParentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityParentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_parent);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public void switchToDashboard() {
        binding.navView.setSelectedItemId(R.id.navigation_dashboard);
    }

    public void switchToHome() {
        deleteAllFragment();
        binding.navView.setSelectedItemId(R.id.navigation_home);
    }

    public void switchToNotifications() {
        binding.navView.setSelectedItemId(R.id.navigation_notifications);
    }

    public void goToPostDetail(String postId) {
        switchToHome();
        startPostDetailFragment(postId);
    }

    public void startPostDetailFragment(String postId) {
        deleteFragmentTag("post_detail");
        Log.i("abc", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        StaticData.getInstance().setCurrentPostID(postId);
        PostDetailFragment nextFrag= new PostDetailFragment();
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, nextFrag, "post_detail")
                .addToBackStack(null)
                .commit();
    }

    public void deleteAllFragment() {
        FragmentManager fm = getSupportFragmentManager();
        for(int entry = 0; entry<fm.getBackStackEntryCount(); entry++){
            fm.popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    private void logout() {
        Intent myIntent = new Intent(ParentActivity.this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myIntent);
    }

    private void noti() {
        deleteNoti();
        NotificationsFragment nextFrag= new NotificationsFragment();
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, nextFrag, "notification")
                .addToBackStack(null)
                .commit();
    }

    public void deleteFragmentTag(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void deleteNoti() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("notification");
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.info:
//                newGame();
                return true;
            case R.id.logout:
                logout();
                return true;
            case R.id.noti:
                noti();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onBackPressed() {
//        FragmentManager fm = getSupportFragmentManager();
//        Log.d("abc", String.valueOf(fm.getBackStackEntryCount()));
//        for(int entry = 0; entry<fm.getBackStackEntryCount(); entry++){
//            Log.i("abc", "Found fragment: ");
//            if (fm.getBackStackEntryAt(entry) instanceof PostDetailFragment){
//                Log.d("debug", "you are in PostDetailFragment");
//            } else if (fm.getBackStackEntryAt(entry) instanceof HomeFragment) {
//                Log.d("debug", "you are in HomeFragment");
//            }
//        }
//        if (fm.getBackStackEntryCount() > 0) {
//            Log.d("ParentActivity", "popping backstack");
//            fm.popBackStack();
//        } else {
//            Log.d("ParentActivity", "nothing on backstack, calling super");
////            super.onBackPressed();
//        }
//    }
}