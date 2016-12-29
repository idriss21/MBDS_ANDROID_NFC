package emsi.izouhair.com.mbdsnfc_tp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emsi.izouhair.com.mbdsnfc_tp.R;
import emsi.izouhair.com.mbdsnfc_tp.classes.Person;

/**
 * Created by idriss on 28/12/2016.
 */
public class PersonItemAdapter extends BaseAdapter {

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
    public View getView(int position, View convertView, ViewGroup arg2) {
        View v = convertView;

        PersonViewHolder viewHolder = null;
        if(v==null){
            v = View.inflate(context, R.layout.item_list_person, null);
            viewHolder = new PersonViewHolder();
            viewHolder.nom_prenom= (TextView)v.findViewById(R.id.username_item);
            viewHolder.connected = (TextView)v.findViewById(R.id.status_item);
            viewHolder.avatar = (CircleImageView) v.findViewById(R.id.image_item);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (PersonViewHolder) v.getTag();
        }
        Person p = person.get(position);
        viewHolder.nom_prenom.setText(p.getNom()+" "+p.getPrenom());

       if(p.isConnect()) {viewHolder.connected.setText("connect");  viewHolder.avatar.setBorderColor(context.getResources().getColor(R.color.connect));}
        else {viewHolder.connected.setText("disconnect"); viewHolder.avatar.setBorderColor(context.getResources().getColor(R.color.disconnect));}

        return v;
    }

    class PersonViewHolder{
        TextView nom_prenom;
        TextView connected;
        CircleImageView avatar;
    }

}