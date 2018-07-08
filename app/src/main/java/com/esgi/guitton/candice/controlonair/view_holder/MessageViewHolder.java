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

    private TextView sentMessageText;
    private TextView sentMessageDate;

    private TextView receivedMessageText;
    private TextView receivedMessageDate;


    private OnMessageClickListener listener;

    //itemView est la vue correspondante à 1 cellule
    public MessageViewHolder(View itemView, OnMessageClickListener listener) {
        super(itemView);
        this.listener = listener;

        sentMessageText = itemView.findViewById(R.id.sentMessageText);
        sentMessageDate = itemView.findViewById(R.id.sentMessageDate);

        receivedMessageText = itemView.findViewById(R.id.receivedMessageText);
        receivedMessageDate = itemView.findViewById(R.id.receivedMessageDate);

    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(final Message message) {

        if (message.isSent()) {
            sentMessageText.setVisibility(View.VISIBLE);
            sentMessageDate.setVisibility(View.VISIBLE);

            sentMessageText.setText(message.getBody());

            receivedMessageDate.setVisibility(View.GONE);
            receivedMessageText.setVisibility(View.GONE);
        } else {
            receivedMessageText.setVisibility(View.VISIBLE);
            receivedMessageDate.setVisibility(View.VISIBLE);

            receivedMessageText.setText(message.getBody());

            sentMessageText.setVisibility(View.GONE);
            sentMessageDate.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMessageClicked(message);
            }
        });
    }
}