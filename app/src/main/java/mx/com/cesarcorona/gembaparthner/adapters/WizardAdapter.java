package mx.com.cesarcorona.gembaparthner.adapters;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import mx.com.cesarcorona.gembaparthner.fragments.WizardFragmentDocuments;
import mx.com.cesarcorona.gembaparthner.fragments.WizardFragmentPlace;
import mx.com.cesarcorona.gembaparthner.fragments.WizardFragmentProfile;

/**
 * Created by Corona on 8/10/2017.
 */

public class WizardAdapter extends AbstractFragmentStepAdapter {

    public WizardAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
         switch (position){
             case 0:
                 return  new WizardFragmentProfile();
             case 1:
                 return  new WizardFragmentPlace();
             case 2:
                 return new WizardFragmentDocuments();

         }
         return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types

           StepViewModel stepViewModel = null;

           switch (position){
               case 0:
                   stepViewModel = new StepViewModel.Builder(context)
                           .setTitle("Datos Personales") //can be a CharSequence instead
                           .create();
                   break;
               case 1:
                   stepViewModel = new StepViewModel.Builder(context)
                           .setTitle("Datos del Cajón") //can be a CharSequence instead
                           .create();
                   break;
               case 2:
                   stepViewModel = new StepViewModel.Builder(context)
                           .setTitle("Documentos") //can be a CharSequence instead
                           .create();
                   break;
           }
        return stepViewModel;
    }
}