package com.example.todo_app_sp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TodoApp.db";
    private static final int DATABASE_VERSION = 2;

    // User table and columns
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Todo table and columns
    public static final String TABLE_TODOS = "todos";
    public static final String COLUMN_TODO_ID = "todo_id";
    public static final String COLUMN_TODO_TITLE = "title";
    public static final String COLUMN_TODO_DESCRIPTION = "description";
    public static final String COLUMN_TODO_CREATED_AT = "created_at";

    // SQL to create users table
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    // SQL to create todos table
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODOS + "("
            + COLUMN_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TODO_TITLE + " TEXT,"
            + COLUMN_TODO_DESCRIPTION + " TEXT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_TODO_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE"
            + ")";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TODO_TABLE);
        Log.d("DB_CREATE", "Tables created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // When upgrading from version 1 to version 2 or higher
        if (oldVersion < 2) {
            // You can add new columns or tables here, without dropping the existing ones
            Log.d("DB_UPGRADE", "Upgrading database from version " + oldVersion + " to " + newVersion);

            // Example of adding a new column if needed (uncomment if you need it):
            // db.execSQL("ALTER TABLE todos ADD COLUMN new_column_name INTEGER");

            // If you're adding a new table, just create it like onCreate (but don't drop old ones):
            // db.execSQL(CREATE_TODO_TABLE);
        }
    }

    // User-related methods
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        }
        cursor.close();
        return userId;
    }

    // Todo-related methods
    public long addTodo(String title, String description, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_TITLE, title);
        values.put(COLUMN_TODO_DESCRIPTION, description);
        values.put(COLUMN_USER_ID, userId);

        return db.insert(TABLE_TODOS, null, values);
    }

    public List<Todo> getAllTodos(int userId) {
        List<Todo> todoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_TODO_ID,
                COLUMN_TODO_TITLE,
                COLUMN_TODO_DESCRIPTION,
                COLUMN_TODO_CREATED_AT
        };

        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_TODOS, columns, selection, selectionArgs,
                null, null, COLUMN_TODO_CREATED_AT + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TODO_ID)));
                todo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TODO_TITLE)));
                todo.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TODO_DESCRIPTION)));
                todo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TODO_CREATED_AT)));
                todoList.add(todo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return todoList;
    }

    public int updateTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO_TITLE, todo.getTitle());
        values.put(COLUMN_TODO_DESCRIPTION, todo.getDescription());

        return db.update(TABLE_TODOS, values, COLUMN_TODO_ID + " = ?",
                new String[]{String.valueOf(todo.getId())});
    }

    public void deleteTodo(int todoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODOS, COLUMN_TODO_ID + " = ?",
                new String[]{String.valueOf(todoId)});
    }
}
