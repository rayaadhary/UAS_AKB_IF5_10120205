package com.UAS_AKB_IF5_10120205.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.UAS_AKB_IF5_10120205.R;
import com.UAS_AKB_IF5_10120205.adapter.NoteAdapter;
import com.UAS_AKB_IF5_10120205.database.DatabaseHelper;
import com.UAS_AKB_IF5_10120205.model.Note;
import com.UAS_AKB_IF5_10120205.view.activity.AddNoteActivity;
import com.UAS_AKB_IF5_10120205.view.activity.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {

    private MainActivity mainActivity;
    private List<Note> notes;
    private DatabaseReference notesReference;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_note, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().hide();

        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.mynote);
        addButton = view.findViewById(R.id.button_add);
        addButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddNoteActivity.class));
        });

        // Initialize Firebase Realtime Database reference
        notesReference = new DatabaseHelper().getNotesReference();
        notesReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Note note = dataSnapshot.getValue(Note.class);
                if (note != null) {
                    notes.add(note);
                    noteAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Note updatedNote = dataSnapshot.getValue(Note.class);
                if (updatedNote != null) {
                    String noteId = dataSnapshot.getKey();
                    for (int i = 0; i < notes.size(); i++) {
                        if (notes.get(i).equals(noteId)) {
                            notes.set(i, updatedNote);
                            noteAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String removedNoteId = dataSnapshot.getKey();
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).equals(removedNoteId)) {
                        notes.remove(i);
                        noteAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Not needed for this implementation
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Not needed for this implementation
            }
        });

        notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(notes);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }
}
