package emsi.izouhair.com.mbdsnfc_tp.sessionManaged;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import emsi.izouhair.com.mbdsnfc_tp.classes.Person;

/**
 * Created by idriss on 27/12/2016.
 */

public class GsonSP {

    public static void saveObjectToSharedPreference(Context context, String preferenceFileName, String serializedObjectKey, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }


    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }


    public  static  Person JsonToClasse(JSONObject JsonObject) throws JSONException {
        Person person = new Person();
        person.setId(JsonObject.getString("id"));

        if(JsonObject.has("nom"))   person.setNom(JsonObject.getString("nom"));
        if(JsonObject.has("prenom"))  person.setPrenom(JsonObject.getString("prenom"));
        if(JsonObject.has("email")) person.setEmail(JsonObject.getString("email"));
        if(JsonObject.has("telephone "))person.setTelephone(JsonObject.getString("telephone"));
        if(JsonObject.has("connected ")) person.setConnect(JsonObject.getBoolean("connected"));
        if(JsonObject.has("createdAt")) person.setCreatedAt(JsonObject.getString("createdAt"));
        if(JsonObject.has("createdBy") ) person.setCreatedby(JsonObject.getString("createdBy"));
        return person;
    }



}

