package com.example.editdelete;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText nameInput, phoneInput;
    Button addContactButton;
    ArrayList<Contact> contactList;
    ArrayAdapter<String> adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        listView = findViewById(R.id.listView);
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        addContactButton = findViewById(R.id.addContactButton);
        dbHelper = new DatabaseHelper(this);
        contactList = new ArrayList<>();

        loadContacts();
        registerForContextMenu(listView);

        // Handle Add Contact Button Click
        addContactButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            if (!name.isEmpty() && !phone.isEmpty()) {
                addContact(name, phone);
            } else {
                Toast.makeText(this, "Please enter name and phone", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadContacts() {
        contactList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contacts", null);

        ArrayList<String> names = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            contactList.add(new Contact(id, name, phone));
            names.add(name);
        }
        cursor.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
    }

    private void addContact(String name, String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        db.insert("contacts", null, values);
//        db.close();
        loadContacts();  // Refresh List
        nameInput.setText("");  // Clear input fields
        phoneInput.setText("");
        Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);  // Ensure context_menu.xml exists!
    }




    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contact selectedContact = contactList.get(info.position);

        if (item.getItemId() == R.id.edit) {
            Intent intent = new Intent(this, EditContactActivity.class);
            intent.putExtra("CONTACT_ID", selectedContact.getId());
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.delete) {
            dbHelper.deleteContact(selectedContact.getId());
            loadContacts();  // Refresh List
            Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
