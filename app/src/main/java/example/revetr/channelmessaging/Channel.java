package example.revetr.channelmessaging;

/**
 * Created by revetr on 27/01/2017.
 */
public class Channel {
    public Channel(int channelId, String channelName, int connectedUsers) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.connectedUsers = connectedUsers;
    }

    private int channelId;
    private String channelName;
    private int connectedUsers;

}
