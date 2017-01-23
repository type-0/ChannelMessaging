package example.revetr.channelmessaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {

    protected String Login;
    protected String Password;
    protected Button ValidateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ValidateButton =(Button) findViewById(R.id.ValidateButton);
        ValidateButton.setOnClickListener(this);
    }

    public LoginActivity() {
        EditText txtFieldId = (EditText) findViewById(R.id.Login);
        EditText txtFieldPassword = (EditText) findViewById(R.id.Password);
        Login = txtFieldId.getText().toString();
        Password = txtFieldPassword.getText().toString();
    }

    @Override
    public void onClick(View v) {
        Downloader d = new Downloader();
        d.setDowlist(this);
        d.execute();
    }

    @Override
    public void onDownloadCompleted(String content) {

    }
}
