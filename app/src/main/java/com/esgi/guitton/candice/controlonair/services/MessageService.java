package com.esgi.guitton.candice.controlonair.services;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.esgi.guitton.candice.controlonair.ConversationActivity;
import com.esgi.guitton.candice.controlonair.MessageActivity;
import com.esgi.guitton.candice.controlonair.models.Conversation;

import java.util.ArrayList;

public class MessageService {


    public static void getAllSms(Context context) {
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(message, null, "thread_id=" + 143, null, null);
        int totalSMS = c.getCount();
        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                Log.d("SMScandice",
                        "Contact number : "
                                + c.getString(c.getColumnIndexOrThrow("address"))
                                + "\n"
                                + "msg : "
                                + c.getString(c.getColumnIndexOrThrow("body"))
                                + "\n"
                                + "ID : "
                                + c.getString(c.getColumnIndexOrThrow("_id"))
                                + "\n"
                                + "thread_id : "
                                + c.getString(c.getColumnIndexOrThrow("thread_id"))
                                + "\n"
                                + "Person : "
                                + getContactName(
                                context,
                                c.getString(c
                                        .getColumnIndexOrThrow("address"))));

                c.moveToNext();
            }
        }
        c.close();

    }

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

    public static ArrayList<Conversation> getConversations(Context context) {

        context.getContentResolver().query(
                Uri.parse("content://sms/"),
                new String[]{"thread_id", "adress", "date"},
                null,
                null,null
                );
        return null;
    }
}
