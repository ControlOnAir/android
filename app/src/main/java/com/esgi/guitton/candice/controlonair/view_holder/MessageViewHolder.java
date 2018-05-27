package com.esgi.guitton.candice.controlonair.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Message;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    public interface OnMessageClickListener {
        void onMessageClicked(Message message);
    }

    private TextView messageBody;
    private OnMessageClickListener listener;

    //itemView est la vue correspondante à 1 cellule
    public MessageViewHolder(View itemView, OnMessageClickListener listener) {
        super(itemView);
        this.listener = listener;

        messageBody = (TextView) itemView.findViewById(R.id.text_message_body);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(final Message message) {

        messageBody.setText(message.getBody());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMessageClicked(message);
            }
        });
    }
}