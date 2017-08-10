package mx.com.cesarcorona.gembaparthner.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;

import java.util.Timer;
import java.util.TimerTask;

import mx.com.cesarcorona.gembaparthner.MainActivity;
import mx.com.cesarcorona.gembaparthner.R;

public class LoginRegisterActivity extends AppCompatActivity {

    public static String TAG = LoginRegisterActivity.class.getSimpleName();

    private EditText userField, passField;
    private Button loginButton, registerButton;
    private TextView recoveryPass;

    private String userText, passText;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_register_login);
        mAuth = FirebaseAuth.getInstance();

        userField = (EditText) findViewById(R.id.user_field);
        passField = (EditText) findViewById(R.id.pass_field);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        recoveryPass = (TextView) findViewById(R.id.recover_pass);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAtempt();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAtempt();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    FirebaseCrash.log(TAG+" onAuthStateChanged:signed_in:" + user.getUid());
                    Intent mainActivity = new Intent(LoginRegisterActivity.this,MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                } else {

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


    @Override
    public void onBackPressed() {

    }

    private void loginAtempt(){
        if((userField != null && userField.length() > 0) && (passField != null && passField.length() > 0)){
            userText = userField.getText().toString();
            passText = passField.getText().toString();

            mAuth.signInWithEmailAndPassword(userText, passText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginRegisterActivity.this, R.string.error_login,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(LoginRegisterActivity.this, R.string.error_campos,
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void registerAtempt(){
        if((userField != null && userField.length() > 0) && (passField != null && passField.length() > 0)){
            userText = userField.getText().toString();
            passText = passField.getText().toString();

            mAuth.createUserWithEmailAndPassword(userText, passText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseCrash.log(TAG + "createUserWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginRegisterActivity.this, R.string.error_registro,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(LoginRegisterActivity.this, R.string.error_campos,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
