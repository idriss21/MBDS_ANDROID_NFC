package emsi.izouhair.com.mbdsnfc_tp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import emsi.izouhair.com.mbdsnfc_tp.adapter.PersonItemAdapter;
import emsi.izouhair.com.mbdsnfc_tp.classes.Person;
import emsi.izouhair.com.mbdsnfc_tp.sessionManaged.GsonSP;

/**
 * Created by idriss on 26/12/2016.
 */

public class WelcomeActivity extends AppCompatActivity {


    private TextView msg;
    private List<Person> listPerson ;
    private ImageView deleteBtn;
    ProgressDialog progressDialog;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_welcome);
        //msg = (TextView) findViewById(R.id.welcomeMsg);
        new getPersonrTask().execute();


    }


    class getPersonrTask extends AsyncTask<Person,Void,String > {

        String url = "http://95.142.161.35:8080/person";
        @Override
        protected String  doInBackground(Person... person1) {






            try{
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);

                // add header
                get.setHeader("Content-Type", "application/json");


                HttpResponse response = client.execute(get);
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " +
                        response.getStatusLine().getStatusCode());

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                System.out.println(result.toString());
                return result.toString();
            } catch (Exception e){

            }
            return  null;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(String  persons) {
            super.onPostExecute(persons);

            showProgressDialog(false);

            //Enlever le loading
            //Traiter la person
            if (persons!=null) {

                Toast.makeText(getApplicationContext()," Person Found  !!",Toast.LENGTH_LONG).show();
                ListView lst = (ListView)findViewById(R.id.list_person);
                List<Person> listPersons = new ArrayList<>();


                try {


                    JSONArray listPerson = new JSONArray(persons);



                    for(int i=0;i<listPerson.length();i++)
                    {
                        JSONObject jObject = listPerson.getJSONObject(i);
                        listPersons.add( GsonSP.JsonToClasse(jObject));
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }

                PersonItemAdapter adapter = new PersonItemAdapter(WelcomeActivity.this, listPersons);
                lst.setAdapter(adapter);


            }else{
                Toast.makeText(getApplicationContext(),"Nothing returend  !!",Toast.LENGTH_LONG).show();
            }
            //Renvoyer vers le login
            //Fermer l'activité Enregistrer
        }


                   /*
[
    {
    sexe: "M",
    email: "florian.m.06@gmail.com ",
    password: "toto",
    createdby: "Massa & Moise",
    prenom: "Florian ",
    nom: "Massa",
    telephone: "06060606006",
    connected: true,
    createdAt: "2015-10-27T12:30:54.104Z",
    updatedAt: "2015-10-27T12:30:54.104Z",
    id: "562f6e7e7db67c3f057e3223"
    },
    {
    sexe: "M",
    email: "yoann@bana.ne",
    password: "actuellement",
    createdby: "Massa & Moise",
    prenom: "Yoann ",
    nom: "Moise",
    telephone: "0123456789",
    connected: true,
    createdAt: "2015-10-27T12:37:15.763Z",
    updatedAt: "2015-10-27T12:37:15.763Z",
    id: "562f6ffb7db67c3f057e3224"
    }
]

             */

    }





// Dialog box



    public void showProgressDialog(boolean isVisible) {
        if (isVisible) {
            if(progressDialog==null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(this.getResources().getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        progressDialog = null;
                    }
                });
                progressDialog.show();
            }
        }
        else {
            if(progressDialog!=null) {
                progressDialog.dismiss();
            }
        }
    }

}
