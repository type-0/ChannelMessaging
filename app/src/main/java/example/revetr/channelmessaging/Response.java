package example.revetr.channelmessaging;

/**
 * Created by revetr on 23/01/2017.
 */
public class Response {
    private String response;
    private String code;

    public String getAccesstoken() {
        return accesstoken;
    }

    private String accesstoken;

    public Response(String response, String code, String accesstoken) {
        this.response = response;
        this.code = code;
        this.accesstoken = accesstoken;
    }

    public Response(){

    }
}
