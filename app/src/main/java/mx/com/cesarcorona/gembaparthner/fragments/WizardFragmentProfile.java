package mx.com.cesarcorona.gembaparthner.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import de.hdodenhof.circleimageview.CircleImageView;
import mx.com.cesarcorona.gembaparthner.R;


public class WizardFragmentProfile extends Fragment implements Step {


    FirebaseStorage storage = FirebaseStorage.getInstance();
    EditText nombreInput;
    EditText apellidoInput;
    EditText telefonoInput;
    CircleImageView profileLoader;

    private static int RESULT_LOAD_IMAGE = 1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wizard_fragment_profile, container, false);
        profileLoader = (CircleImageView) v.findViewById(R.id.profile_picture);
         nombreInput = (EditText) v.findViewById(R.id.nombre_field);
         apellidoInput =(EditText) v.findViewById(R.id.apellido_field);
         telefonoInput = (EditText) v.findViewById(R.id.telefono_field);

        profileLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });



        //initialize your UI

        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        VerificationError error = null;
        if(nombreInput.length()>0 && apellidoInput.length() >0 && telefonoInput.length() >0){
            error = null;
        }else{
            error = new VerificationError("Rellena todos los campos para continuar");
            Snackbar.make(nombreInput, R.string.fill_all_fields, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        return error;
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
        if(requestCode == RESULT_LOAD_IMAGE){
            Picasso.with(getActivity()).load(data.getData()).noPlaceholder()
                    .into(profileLoader);

        }
    }

    public void pickImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);

    }
}