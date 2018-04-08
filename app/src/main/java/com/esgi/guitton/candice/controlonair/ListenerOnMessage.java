package com.esgi.guitton.candice.controlonair;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by candiceguitton on 07/04/2018.
 */

public class ListenerOnMessage implements ChildEventListener {

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        // A new message has been added
        // onChildAdded() will be called for each node at the first time

        Message message = (Message) dataSnapshot.getValue();
        Log.d("", "received ${message} from firebase");
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.e("", "onChildChanged:" + dataSnapshot.getKey());

        // A message has changed
        Message message = (Message) dataSnapshot.getValue();
        Log.d("", "changed ${message} from firebase");
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Log.e("", "onChildRemoved:" + dataSnapshot.getKey());

        // A message has been removed
        Message message = (Message) dataSnapshot.getValue();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        Log.e("", "onChildMoved:" + dataSnapshot.getKey());

        // A message has changed position
        Message message = (Message) dataSnapshot.getValue();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e("", "postMessages:onCancelled", databaseError.toException());

    }

}
