package example.revetr.channelmessaging;

/**
 * Created by revetr on 06/02/2017.
 */
public class ChannelMessage {
    public String getUsername() {
        return username;
    }

    public ChannelMessage(String username, int userID, String message, String date, String imageUrl) {
        this.username = username;
        this.userID = userID;
        this.message = message;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    private String username;
    private int userID;

    public String getMessage() {
        return message;
    }

    public int getUserID() {
        return userID;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private String message;
    private String date;

    private String imageUrl;


}
