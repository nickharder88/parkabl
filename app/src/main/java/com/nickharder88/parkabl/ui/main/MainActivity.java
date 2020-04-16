package com.nickharder88.parkabl.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.model.Admin;
import com.nickharder88.parkabl.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mNavHeaderTitle;
    private TextView mNavHeaderSubtitle;

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Navigate to Login if Not Logged In
        final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        final Intent intent = new Intent(this, LoginActivity.class);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(intent);
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection(Admin.collection).whereEqualTo("authUID", user.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            DocumentSnapshot snap = queryDocumentSnapshots.getDocuments().get(0);
                            if (snap != null) {
                                Admin admin = snap.toObject(Admin.class);
                                if (admin != null) {
                                    mNavHeaderTitle.setText(admin.name);
                                    mNavHeaderSubtitle.setText(admin.email);
                                }
                            }
                        }
                    });
                }
            }
        };

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mNavHeaderTitle = navigationView.getHeaderView(0).findViewById(R.id.nav_header_title);
        mNavHeaderSubtitle = navigationView.getHeaderView(0).findViewById(R.id.nav_header_subtitle);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeNextFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // listen for logout
        MenuItem menuItemLogout = navigationView.getMenu().findItem(R.id.logout);
        menuItemLogout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Resources resources = getResources();
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
                builder
                        .setTitle(resources.getString(R.string.confirm_title_logout))
                        .setMessage(resources.getString(R.string.confirm_message_logout))
                        .setNegativeButton(resources.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Nothing
                            }
                        })
                        .setPositiveButton(resources.getString(R.string.continue_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFirebaseAuth.signOut();
                            }
                        })
                        .show();
                return true;
            }
        });


        // Start Listening
        mFirebaseAuth.addAuthStateListener(authListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (authListener != null) {
            final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseAuth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
