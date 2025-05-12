package com.example.todo_app_sp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import com.example.todo_app_sp.Todo;
/**
 * Custom adapter for Todo list
 */
public class TodoAdapter extends ArrayAdapter<Todo> {
    public TodoAdapter(Context context, List<Todo> todos) {
        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Todo todo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }

        // Lookup view for data population
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);
        TextView tvCreatedAt = convertView.findViewById(R.id.tvCreatedAt);

        // Populate the data into the template view using the data object
        tvTitle.setText(todo.getTitle());
        tvDescription.setText(todo.getDescription());
        tvCreatedAt.setText(todo.getCreatedAt());

        // Return the completed view to render on screen
        return convertView;
    }
}