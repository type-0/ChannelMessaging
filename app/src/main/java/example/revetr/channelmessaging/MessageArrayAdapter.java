package example.revetr.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by revetr on 06/02/2017.
 */
public class MessageArrayAdapter extends ArrayAdapter<ChannelMessage> {
    private final Context context;
    private final List<ChannelMessage> values;

    public MessageArrayAdapter(Context context, List<ChannelMessage> values) {
        super(context, R.layout.message_line, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.message_line, parent, false);
        TextView user = (TextView) rowView.findViewById(R.id.User);
        TextView message = (TextView) rowView.findViewById(R.id.Message);
        TextView time = (TextView) rowView.findViewById(R.id.Time);
        ChannelMessage chanmessage = values.get(position);
            user.setText(chanmessage.getUsername());
            message.setText(": " + chanmessage.getMessage());
            time.setText(chanmessage.getDate());
        return rowView;
    }
}
