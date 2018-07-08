package com.esgi.guitton.candice.controlonair.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Contact;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public interface OnContactClickListener {
        void onContactClicked(Contact contact);
    }

    private TextView contactName;
    private TextView contactNumber;
    private OnContactClickListener listener;

    //itemView est la vue correspondante à 1 cellule
    public ContactViewHolder(View itemView, OnContactClickListener listener) {
        super(itemView);
        this.listener = listener;

        contactName = (TextView) itemView.findViewById(R.id.contactName);
        contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(final Contact contact) {

        contactName.setText(contact.getName());
        contactNumber.setText(contact.getNumber());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onContactClicked(contact);
            }
        });
    }
}