package emsi.izouhair.com.mbdsnfc_tp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.TextUtils;
import emsi.izouhair.com.mbdsnfc_tp.classes.Person;
import emsi.izouhair.com.mbdsnfc_tp.sessionManaged.GsonSP;

/*
 * Created by idriss on 26/12/2016.
 */





public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button btnConnect;
    private TextView txtForgetPassword;
    private EditText login  , password;
    private Person person;
    ProgressDialog progressDialog;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_login);




        login = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtpassword);

        txtForgetPassword = (TextView) findViewById(R.id.txtForgetPassword);
        btnConnect = (Button)findViewById(R.id.btnConnect);


        txtForgetPassword.setOnClickListener(this) ;
        btnConnect.setOnClickListener(this) ;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnConnect:


                if(!TextUtils.isEmpty(login.getText()))
                {
                    if(!TextUtils.isEmpty(password.getText()))
                    {
                        person = new Person();
                        person.setPassword(password.getText().toString());
                        person.setEmail(login.getText().toString());

                        new LoginTask().execute();
                    }else
                    {
                       password.setError("empty Lassword");
                    }
                }else {
                    login.setError("empty Login");
                }





               break;
            case R.id.txtForgetPassword:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }


    }


    class LoginTask extends AsyncTask<Person,Void,String > {

            String url = "http://95.142.161.35:8080/person/login";
        @Override
        protected String  doInBackground(Person... person1) {


            /*

                données à envoyer :
                    {
                    "email" : "eamosse@gmail.com",
                    "password" : "1234"
                    }



             */

            try{
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                // add header
                post.setHeader("Content-Type", "application/json");
                JSONObject obj = new JSONObject();
                obj.put("email",person.getEmail() );
                obj.put("password",person.getPassword());
                StringEntity entity = new StringEntity(obj.toString());

                post.setEntity(entity);

                HttpResponse response = client.execute(post);
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + post.getEntity());
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
            //Afficher un loading "Patientez, inscription en cours..."

            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(String  person) {
            super.onPostExecute(person);

            showProgressDialog(false);
            /*

              Modèle de réponse en cas d'erreur
                    {
                    "success" : false,
                    "message" : "Nom d'utilisateur ou mot de passe incorrecte"
                    }

                 Modèle de réponse en cas de succès

                 {
                  "success": true,
                  "user": {
                    "nom": "Edouard",
                    "prenom": "Amosse",
                    "sexe": "Masculin",
                    "telephone": "123456789",
                    "email": "eamosse@gmail.com",
                    "createdby": "Amosse",
                    "password": "1234",
                    "connected": true,
                    "createdAt": "2014-10-15T20:27:09.998Z",
                    "updatedAt": "2014-10-15T20:27:09.998Z",
                    "id": "543ed89ed9573e6e76a02490"
                  }
                }
             */
            if (person!=null) {


                Log.i("LoginActivity",person);

                try {
                    /* JSONArray jsonArray = new JSONArray(person);
                    Log.i(RegisterActivity.class.getName(),
                            "Number of entries " + jsonArray.length());*/

                    JSONObject jObject = new JSONObject(person);
                    boolean success = jObject.getBoolean("success");
                    if(success == true)
                    {
                        JSONObject InfoPerson = jObject.getJSONObject("user");
                        Toast.makeText(getApplicationContext(),"Login Success   ",Toast.LENGTH_LONG).show();


                        Person  getPerson = GsonSP.JsonToClasse(InfoPerson); //convert JsonObject to Person Oject

                        GsonSP.saveObjectToSharedPreference(getApplicationContext(), "mPreference", "currentPerson", getPerson); //to store an object



                        Intent myIntent = new Intent(getApplicationContext(), WelcomeActivity.class);

                        startActivity(myIntent);
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Not Logged in  !!  "+jObject.getString("message"),Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{
                Toast.makeText(getApplicationContext(),"Nothing returend  !!",Toast.LENGTH_LONG).show();
            }
            //Renvoyer vers le login
            //Fermer l'activité Enregistrer
        }

    }




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

