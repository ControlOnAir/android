package com.esgi.guitton.candice.controlonair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Conversation;

import java.util.List;


public class ConversationAdapter extends ArrayAdapter<Conversation> {

    public interface OnConversationClickListener {
        void onConversationClicked(Conversation conversation);
    }

    private OnConversationClickListener onConversationClickListener;

    public ConversationAdapter(Context context, List<Conversation> conversations, OnConversationClickListener onConversationClickListener) {
        super(context, R.layout.conversation_list_item, conversations);
        this.onConversationClickListener = onConversationClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Conversation conversation = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.conversation_list_item, parent, false);
        }

        ConversationViewHolder viewHolder = (ConversationViewHolder) convertView.getTag();

        if (viewHolder == null) {
            viewHolder = new ConversationViewHolder();
            viewHolder.contactName = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.contactNumber = (TextView) convertView.findViewById(R.id.contactNumber);
            convertView.setTag(viewHolder);
        }

        if (conversation!= null && conversation.getContact() != null)
        {
            viewHolder.contactName.setText(conversation.getContact().getName());
            viewHolder.contactNumber.setText(conversation.getContact().getNumber());
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConversationClickListener != null) {
                    onConversationClickListener.onConversationClicked(conversation);
                }
            }
        });


        return convertView;
    }

    private class ConversationViewHolder {
        public TextView contactName;
        public TextView contactNumber;
    }
}
