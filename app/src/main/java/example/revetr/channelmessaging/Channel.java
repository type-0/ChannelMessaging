package example.revetr.channelmessaging;

/**
 * Created by revetr on 27/01/2017.
 */
public class Channel {
    public Channel(int channelID, String name, int connectedusers) {
        this.channelID = channelID;
        this.name = name;
        this.connectedusers = connectedusers;
    }

    private int channelID;

    public int getchannelID() {
        return channelID;
    }

    public String getname() {
        return name;
    }

    public int getconnectedusers() {
        return connectedusers;
    }

    private String name;
    private int connectedusers;

}
