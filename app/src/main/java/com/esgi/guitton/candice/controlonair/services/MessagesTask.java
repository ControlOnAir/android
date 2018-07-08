package com.esgi.guitton.candice.controlonair.services;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.esgi.guitton.candice.controlonair.Constants;
import com.esgi.guitton.candice.controlonair.Utils;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessagesTask extends AsyncTask<MessagesTask.MessagesTaskParams, Object, ArrayList<Message>> {

    public interface OnMessagesTaskCompleted {
        void onMessagesTaskComplete(ArrayList<Message> messages);
    }

    public static class MessagesTaskParams {
        private final Context context;
        private final String contactNumber;
        private final int converdationId;

        public MessagesTaskParams(Context context, String contactNumber, int converdationId) {
            this.context = context;
            this.contactNumber = contactNumber;
            this.converdationId = converdationId;
        }
    }

    private OnMessagesTaskCompleted listener;

    public MessagesTask(OnMessagesTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Message> doInBackground(MessagesTaskParams... params) {
        Uri messageUri = Uri.parse("content://sms/");
        Context context = params[0].context;
        Integer idConversation = params[0].converdationId;
        String contactNumber = params[0].contactNumber;

        ContentResolver cr = context.getContentResolver();


        Cursor c = cr.query(messageUri, null, "thread_id=" + idConversation, null, "date DESC");
        int totalSMS = c.getCount();
        ArrayList<Message> messages = new ArrayList<>();
        System.out.println(messages + " kikou");
        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                int id_message = Integer.parseInt(c.getString(c.getColumnIndexOrThrow("_id")));
                Contact contact_message = ConversationsTask.getContact(context, c.getString(c.getColumnIndexOrThrow("address")));
                String body_message = c.getString(c.getColumnIndexOrThrow("body"));
                long timestamp_message = Long.parseLong(c.getString(c.getColumnIndexOrThrow("date")));

                String protocol = c.getString(c.getColumnIndex("protocol"));

                boolean isSentByUser = protocol == null;

                Message message = new Message(id_message, contact_message, body_message, isSentByUser, timestamp_message);

                Log.i("mimp", messageUri.toString());


                messages.add(message);

                FirebaseDatabase database = Utils.getDatabase();
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
                String userNode = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "plop");

                DatabaseReference dataReference = database.getReference(Constants.USERS_NODE).child(userNode).child(Constants.MESSAGES_NODE);

                String formattedAdress = contactNumber;
                if (contactNumber.contains("+")) {
                    formattedAdress = contactNumber.replace("+", "a");
                }

                dataReference.child(String.valueOf(formattedAdress)).child(String.valueOf(message.getTimestamp())).setValue(message);


                c.moveToNext();
            }
        }
        c.close();
        return messages;
    }




    @Override
    protected void onPostExecute(ArrayList<Message> messages) {
        listener.onMessagesTaskComplete(messages);
    }


}
