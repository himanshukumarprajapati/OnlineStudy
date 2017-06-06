package regatta.com.oea;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import regatta.com.oea.help.HelpActivity;

import regatta.com.oea.termscondition.TermCondition;
import regatta.com.oea.ui.LoginActivity;
import regatta.com.oea.ui.Profile;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView six,seven,eight,nine,ten,ele,twe;
    TextView usermailidd,textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        usermailidd = (TextView) header.findViewById(R.id.textView7);
        textView =(TextView) header.findViewById(R.id.textView);

        usermailidd.setText(AppConstantVariables.firstName);
        textView.setText(AppConstantVariables.mailId);
        Log.d("userkaname",AppConstantVariables.mailId);

     //   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        six = (ImageView) findViewById(R.id.six);
        seven = (ImageView) findViewById(R.id.seven);
        eight = (ImageView) findViewById(R.id.eight);
        nine = (ImageView) findViewById(R.id.nine);
        ten = (ImageView) findViewById(R.id.ten);
        ele = (ImageView) findViewById(R.id.ele);
        twe = (ImageView) findViewById(R.id.twe);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(MainActivity.this, goeasypos.com.onlinestudy.newclasses.MainActivity.class);
                startActivity(intent);
                finish();
*/              String a="1";
                AppConstantVariables.idclass=a;
                Intent intent = new Intent(MainActivity.this, regatta.com.oea.newclasses.MainActivity.class);
                intent.putExtra("int_value", a);
                startActivity(intent);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a="2";
                AppConstantVariables.idclass=a;
                Intent intent = new Intent(MainActivity.this, regatta.com.oea.newclasses.MainActivity.class);
                intent.putExtra("int_value", a);
                startActivity(intent);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a="3";
                AppConstantVariables.idclass=a;
                Intent intent = new Intent(MainActivity.this, regatta.com.oea.newclasses.MainActivity.class);
                intent.putExtra("int_value", a);
                startActivity(intent);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a="4";
                AppConstantVariables.idclass=a;
                Intent intent = new Intent(MainActivity.this, regatta.com.oea.newclasses.MainActivity.class);
                intent.putExtra("int_value", a);
                startActivity(intent);
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a="5";
                AppConstantVariables.idclass=a;
                Intent intent = new Intent(MainActivity.this, regatta.com.oea.newclasses.MainActivity.class);
                intent.putExtra("int_value", a);
                startActivity(intent);
            }
        });
        ele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a="6";
                AppConstantVariables.idclass=a;
                Intent intent = new Intent(MainActivity.this, regatta.com.oea.newclasses.MainActivity.class);
                intent.putExtra("int_value", a);
                startActivity(intent);
            }
        });
        twe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a="7";
                AppConstantVariables.idclass=a;
                Intent intent = new Intent(MainActivity.this, regatta.com.oea.newclasses.MainActivity.class);
                intent.putExtra("int_value", a);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

       /* NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this, regatta.com.oea.abt.MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, TermCondition.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        /*    Intent intent = new Intent(MainActivity.this, CustomDialog.class);
            startActivity(intent);
            finish();*/

        }
        else if (id == R.id.purchaselist) {

            Intent intent = new Intent(MainActivity.this, regatta.com.oea.purchaselistclasses.PurchaseListActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
