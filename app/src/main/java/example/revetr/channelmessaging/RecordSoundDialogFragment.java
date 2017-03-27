package example.revetr.channelmessaging;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by revetr on 15/03/2017.
 */
public class RecordSoundDialogFragment extends DialogFragment {

    private Button recordButton;
    private Button playButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.sound_record, null));
        /*recordButton =(Button) findViewById(R.id.recordbutton);
        recordButton.setOnClickListener(this);
        playButton =(Button) findViewById(R.id.playbutton);
        playButton.setOnClickListener(this);*/
        builder.setMessage(R.string.dialog_record_sound)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
