package com.esgi.guitton.candice.controlonair;

import android.provider.Settings;

public class Constants {
    public final static String GENERATED_PRIVATE_KEY = "generatedPrivateKey";
    public final static String PREFERENCES = "preferences";
    public final static String USERS_NODE = "users";
    public final static String CONVERSATIONS_NODE = "conversations";
    public final static String MESSAGES_NODE = "messages";
    public final static String CONTACTS_NODE = "contacts";
    public final static String CONVERSATIONS_FIREBASE_URL_REFERENCE = "https://controlonairapp.firebaseio.com/users/"+Settings.Secure.ANDROID_ID+"/conversations";
    public final static String CONTACTS_FIREBASE_URL_REFERENCE = "https://controlonairapp.firebaseio.com/users/"+ Settings.Secure.ANDROID_ID +"/contacts";
    public final static String MESSAGES_FIREBASE_URL_REFERENCE = "https://controlonairapp.firebaseio.com/users/"+Settings.Secure.ANDROID_ID+"/messages";
}
