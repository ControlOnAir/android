package com.esgi.guitton.candice.controlonair.services;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Conversation;

import java.util.ArrayList;

public class ConversationsTask extends AsyncTask<Context, Object, ArrayList<Conversation>> {

    public interface OnConversationsTaskCompleted {
        void onTaskComplete(ArrayList<Conversation> conversations);
    }

    private OnConversationsTaskCompleted listener;

    public ConversationsTask(OnConversationsTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Conversation> doInBackground(Context... contexts) {

        final ArrayList<Conversation> conversations = new ArrayList<>();
        Cursor cur = contexts[0].getContentResolver().query(
                Uri.parse("content://sms/"),
                new String[]{"thread_id", "address", "date"},
                null,
                null, null
        );

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {

                String thread_id = cur.getString(cur.getColumnIndexOrThrow("thread_id"));
                String address = cur.getString(cur.getColumnIndexOrThrow("address"));
                String date = cur.getString(cur.getColumnIndexOrThrow("date"));

                Log.i("thomasecalle", "conversation : " + thread_id
                        + ",address : " + address
                        + ", date: " + date);

                Contact contact = getContact(contexts[0], address);
                conversations.add(new Conversation(Integer.parseInt(thread_id), contact, Long.parseLong(date), null));
            }
        }
        if (cur != null) {
            cur.close();
        }
        return conversations;
    }

    protected void onPostExecute(ArrayList<Conversation> conversations) {
        // your stuff
        listener.onTaskComplete(conversations);
    }

    public static Contact getContact(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }

        Contact contact = new Contact(null, phoneNumber);
        if (cursor.moveToFirst()) {
            final String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            contact.setName(contactName);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contact;
    }
}