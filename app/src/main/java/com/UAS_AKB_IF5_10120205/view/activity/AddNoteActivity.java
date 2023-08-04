package com.UAS_AKB_IF5_10120205.view.activity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.UAS_AKB_IF5_10120205.R;
import com.UAS_AKB_IF5_10120205.database.DatabaseHelper;
import com.UAS_AKB_IF5_10120205.model.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    ImageButton button;
    EditText editTitle;
    EditText editCategory;
    EditText editDesc;
    Button addButton;
    Button deleteButton;
    TextView titleAdd;

    private DatabaseReference notesReference;
    Note note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().hide();
        note = (Note) getIntent().getSerializableExtra("Note");
        button = findViewById(R.id.back);
        editTitle = findViewById(R.id.title);
        editCategory = findViewById(R.id.category);
        editDesc = findViewById(R.id.txt_desc);
        addButton = findViewById(R.id.buttonAdd);
        deleteButton = findViewById(R.id.buttonDelete);
        titleAdd = findViewById(R.id.txt_add);
        notesReference = new DatabaseHelper().getNotesReference();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        button.setOnClickListener(v -> {
            finish();
        });

        if (note == null) {
            deleteButton.setVisibility(View.GONE);

            addButton.setOnClickListener(v -> {
                if (editTitle.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Judul Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editCategory.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Kategori Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editDesc.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Isi Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
                Date d = new Date();
                CharSequence date = DateFormat.format("EEEE, d MMM yyyy HH:mm", d.getTime());
                String newNoteId = notesReference.push().getKey();
                Note n = new Note(
                        newNoteId,
                        editTitle.getText().toString(),
                        editCategory.getText().toString(),
                        editDesc.getText().toString(),
                        date + ""
                );


                if (newNoteId != null) {
                    notesReference.child(userId).child(newNoteId).setValue(n);
                    finish();
                    Toast.makeText(this, "Catatan berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to add note: Invalid note ID", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            editTitle.setText(note.getTitle());
            editCategory.setText(note.getCategory());
            editDesc.setText(note.getDesc());

            deleteButton.setVisibility(View.VISIBLE);
            titleAdd.setText("Edit Catatan");

            addButton.setOnClickListener(v -> {
                if (editTitle.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Judul Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editCategory.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Kategori Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editDesc.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Isi Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }

                Date d = new Date();
                CharSequence date = DateFormat.format("EEEE, d MMMM yyyy HH:mm", d.getTime());

                note.setTitle(editTitle.getText().toString());
                note.setCategory(editCategory.getText().toString());
                note.setDesc(editDesc.getText().toString());
                note.setDate((String) date);
                String noteId = note.getId();
                notesReference.child(userId).child(noteId).setValue(note);
                Toast.makeText(this, "Catatan berhasil diedit", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        deleteButton.setOnClickListener(v -> {
            if (note != null) {
                String noteId = note.getId();
                if (noteId != null) {
                    notesReference.child(userId).child(noteId).removeValue()
                            .addOnSuccessListener(aVoid -> {
                                finish();
                                Toast.makeText(this, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Gagal menghapus catatan", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(this, "Gagal menghapus catatan: ID catatan tidak valid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Catatan tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
