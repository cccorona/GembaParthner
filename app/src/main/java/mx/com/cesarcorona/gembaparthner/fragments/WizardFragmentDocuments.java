package mx.com.cesarcorona.gembaparthner.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.picasso.Picasso;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import mx.com.cesarcorona.gembaparthner.R;

/**
 * Created by Corona on 8/10/2017.
 */

public class WizardFragmentDocuments extends Fragment implements Step {


    private static int RESULT_LOAD_IMAGE_IDENTIFICACION = 1;
    private static int RESULTADO_LOAD_DOMICILIO= 2;
    private EditText identificacionUpload, comprobanteDomicilio;

    private OnDocumentsInterface onDocumentsInterface;

    public interface  OnDocumentsInterface{
        void OnIdentificacionUpload(Uri uri);
        void OnDomicilioUpload(Uri uri);
    }


    public void setOnDocumentsInterface(OnDocumentsInterface onDocumentsInterface) {
        this.onDocumentsInterface = onDocumentsInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wizard_fragment_documentos, container, false);
        identificacionUpload = (EditText) v.findViewById(R.id.identificacion_upload);
        comprobanteDomicilio = (EditText) v.findViewById(R.id.domicilio_upload);

        identificacionUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(RESULT_LOAD_IMAGE_IDENTIFICACION);
            }
        });

        comprobanteDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(RESULTADO_LOAD_DOMICILIO);
            }
        });

        return v;
    }

    @Override
    public VerificationError verifyStep() {
          if(identificacionUpload.getText().length() >0 &&
                  comprobanteDomicilio.getText().length() >0){
              return null;
          }else{
              return new VerificationError("Debes subir los dos documentos requeridos");
          }

    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE_IDENTIFICACION){
            identificacionUpload.setText(data.getData().toString());

        }else if(requestCode == RESULTADO_LOAD_DOMICILIO){
            comprobanteDomicilio.setText(data.getData().toString());
        }

    }


    public void pickImage(int CODE) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CODE);

    }

}