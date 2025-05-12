package com.example.todo_app_sp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvTodos;
    private FloatingActionButton fabAdd;
    private TodoAdapter todoAdapter;
    private AppDatabaseHelper appDatabaseHelper; // Replaced TodoDbHelper with AppDatabaseHelper
    private SessionManager sessionManager;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper and session manager
        appDatabaseHelper = new AppDatabaseHelper(this); // Using AppDatabaseHelper
        sessionManager = new SessionManager(this);

        // Check if user is logged in, if not redirect to login
        if (!sessionManager.isLoggedIn()) {
            logout();
            return;
        }

        // Get user ID from session
        userId = sessionManager.getUserId();

        // Initialize views
        lvTodos = findViewById(R.id.lvTodos);
        fabAdd = findViewById(R.id.fabAdd);

        // Load todos
        loadTodos();

        // Add todo button click event
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TodoActivity.class);
                intent.putExtra("isEdit", false);
                startActivityForResult(intent, 1);
            }
        });

        // Todo item click event (for edit)
        lvTodos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = (Todo) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, TodoActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("todoId", todo.getId());
                intent.putExtra("title", todo.getTitle());
                intent.putExtra("description", todo.getDescription());
                startActivityForResult(intent, 2);
            }
        });

        // Check if session is about to expire
        if (sessionManager.isSessionAboutToExpire()) {
            Toast.makeText(this, "Session will expire soon. Please save your work.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if user is still logged in
        if (!sessionManager.isLoggedIn()) {
            logout();
        }
    }

    /**
     * Load todos from database and populate list view
     */
    private void loadTodos() {
        List<Todo> todoList = appDatabaseHelper.getAllTodos(userId); // Using AppDatabaseHelper
        todoAdapter = new TodoAdapter(this, todoList);
        lvTodos.setAdapter(todoAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Refresh todo list after add/edit
            loadTodos();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Logout user and redirect to login screen
     */
    private void logout() {
        sessionManager.logoutUser();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        appDatabaseHelper.close(); // Close the database helper
        super.onDestroy();
    }
}
