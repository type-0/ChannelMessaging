package example.revetr.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by revetr on 27/01/2017.
 */
public class MyArrayAdapter extends ArrayAdapter<Channel> {
    private final Context context;
    private final List<Channel> values;

    public MyArrayAdapter(Context context, List<Channel> values) {
        super(context, R.layout.channel_line, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.channel_line, parent, false);
        TextView nameChannel = (TextView) rowView.findViewById(R.id.nameChannel);
        TextView usersConnected = (TextView) rowView.findViewById(R.id.usersConnected);
        Channel chan = values.get(position);
            nameChannel.setText(chan.getname());
            usersConnected.setText("Nombre d'utilisateurs connectés: " + String.valueOf(chan.getconnectedusers()));
        return rowView;
    }
}
