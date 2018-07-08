package com.esgi.guitton.candice.controlonair;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Message;
import com.esgi.guitton.candice.controlonair.services.ConversationsTask;
import com.google.firebase.database.DatabaseReference;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            int smsId = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {

                    smsSender = smsMessage.getDisplayOriginatingAddress();
                    smsBody += smsMessage.getMessageBody();
                    smsId = smsMessage.getIndexOnIcc();

                }
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody += messages[i].getMessageBody();
                    }
                    smsSender = messages[0].getOriginatingAddress();
                    smsId =messages[0].getIndexOnIcc();
                }
            }

            Contact contact = ConversationsTask.getContact(context, smsSender);
            long timestamp = System.currentTimeMillis();
            Message message = new Message(smsId, contact, smsBody, false, timestamp);

            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
            String userNode = sharedPreferences.getString(Constants.GENERATED_PRIVATE_KEY, "plop");

            DatabaseReference dataReference = Utils.getDatabase().getReference(Constants.USERS_NODE).child(userNode).child(Constants.MESSAGES_NODE);

            String formattedAdress = smsSender;
            if (smsSender.contains("+")) {
                formattedAdress = smsSender.replace("+", "a");
            }

            dataReference.child(String.valueOf(formattedAdress)).child(String.valueOf(timestamp)).setValue(message);
        }
    }

}
