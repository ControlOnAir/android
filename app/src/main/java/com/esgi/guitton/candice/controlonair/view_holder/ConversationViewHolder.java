package com.esgi.guitton.candice.controlonair.view_holder;

import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Conversation;

public class ConversationViewHolder extends RecyclerView.ViewHolder {

    public interface OnConversationClickListener {
        void onConversationClicked(Conversation conversation);
    }

    private TextView contactName;
    private TextView contactNumber;
    private OnConversationClickListener listener;

    //itemView est la vue correspondante à 1 cellule
    public ConversationViewHolder(View itemView, OnConversationClickListener listener) {
        super(itemView);
        this.listener = listener;

        contactName = (TextView) itemView.findViewById(R.id.contactName);
        contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(final Conversation conversation) {

        contactName.setText(conversation.getContact().getName());
        contactNumber.setText(conversation.getContact().getNumber());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConversationClicked(conversation);
            }
        });
    }
}