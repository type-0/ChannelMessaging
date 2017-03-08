package FragmentPackage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

/**
 * Created by revetr on 27/02/2017.
 */
public class MessageFragment extends Fragment implements View.OnClickListener, OnDownloadCompleteListener {
    private ListView messagesListe;
    HashMap<String, String> postparams = new HashMap<>();
    private ChannelMessages MessagesList;
    private Button sendButton;
    private String newMessage;
    private EditText txtFieldMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_fragment,container);
        final OnDownloadCompleteListener dowlisten = this;
        sendButton =(Button) v.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        txtFieldMessage = (EditText) v.findViewById(R.id.newMessage);
        messagesListe =(ListView) v.findViewById(R.id.messagesList);
        Intent intent = getActivity().getIntent();
        int channelid = intent.getIntExtra("channelID",0);
        SharedPreferences settings = getActivity().getSharedPreferences("carcajou",0);
        String accestoken = settings.getString("accesstoken","");
        postparams.put("accesstoken",accestoken);
        postparams.put("channelid",""+channelid);
        final String url ="http://www.raphaelbischof.fr/messaging/?function=getmessages";
        Downloader d = new Downloader(url,postparams,3);
        d.setDowlist(this);
        d.execute();
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if(getActivity() != null){
                    Downloader d = new Downloader(url,postparams,3);
                    d.setDowlist(dowlisten);
                    d.execute();
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(r, 1000);
        return v;
    }


    @Override
    public void onClick(View v) {
        newMessage = txtFieldMessage.getText().toString();
        Intent intent = getActivity().getIntent();
        int channelid = intent.getIntExtra("channelID",0);
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

    @Override
    public void onDownloadCompleted(String content, int requestcode) {
        Gson gson = new Gson();
        if( requestcode == 3){
            MessagesList = gson.fromJson(content, ChannelMessages.class);
            messagesListe.setAdapter(new MessageArrayAdapter(getActivity().getApplicationContext(), MessagesList.getMessages()));
        }
    }

    public void changeChannelId(int channelId){
        postparams.put("channelid",""+channelId);
    }
}
