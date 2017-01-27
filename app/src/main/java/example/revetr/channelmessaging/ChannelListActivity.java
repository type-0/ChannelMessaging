package example.revetr.channelmessaging;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelListActivity extends AppCompatActivity {
    private ListView channelListe;
    HashMap<String, String> postparams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        SharedPreferences settings = getSharedPreferences("carcajou",0);
        String accestoken = settings.getString("accesstoken","");
        postparams.put("accesstoken",accestoken);
        String url = "http://www.raphaelbischof.fr/messaging/?function=getchannels";
        Downloader d = new Downloader(url,postparams);
    }

    public void onDownloadCompleted(String content) {
        Gson gson = new Gson();
        Channels listChannels = gson.fromJson(content, Channels.class);

    }

}
