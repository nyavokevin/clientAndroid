package com.example.client.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.client.R;
import com.example.client.fragments.HomeFragment;
import com.example.client.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import org.json.JSONException;
import org.json.JSONObject;

@RequiresApi(api = VERSION_CODES.O)
public class HomeActivity extends AppCompatActivity {

    SharedPreferences settings;

    BottomNavigationItemView bottomNavigationItemView;

    boolean checkUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Channel name";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_HIGH; //Important for heads-up notification
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Button homeFrag = (Button) findViewById(R.id.homeButton);
        Button profileFrag = (Button) findViewById(R.id.profileButton);
        Button nitif = (Button) findViewById(R.id.buttonnotification);
        settings = getSharedPreferences("auth", MODE_PRIVATE);

        checkUser = checkUserConnected(settings);

        nitif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(HomeActivity.this, "1")
                            .setSmallIcon(R.drawable.small_icon)
                            .setContentTitle("textTitle")
                            .setContentText("textContent")
                            .setPriority(Notification.PRIORITY_MAX); //Important for heads-up notification

                    Notification buildNotification = mBuilder.build();
                    NotificationManager mNotifyMgr = (NotificationManager) HomeActivity.this.getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(001, buildNotification);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        homeFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });

        profileFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUser) {
                    replaceFragment(new ProfileFragment());
                } else {
                    Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginPage);
                }
            }
        });
        connectTopusher();

    }

    public void replaceFragment(Fragment frag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, frag);
        fragmentTransaction.commit();
    }

    /**
     * fonction redirection à la page login
     */
    public void redirectToLogin(View v) {
        try {
            Intent loginPage = new Intent(this, LoginActivity.class);
            startActivity(loginPage);
        } catch (Exception e) {
            Log.e("Activity exception", e.getMessage());
        }
    }

    public void redirectToRegister(View v) {
        try {
            Intent redirectpage = new Intent(this, RegisterActivity.class);
            startActivity(redirectpage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectProfile(View v) {
        try {
            Intent redirectProfile = new Intent(this, ProfileActivity.class);
            startActivity(redirectProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * fonction pour verifier si un user est deja dans le cache du telephone
     *
     * @param sh
     * @return
     */
    public Boolean checkUserConnected(SharedPreferences sh) {
        if (sh.contains("jsondata")) {
            return true;
        } else {
            return false;
        }
    }


    public void connectTopusher() {
        // Create a new Pusher instance
        PusherOptions options = new PusherOptions().setCluster("eu");
        Pusher pusher = new Pusher("6028606e361eb648ceb3", options);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());

            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
                System.out.println(message);
                e.printStackTrace();
            }
        }, ConnectionState.ALL);

// Subscribe to a channel
        Channel channel = pusher.subscribe("channel-1");

// Bind to listen for events called "my-event" sent to "my-channel"
        channel.bind("test_event", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                System.out.println("Received event with data: " + event.toString());
                String message = "";
                String title = "";
                try {
                    JSONObject jsonObject = new JSONObject(event.toString());
                    JSONObject mes = new JSONObject(jsonObject.getString("data"));
                    message = mes.getString("message");
                    title = mes.getString("title");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(HomeActivity.this, "1")
                        .setSmallIcon(R.drawable.small_icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(Notification.PRIORITY_MAX); //Important for heads-up notification

                Notification buildNotification = mBuilder.build();
                NotificationManager mNotifyMgr = (NotificationManager) HomeActivity.this.getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(001, buildNotification);
            }
        });
//// Disconnect from the service
//        pusher.disconnect();
//// Reconnect, with all channel subscriptions and event bindings automatically recreated
   pusher.connect();
    }
}