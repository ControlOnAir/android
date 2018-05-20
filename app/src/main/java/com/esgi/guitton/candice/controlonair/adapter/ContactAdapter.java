package com.esgi.guitton.candice.controlonair.adapter;

import android.content.Context;
import android.util.Log;
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

public class ContactAdapter extends ArrayAdapter<Contact> {

    public interface OnContactClickListener {
        void onContactClickListener(Contact id);
    }

    private ContactAdapter.OnContactClickListener onContactClickListener;

    public ContactAdapter(Context context, List<Contact> contacts, OnContactClickListener onContactClickListener) {
        super(context, R.layout.contact_list_item, contacts);
        this.onContactClickListener = onContactClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            int layout = R.layout.contact_list_item;
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }

        ContactAdapter.ContactViewHolder viewHolder = (ContactAdapter.ContactViewHolder) convertView.getTag();

        if (viewHolder == null) {
            viewHolder = new ContactAdapter.ContactViewHolder();
            viewHolder.name = convertView.findViewById(R.id.contactName);
            viewHolder.number = convertView.findViewById(R.id.contactNumber);
            convertView.setTag(viewHolder);
        }



        final Contact contact = getItem(position);

        String name = contact.getName();
        String number = contact.getNumber();

        viewHolder.name.setText(name);
        viewHolder.number.setText(number);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onContactClickListener != null) {
                    onContactClickListener.onContactClickListener(contact);
                }
            }
        });

        return convertView;
    }

    private class ContactViewHolder {
        public TextView name;
        public TextView number;
    }


}
