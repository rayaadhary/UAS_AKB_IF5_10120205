package com.UAS_AKB_IF5_10120205.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    private static final String NOTES_PATH = "notes";
    private DatabaseReference databaseReference;

    public DatabaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference(NOTES_PATH);
    }

    public DatabaseReference getNotesReference() {
        return databaseReference;
    }

    // No need for create, update, and delete methods. Firebase handles these operations automatically.
}
