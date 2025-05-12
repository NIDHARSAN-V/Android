package com.example.todo_app_sp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todo_app_sp.SessionManager;

public class TodoActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private Button btnSave;
    private AppDatabaseHelper appDatabaseHelper;  // Changed to AppDatabaseHelper
    private SessionManager sessionManager;
    private boolean isEdit;
    private int todoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        // Initialize database helper and session manager
        appDatabaseHelper = new AppDatabaseHelper(this);  // Use AppDatabaseHelper
        sessionManager = new SessionManager(this);

        // Check if user is logged in, if not redirect to login
        if (!sessionManager.isLoggedIn()) {
            logout();
            return;
        }

        // Initialize views
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        // Check if this is for edit or add
        isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            // Set title and description for edit
            todoId = getIntent().getIntExtra("todoId", -1);
            etTitle.setText(getIntent().getStringExtra("title"));
            etDescription.setText(getIntent().getStringExtra("description"));
        }

        // Save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();

                if (title.isEmpty()) {
                    Toast.makeText(TodoActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    int userId = sessionManager.getUserId();

                    if (isEdit) {
                        // Update existing todo
                        Todo todo = new Todo();
                        todo.setId(todoId);
                        todo.setTitle(title);
                        todo.setDescription(description);

                        int result = appDatabaseHelper.updateTodo(todo);  // Updated method call
                        if (result > 0) {
                            Toast.makeText(TodoActivity.this, "Todo updated", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(TodoActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Add new todo
                        long result = appDatabaseHelper.addTodo(title, description, userId);  // Updated method call
                        if (result != -1) {
                            Toast.makeText(TodoActivity.this, "Todo added", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(TodoActivity.this, "Add failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void logout() {
        sessionManager.logoutUser();
        Intent intent = new Intent(TodoActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        appDatabaseHelper.close();  // Close database helper
        super.onDestroy();
    }
}
