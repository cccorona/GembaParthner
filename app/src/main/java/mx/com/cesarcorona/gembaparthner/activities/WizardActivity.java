package mx.com.cesarcorona.gembaparthner.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;


import mx.com.cesarcorona.gembaparthner.R;
import mx.com.cesarcorona.gembaparthner.adapters.WizardAdapter;
import mx.com.cesarcorona.gembaparthner.fragments.WizardFragmentDocuments;
import mx.com.cesarcorona.gembaparthner.fragments.WizardFragmentPlace;
import mx.com.cesarcorona.gembaparthner.fragments.WizardFragmentProfile;

public class WizardActivity extends AppCompatActivity implements StepperLayout.StepperListener,
        WizardFragmentProfile.OnProfilePictureInterface , WizardFragmentPlace.OnPlaceInterface ,
        WizardFragmentDocuments.OnDocumentsInterface{




    private StepperLayout mStepperLayout;

    private Uri urlProfilePicture;
    private Uri ife ,domicilio;
    private Place parkingPlace;

    private ProgressDialog pDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new WizardAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
        pDialog = new ProgressDialog(WizardActivity.this);
        pDialog.setMessage("Por favor espera...");
        pDialog.setCancelable(false);
    }

    @Override
    public void onCompleted(View completeButton) {
       // Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
        createProfileIntent();
    }

    @Override
    public void onError(VerificationError verificationError) {
        //Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
      //  Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void OnProfileSelected(Uri url) {
        urlProfilePicture = url;

    }


    @Override
    public void onBackPressed() {
    }

    @Override
    public void OnPlaceSelected(Place place) {
        parkingPlace = place;

    }

    @Override
    public void OnIdentificacionUpload(Uri uri) {
        ife = uri;
    }

    @Override
    public void OnDomicilioUpload(Uri uri) {
       domicilio = uri;
    }

    private void createProfileIntent(){
        showpDialog();
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
