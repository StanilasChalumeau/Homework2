package fr.stanislas.homework2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DeleteDialog extends DialogFragment {
    static DeleteDialog newInstance(){
        return new DeleteDialog();
    }

    public DeleteDialog (){};

    /* The activity that creates an instance of this dialog fragment must implement
     * this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it*/
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);

    }

    //use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

   /* //Override the Fragment.onAttach() method to instanciate the NoticeDialogListener
    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public void onAttach(Activity activity){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1){
            super.onAttach(activity);
            //Verify that the host activity implements the callback interface
            try{
                //Instanciate the NoticeDialogListener so we can send events to the host
                mListener = (NoticeDialogListener) activity;
            }catch (ClassCastException e){
                //The activity doesn't implement the interface, throw exception
                throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
            }
        }
    } //37*/


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity = getActivity();
        //verify that the host activity implements the callback interface
        try {
            // Instanciate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        }catch (ClassCastException e) {
            //The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + "must implement NoticeDialogListener");
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.dialog_title))
                .setPositiveButton(getResources().getString(R.string.dialog_OK), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        //user clicked ok button
                        mListener.onDialogPositiveClick(DeleteDialog.this);

                    }
                })
                .setNegativeButton(getResources().getString(R.string.dialog_Cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        mListener.onDialogNegativeClick(DeleteDialog.this);

                    }
                });







        //create the AlertDialog object and return it
        return builder.create();
    }

}
