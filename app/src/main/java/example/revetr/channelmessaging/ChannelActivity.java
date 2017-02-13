package example.revetr.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {
    private ListView messagesListe;
    HashMap<String, String> postparams = new HashMap<>();
    private ChannelMessages MessagesList;
    private Button sendButton;
    private String newMessage;
    private EditText txtFieldMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final OnDownloadCompleteListener dowlisten = this;
        setContentView(R.layout.activity_channel);
        sendButton =(Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        txtFieldMessage = (EditText) findViewById(R.id.newMessage);
        messagesListe =(ListView) findViewById(R.id.messagesList);
        Intent intent = getIntent();
        int channelid = intent.getIntExtra("channelID",0);
        SharedPreferences settings = getSharedPreferences("carcajou",0);
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
                Downloader d = new Downloader(url,postparams,3);
                d.setDowlist(dowlisten);
                d.execute();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);
    }

    @Override
    public void onDownloadCompleted(String content, int requestcode) {
        Gson gson = new Gson();
        if( requestcode == 3){
            MessagesList = gson.fromJson(content, ChannelMessages.class);
            messagesListe.setAdapter(new MessageArrayAdapter(getApplicationContext(), MessagesList.getMessages()));
        }
    }

    @Override
    public void onClick(View v) {
        newMessage = txtFieldMessage.getText().toString();
        Intent intent = getIntent();
        int channelid = intent.getIntExtra("channelID",0);
        SharedPreferences settings = getSharedPreferences("carcajou",0);
        String accestoken = settings.getString("accesstoken","");
        postparams.put("accesstoken",accestoken);
        postparams.put("channelid",""+channelid);
        postparams.put("message", newMessage);
        String url = "http://www.raphaelbischof.fr/messaging/?function=sendmessage";
        Downloader d = new Downloader(url,postparams,4);
        d.setDowlist(this);
        d.execute();
    }
}
