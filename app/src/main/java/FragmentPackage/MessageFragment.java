package FragmentPackage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import example.revetr.channelmessaging.Channel;
import example.revetr.channelmessaging.ChannelMessages;
import example.revetr.channelmessaging.Downloader;
import example.revetr.channelmessaging.MessageArrayAdapter;
import example.revetr.channelmessaging.OnDownloadCompleteListener;
import example.revetr.channelmessaging.R;
import example.revetr.channelmessaging.RecordSoundDialogFragment;

/**
 * Created by revetr on 27/02/2017.
 */
public class MessageFragment extends Fragment implements View.OnClickListener, OnDownloadCompleteListener {
    private ListView messagesListe;

    private ChannelMessages MessagesList;
    private Button sendButton;
    private Button soundButton;
    private int channelid;
    private String newMessage;
    private EditText txtFieldMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_fragment,container);
        final OnDownloadCompleteListener dowlisten = this;
        sendButton =(Button) v.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        soundButton =(Button) v.findViewById(R.id.Son);
        soundButton.setOnClickListener(this);
        txtFieldMessage = (EditText) v.findViewById(R.id.newMessage);
        messagesListe =(ListView) v.findViewById(R.id.messagesList);
        Intent intent = getActivity().getIntent();
        channelid = intent.getIntExtra("channelID",-1);
        SharedPreferences settings = getActivity().getSharedPreferences("carcajou",0);
        final String accestoken = settings.getString("accesstoken","");
        final String url ="http://www.raphaelbischof.fr/messaging/?function=getmessages";
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if(getActivity() != null){
                    HashMap<String, String> postparams = new HashMap<>();
                    postparams.put("accesstoken",accestoken);
                    if(channelid != -1) {
                        postparams.put("channelid", "" + channelid);
                        Downloader d = new Downloader(url, postparams, 3);
                        d.setDowlist(dowlisten);
                        d.execute();
                    }
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(r);
        return v;
    }


    @Override
    public void onClick(View v) {
        if(v == sendButton){
            newMessage = txtFieldMessage.getText().toString();
            SharedPreferences settings = getActivity().getSharedPreferences("carcajou",0);
            String accestoken = settings.getString("accesstoken","");
            HashMap<String, String> p = new HashMap<>();
            p.put("accesstoken",accestoken);
            p.put("channelid",""+channelid);
            p.put("message", newMessage);
            String url = "http://www.raphaelbischof.fr/messaging/?function=sendmessage";
            Downloader d = new Downloader(url,p,4);
            d.setDowlist(this);
            d.execute();
        }
        else if(v == soundButton){
            DialogFragment newFragment = new RecordSoundDialogFragment();
            newFragment.show(getActivity().getSupportFragmentManager(), "son");
        }

    }

    @Override
    public void onDownloadCompleted(String content, int requestcode) {
        if(getActivity() != null) {
            Gson gson = new Gson();
            if (requestcode == 3) {
                MessagesList = gson.fromJson(content, ChannelMessages.class);
                if (MessagesList.getMessages() != null) {
                    messagesListe.setAdapter(new MessageArrayAdapter(getActivity().getApplicationContext(), MessagesList.getMessages()));
                }
            }
        }
    }

    public void changeChannelId(int channelId){
        this.channelid = channelId;
    }
}
