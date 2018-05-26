package com.esgi.guitton.candice.controlonair.services;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.esgi.guitton.candice.controlonair.Constants;
import com.esgi.guitton.candice.controlonair.Utils;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MessagesTask extends AsyncTask<Pair<Integer, Context>, Object, ArrayList<Message>> {

    public interface OnMessagesTaskCompleted {
        void onTaskComplete(ArrayList<Message> messages);
    }

    private OnMessagesTaskCompleted listener;

    public MessagesTask(OnMessagesTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Message> doInBackground(Pair<Integer, Context>... pairs) {
        Uri message = Uri.parse("content://sms/");
        Context context = pairs[0].second;
        Integer idConversation = pairs[0].first;
        ContentResolver cr = context.getContentResolver();


        Cursor c = cr.query(message, null, "thread_id=" + idConversation, null, "date ASC");
        int totalSMS = c.getCount();
        ArrayList<Message> messages = new ArrayList<>();
        System.out.println(messages + " kikou");
        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                int id_message = Integer.parseInt(c.getString(c.getColumnIndexOrThrow("_id")));
                Contact contact_message = ConversationsTask.getContact(context, c.getString(c.getColumnIndexOrThrow("address")));
                String body_message = c.getString(c.getColumnIndexOrThrow("body"));
                long timestamp_message = Long.parseLong(c.getString(c.getColumnIndexOrThrow("date")));

                Message message_list_item = new Message(id_message, contact_message, body_message, false, timestamp_message);

                Log.i("mimp", message.toString());


                messages.add(message_list_item);

                FirebaseDatabase database = Utils.getDatabase();
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
                String userNode = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "plop");

                DatabaseReference dataReference = database.getReference(Constants.USERS_NODE).child(userNode).child(Constants.MESSAGES_NODE);

                dataReference.child(String.valueOf(idConversation)).push().setValue(message_list_item);


                c.moveToNext();
            }
        }
        c.close();
        return messages;
    }


    @Override
    protected void onPostExecute(ArrayList<Message> messages) {
        listener.onTaskComplete(messages);
    }


}
