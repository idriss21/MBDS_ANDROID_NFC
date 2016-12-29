package emsi.izouhair.com.mbdsnfc_tp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import emsi.izouhair.com.mbdsnfc_tp.classes.Person;
import emsi.izouhair.com.mbdsnfc_tp.sessionManaged.GsonSP;

/**
 * Created by idriss on 26/12/2016.
 */
public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {




    private Button signIn;
    private Person person;
    private RadioGroup sexe;
    ProgressDialog progressDialog;
    private EditText first_name , last_name , email, telephone , password ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_register);

        first_name = (EditText) findViewById(R.id.txtUsername);
        last_name = (EditText) findViewById(R.id.txtLastname);
        email = (EditText) findViewById(R.id.mail);
        telephone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.txtpassword);


        signIn = (Button) findViewById(R.id.btnRegister);
        signIn.setOnClickListener(this);

        //postData(this,"http://95.142.161.35:8080/person",null);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:


                sexe=(RadioGroup)findViewById(R.id.sexe);
                String gender = ((RadioButton) sexe.findViewById(sexe.getCheckedRadioButtonId())).getText().toString();


               if(TextUtils.isEmpty(first_name.getText()))
                {
                        first_name.setError("empty first name");
                }else
                if(TextUtils.isEmpty(last_name.getText()))
                {
                    last_name.setError("empty last  name");
                }else
                if(TextUtils.isEmpty(email.getText()))
                {
                    email.setError("empty last  email");
                }else
                if(TextUtils.isEmpty(password.getText()))
                {
                    password.setError("empty last  password");
                }else
                {




                    person = new Person();
                    person.setNom(first_name.getText().toString());
                    person.setPrenom(last_name.getText().toString());
                    person.setEmail(email.getText().toString());
                    person.setTelephone(telephone.getText().toString());
                    person.setPassword(password.getText().toString());
                    person.setCreatedby("Zouhair && Zakaria");
                    person.setSexe(gender);

                    new RegisterTask().execute();

                }



                break;




        }
    }



    class RegisterTask extends AsyncTask<Person,Void,String > {

        String url = "http://95.142.161.35:8080/person/";
        @Override
        protected String  doInBackground(Person... person1) {




            /*

                {
    "nom": "Edouard",
    "prenom" : "Amosse",
    "sexe": "Masculin",
    "telephone" : "123456789",
    "email" : "eamosse@gmail.com",
    "createdby" : "Amosse",
    "password" : "1234"
    }

             */

            try{
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                // add header
                post.setHeader("Content-Type", "application/json");
                JSONObject obj = new JSONObject();
                obj.put("prenom",person.getNom() );
                obj.put("nom",person.getPrenom());
                obj.put("sexe",person.getSexe());
                obj.put("telephone",person.getTelephone());
                obj.put("email",person.getEmail());
                obj.put("createdby",person.getCreatedby());
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
           showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(String  person) {
            super.onPostExecute(person);


            showProgressDialog(false);
            //Enlever le loading
            //Traiter la person
            if (person!=null) {

                Toast.makeText(getApplicationContext()," Person Found  !!",Toast.LENGTH_LONG).show();

                try {
                    /* JSONArray jsonArray = new JSONArray(person);
                    Log.i(RegisterActivity.class.getName(),
                            "Number of entries " + jsonArray.length());*/

                            JSONObject jObject = new JSONObject(person);
                        Log.d("RegistrerActivity",jObject.getString("nom")+"  "+jObject.getString("prenom"));

                    Person  getPerson = GsonSP.JsonToClasse(jObject); //convert JsonObject to Person Oject

                    GsonSP.saveObjectToSharedPreference(getApplicationContext(), "mPreference", "currentPerson", getPerson); //to store an object


                     Intent myIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        startActivity(myIntent);

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




   /* public static void postData(Context context, String url, HashMap<String,String> jsonObject)
    {
        AQuery locaAQuery = new AQuery(context);
        locaAQuery.ajax(url,jsonObject,JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);

                Log.e("postData",object.toString());
            }
        });

    }*/
}
