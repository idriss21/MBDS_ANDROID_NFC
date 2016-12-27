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
        person.setNom(JsonObject.getString("nom"));
        person.setPrenom(JsonObject.getString("prenom"));
        person.setEmail(JsonObject.getString("email"));
        person.setTelephone(JsonObject.getString("telephone"));
        /*if(JsonObject.getString("createdAt") != null) person.setCreatedAt(JsonObject.getString("createdAt"));
        if(JsonObject.getString("createdBy") != null) person.setCreatedby(JsonObject.getString("createdBy"));*/
        return person;
    }
}

