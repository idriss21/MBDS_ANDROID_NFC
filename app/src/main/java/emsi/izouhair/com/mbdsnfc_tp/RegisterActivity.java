package emsi.izouhair.com.mbdsnfc_tp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by idriss on 26/12/2016.
 */
public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener{


    private Button signIn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        signIn = (Button) findViewById(R.id.btnRegister);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnRegister:
                startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
                break;

        }
    }


}
