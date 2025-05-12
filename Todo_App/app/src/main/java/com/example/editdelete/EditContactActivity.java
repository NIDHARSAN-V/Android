package com.example.editdelete;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;



public class EditContactActivity extends AppCompatActivity {
    EditText nameEdit, phoneEdit;
    Button saveButton;
    DatabaseHelper dbHelper;
    int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        nameEdit = findViewById(R.id.nameEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        saveButton = findViewById(R.id.saveButton);
        dbHelper = new DatabaseHelper(this);

        contactId = getIntent().getIntExtra("CONTACT_ID", -1);
        loadContactData(contactId);

        saveButton.setOnClickListener(v -> {
            updateContact(contactId, nameEdit.getText().toString(), phoneEdit.getText().toString());
            finish();
        });
    }

    private void loadContactData(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contacts WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            nameEdit.setText(cursor.getString(1));
            phoneEdit.setText(cursor.getString(2));
        }
        cursor.close();
    }

    private void updateContact(int id, String name, String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        db.update("contacts", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}

