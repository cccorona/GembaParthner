package mx.com.cesarcorona.gembaparthner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import mx.com.cesarcorona.gembaparthner.R;

/**
 * Created by Corona on 8/10/2017.
 */

public class WizardFragmentPlace extends Fragment implements Step {


    public static String TAG = WizardFragmentPlace.class.getSimpleName();
    SupportPlaceAutocompleteFragment autocompleteFragment;
    private Place placeSelected ;
    private OnPlaceInterface placeInterface;


    public interface OnPlaceInterface{
        void OnPlaceSelected(Place place);
    }

    public void setPlaceInterface(OnPlaceInterface placeInterface) {
        this.placeInterface = placeInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wizard_fragment_place, container, false);
        final EditText adress = (EditText) v.findViewById(R.id.adress_text);
         autocompleteFragment= (SupportPlaceAutocompleteFragment)
                 getChildFragmentManager().
                         findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY).setCountry("MX")
                .build();
        autocompleteFragment.setFilter(typeFilter);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());
                placeSelected = place;
                adress.setText(placeSelected.getAddress());
                if(placeInterface != null){
                    placeInterface.OnPlaceSelected(placeSelected);
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        return v;
    }

    @Override
    public VerificationError verifyStep() {
        if(placeSelected != null){
            return  null;
        }else{
            return  new VerificationError("Debes seleccionar la direccion de tu estacionamiento");
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

}