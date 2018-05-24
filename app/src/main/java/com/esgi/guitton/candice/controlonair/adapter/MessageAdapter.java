package com.esgi.guitton.candice.controlonair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Message;

import java.util.List;


public class MessageAdapter extends ArrayAdapter<Message> {

        public MessageAdapter(Context context, List<Message> messages) {
            super(context, 0, messages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Message message = getItem(position);


            if (convertView == null) {
                int layout = message.isSent() ? R.layout.sent_content_message : R.layout.received_content_message;
                convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
            }

            MessageViewHolder viewHolder = (MessageViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new MessageViewHolder();
              //  viewHolder.body = (TextView) convertView.findViewById(R.id.body);
                convertView.setTag(viewHolder);
            }

            viewHolder.body.setText(message.getBody());


            return convertView;
        }

        private class MessageViewHolder {
            public TextView body;
        }
}
