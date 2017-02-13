package example.revetr.channelmessaging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {
    protected String Login;
    protected String Password;
    protected Button ValidateButton;
    HashMap<String, String> postparams = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ValidateButton =(Button) findViewById(R.id.ValidateButton);
        ValidateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        EditText txtFieldId = (EditText) findViewById(R.id.Login);
        EditText txtFieldPassword = (EditText) findViewById(R.id.Password);
        Login = txtFieldId.getText().toString();
        Password = txtFieldPassword.getText().toString();
        postparams.put("username", Login);
        postparams.put("password", Password);
        String url = "http://www.raphaelbischof.fr/messaging/?function=connect";
        Downloader d = new Downloader(url,postparams,1);
        d.setDowlist(this);
        d.execute();
    }

    @Override
    public void onDownloadCompleted(String content, int requestcode) {
        Gson gson = new Gson();
        Response reponse = gson.fromJson(content, Response.class);
        if(reponse.getAccesstoken() != null){
            SharedPreferences settings = getSharedPreferences("carcajou",0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("accesstoken",reponse.getAccesstoken());
            editor.commit();
            Intent myIntent = new Intent(getApplicationContext(),ChannelListActivity.class);
            startActivity(myIntent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Informations de connexion erronées";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}
