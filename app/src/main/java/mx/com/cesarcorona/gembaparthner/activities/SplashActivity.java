package mx.com.cesarcorona.gembaparthner.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;

import java.util.Timer;
import java.util.TimerTask;

import mx.com.cesarcorona.gembaparthner.MainActivity;
import mx.com.cesarcorona.gembaparthner.R;


public class SplashActivity extends AppCompatActivity {


    public static String TAG = SplashActivity.class.getSimpleName();
    private static final long SPLASH_SCREEN_DELAY = 2000;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    FirebaseCrash.log(TAG+" onAuthStateChanged:signed_in:" + user.getUid());
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            Intent loggedIntend = new Intent(SplashActivity.this,WizardActivity.class);
                            startActivity(loggedIntend);
                            finish();
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, SPLASH_SCREEN_DELAY);

                } else {
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            FirebaseCrash.log(TAG+" onAuthStateChanged:signed_out");
                            Intent logigRegisterIntent = new Intent(SplashActivity.this,LoginRegisterActivity.class);
                            startActivity(logigRegisterIntent);
                            finish();
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, SPLASH_SCREEN_DELAY);
                }


            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
