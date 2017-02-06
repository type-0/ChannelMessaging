package example.revetr.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelActivity extends AppCompatActivity implements OnDownloadCompleteListener {
    private ListView messagesListe;
    HashMap<String, String> postparams = new HashMap<>();
    private ChannelMessages MessagesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final OnDownloadCompleteListener dowlisten = this;
        setContentView(R.layout.activity_channel);
        messagesListe =(ListView) findViewById(R.id.messagesList);
        Intent intent = getIntent();
        int channelid = intent.getIntExtra("channelID",0);
        SharedPreferences settings = getSharedPreferences("carcajou",0);
        String accestoken = settings.getString("accesstoken","");
        postparams.put("accesstoken",accestoken);
        postparams.put("channelid",""+channelid);
        final String url ="http://www.raphaelbischof.fr/messaging/?function=getmessages";
        Downloader d = new Downloader(url,postparams);
        d.setDowlist(this);
        d.execute();
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Downloader d = new Downloader(url,postparams);
                d.setDowlist(dowlisten);
                d.execute();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);
    }

    @Override
    public void onDownloadCompleted(String content) {
        Gson gson = new Gson();
        MessagesList = gson.fromJson(content, ChannelMessages.class);
        messagesListe.setAdapter(new MessageArrayAdapter(getApplicationContext(), MessagesList.getMessages()));
    }
}
