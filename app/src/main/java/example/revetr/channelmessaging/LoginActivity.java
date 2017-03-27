package example.revetr.channelmessaging;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {

    private static final String TAG = "LoginActivity";

    protected String Login;
    protected String Password;
    protected Button ValidateButton;
    HashMap<String, String> postparams = new HashMap<>();
    Handler mHandlerTada = new Handler(); // android.os.handler
    int mShortDelay = 4000; //milliseconds




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ValidateButton =(Button) findViewById(R.id.ValidateButton);
        ValidateButton.setOnClickListener(this);
        mHandlerTada.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.Tada)
                        .duration(1000)
                        .playOn(findViewById(R.id.logo));
                mHandlerTada.postDelayed(this, mShortDelay);
            }
        }, mShortDelay);

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
        Log.e(TAG, "onDownloadCompleted: " + content );
        Response reponse = gson.fromJson(content, Response.class);
        if(reponse != null){
            if(reponse.getAccesstoken() != null){
                SharedPreferences settings = getSharedPreferences("carcajou",0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("accesstoken",reponse.getAccesstoken());
                editor.commit();
                Intent loginIntent = new Intent(LoginActivity.this, ChannelListActivity.class);
                startActivity(loginIntent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, findViewById(R.id.logo), "logo").toBundle());
                /*Intent myIntent = new Intent(getApplicationContext(),ChannelListActivity.class);
                startActivity(myIntent);*/
            }
            else{
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, R.string.wrong_login, duration);
                toast.show();
            }
        }
        else{
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.llBackground), R.string.no_internet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
