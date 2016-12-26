package emsi.izouhair.com.mbdsnfc_tp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by idriss on 26/12/2016.
 */


public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button login;
    private TextView txtForgetPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        txtForgetPassword = (TextView) findViewById(R.id.txtForgetPassword);
        login = (Button)findViewById(R.id.btnConnect);


        txtForgetPassword.setOnClickListener(this) ;
        login.setOnClickListener(this) ;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnConnect:
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
               break;
            case R.id.txtForgetPassword:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }
}
