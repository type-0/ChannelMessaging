package FragmentPackage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import example.revetr.channelmessaging.ChannelListActivity;
import example.revetr.channelmessaging.Channels;
import example.revetr.channelmessaging.Downloader;
import example.revetr.channelmessaging.MyArrayAdapter;
import example.revetr.channelmessaging.OnDownloadCompleteListener;
import example.revetr.channelmessaging.R;

/**
 * Created by revetr on 27/02/2017.
 */
public class ChannelListFragment extends Fragment implements OnDownloadCompleteListener {
    private ListView channelListe;
    HashMap<String, String> postparams = new HashMap<>();

    public Channels getListChannels() {
        return listChannels;
    }

    private Channels listChannels;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_list_fragment,container);
        channelListe = (ListView)v.findViewById(R.id.ChannelList);
        SharedPreferences settings = getActivity().getSharedPreferences("carcajou",0);
        String accestoken = settings.getString("accesstoken","");
        postparams.put("accesstoken",accestoken);
        String url = "http://www.raphaelbischof.fr/messaging/?function=getchannels";
        Downloader d = new Downloader(url,postparams,2);
        d.setDowlist(this);
        d.execute();
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        channelListe.setOnItemClickListener((ChannelListActivity)getActivity());
    }

    @Override
    public void onDownloadCompleted(String content, int requestcode) {
        Gson gson = new Gson();
        listChannels = gson.fromJson(content, Channels.class);
        channelListe.setAdapter(new MyArrayAdapter(getActivity().getApplicationContext(), listChannels.getChannels()));
    }
}
