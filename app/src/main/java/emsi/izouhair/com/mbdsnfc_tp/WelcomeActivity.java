package emsi.izouhair.com.mbdsnfc_tp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import emsi.izouhair.com.mbdsnfc_tp.classes.Person;
import emsi.izouhair.com.mbdsnfc_tp.sessionManaged.GsonSP;

/**
 * Created by idriss on 26/12/2016.
 */

public class WelcomeActivity extends AppCompatActivity {


    private TextView msg;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_welcome);

        msg = (TextView) findViewById(R.id.welcomeMsg);



        Person person = GsonSP.getSavedObjectFromPreference(this, "mPreference", "currentPerson", Person.class);

        if(person != null)
        {
            Toast.makeText(getApplication()," Login Success !!",Toast.LENGTH_LONG).show();
            String result ="  WELCOME "+
                    person.getNom()+"  "+person.getPrenom()+
                    " \n Telephone : "+person.getTelephone();
                    msg.setText(result);
        }else
        {
            Toast.makeText(getApplication(),"Error to found !!",Toast.LENGTH_LONG).show();
        }



    }
}
