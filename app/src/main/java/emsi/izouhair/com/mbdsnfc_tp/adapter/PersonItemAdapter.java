package emsi.izouhair.com.mbdsnfc_tp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emsi.izouhair.com.mbdsnfc_tp.R;
import emsi.izouhair.com.mbdsnfc_tp.classes.Person;

/**
 * Created by idriss on 28/12/2016.
 */
public class PersonItemAdapter extends BaseAdapter  {

    private Context context;
    public List<Person> person;



    public PersonItemAdapter(Context context, List<Person> person) {
        this.context = context;
        this.person = person;
    }

    @Override
    public int getCount() {
        return person.size();
    }

    @Override
    public Object getItem(int arg0) {
        return person.get(arg0);
    }


    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        View v = convertView;

        PersonViewHolder viewHolder = null;
        if(v==null){
            v = View.inflate(context, R.layout.item_list_person, null);
            viewHolder = new PersonViewHolder();
            viewHolder.nom_prenom= (TextView)v.findViewById(R.id.username_item);
            viewHolder.connected = (TextView)v.findViewById(R.id.status_item);
            viewHolder.avatar = (CircleImageView) v.findViewById(R.id.image_item);
            viewHolder.btnDelete = (ImageView) v.findViewById(R.id.deleteBtn);
            v.setTag(viewHolder);


        }
        else{
            viewHolder = (PersonViewHolder) v.getTag();
        }


        Person p = person.get(position);
        viewHolder.nom_prenom.setText(p.getNom()+" "+p.getPrenom());

       if(p.isConnect()) {viewHolder.connected.setText("connect");  viewHolder.avatar.setBorderColor(context.getResources().getColor(R.color.connect));}
        else {viewHolder.connected.setText("disconnect"); viewHolder.avatar.setBorderColor(context.getResources().getColor(R.color.disconnect));}


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Person p =(Person) getItem(position);
                String id = p.getId();
                String username = p.getNom()+"  "+p.getPrenom();
                dialogBox(username);
            }
        });

        return v;
    }


    public void dialogBox(String username) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Delete id = "+username);
        alertDialogBuilder.setMessage("Are you sure  ??");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }





}

    class PersonViewHolder{
        TextView nom_prenom;
        TextView connected;
        CircleImageView avatar;
        ImageView btnDelete ;
    }





