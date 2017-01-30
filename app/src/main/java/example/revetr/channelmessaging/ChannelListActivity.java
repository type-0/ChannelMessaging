package example.revetr.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelListActivity extends AppCompatActivity implements OnDownloadCompleteListener, AdapterView.OnItemClickListener {
    private ListView channelListe;
    HashMap<String, String> postparams = new HashMap<>();
    private Channels listChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        channelListe =(ListView) findViewById(R.id.ChannelList);
        SharedPreferences settings = getSharedPreferences("carcajou",0);
        String accestoken = settings.getString("accesstoken","");
        postparams.put("accesstoken",accestoken);
        String url = "http://www.raphaelbischof.fr/messaging/?function=getchannels";
        Downloader d = new Downloader(url,postparams);
        d.setDowlist(this);
        d.execute();
        channelListe.setOnItemClickListener(this);

    }

    public void onDownloadCompleted(String content) {
        Gson gson = new Gson();
        Channels listChannels = gson.fromJson(content, Channels.class);
        channelListe.setAdapter(new MyArrayAdapter(getApplicationContext(), listChannels.getChannels()));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getApplicationContext(),ChannelActivity.class);
        myIntent.putExtra("channelID", listChannels.getChannels().);
        startActivity(myIntent);
    }
}
