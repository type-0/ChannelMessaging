package example.revetr.channelmessaging;

import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by revetr on 06/02/2017.
 */
public class ChannelMessages{
    public ChannelMessages(ArrayList<ChannelMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<ChannelMessage> getMessages() {
        return messages;
    }

    private ArrayList<ChannelMessage> messages;
}
