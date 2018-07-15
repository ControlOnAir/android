package com.esgi.guitton.candice.controlonair.services;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.esgi.guitton.candice.controlonair.Constants;
import com.esgi.guitton.candice.controlonair.Utils;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ConversationsTask extends AsyncTask<Context, Object, ArrayList<Conversation>> {

    public interface OnConversationsTaskCompleted {
        void onConversationsTaskComplete(ArrayList<Conversation> conversations);
    }

    private OnConversationsTaskCompleted listener;

    public ConversationsTask(OnConversationsTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Conversation> doInBackground(Context... contexts) {

        final ArrayList<Conversation> conversations = new ArrayList<>();

        String selection = "thread_id IS NOT NULL) GROUP BY (thread_id";
        Cursor cur = contexts[0].getContentResolver().query(
                Uri.parse("content://sms/"),
                new String[]{"thread_id", "address", "date"},
                selection,
                null, null
        );

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {

                String thread_id = cur.getString(cur.getColumnIndexOrThrow("thread_id"));
                String address = cur.getString(cur.getColumnIndexOrThrow("address"));
                String date = cur.getString(cur.getColumnIndexOrThrow("date"));


                Contact contact = getContact(contexts[0], address);
                Conversation conversation = new Conversation(Integer.parseInt(thread_id), contact, Long.parseLong(date), null);

                conversations.add(conversation);

                FirebaseDatabase database = Utils.getDatabase();
                SharedPreferences sharedPreferences = contexts[0].getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
                String userNode = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "plop");

                DatabaseReference dataReference = database.getReference("users").child(userNode).child("conversations");

                String formattedAdress = address;
                if (address.contains("+")) {
                    formattedAdress = address.replace("+", "a");
                }

                dataReference.child(formattedAdress).setValue(conversation);
            }
        }
        if (cur != null) {
            cur.close();
        }
        return conversations;
    }

    protected void onPostExecute(ArrayList<Conversation> conversations) {
        // your stuff
        listener.onConversationsTaskComplete(conversations);
    }

    public static Contact getContact(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.CONTACT_ID}, null, null, null);
        if (cursor == null) {
            return null;
        }

        Contact contact = new Contact(null, null, phoneNumber);
        if (cursor.moveToFirst()) {
            final String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            final String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.CONTACT_ID));
            contact.setName(contactName);
            contact.setId(contactId);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contact;
    }
}