package com.esgi.guitton.candice.controlonair;

import com.google.firebase.database.FirebaseDatabase;

public class Utils {
    private static FirebaseDatabase database;

    /*
        in Order to assure that setPersistence is call
     */
    public static FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }

        return database;

    }
}
