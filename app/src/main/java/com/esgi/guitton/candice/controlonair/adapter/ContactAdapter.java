package com.esgi.guitton.candice.controlonair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Contact;

import java.util.List;

/**
 * Created by candiceguitton on 08/04/2018.
 */

public class ContactAdapter extends ArrayAdapter<Contact>{




    public interface OnContactClickListener {
        void onContactClickListener(long id);
    }

    private ContactAdapter.OnContactClickListener onContactClickListener;

    public ContactAdapter(Context context, List<Contact> contacts, OnContactClickListener onContactClickListener) {
        super(context, 0, contacts);
        this.onContactClickListener = onContactClickListener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contact contact = getItem(position);


        if (convertView == null) {
            int layout =  R.layout.contact_list_item;
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }

        ContactAdapter.ContactViewHolder viewHolder = (ContactAdapter.ContactViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ContactAdapter.ContactViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.number = (TextView) convertView.findViewById(R.id.contactNumber);
            convertView.setTag(viewHolder);
        }

        viewHolder.name.setText(contact.getName());
        viewHolder.number.setText(contact.getNumber());


        return convertView;
    }

    private class ContactViewHolder {
        public TextView name;
        public TextView number;
    }


}
