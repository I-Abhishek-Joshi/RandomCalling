package com.example.randomcalling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<User> {
    public ListViewAdapter(@NonNull Context context, @NonNull List<User> CompleteListUsers) {
        super(context, 0, CompleteListUsers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View myView = convertView;
        if(myView == null){
            myView = LayoutInflater.from(getContext()).inflate(R.layout.user_view, parent, false);
        }
        User curr = getItem(position);
        TextView name = myView.findViewById(R.id.name);
        TextView phone = myView.findViewById(R.id.Phone);
        name.setText(curr.getUserName());
        phone.setText(curr.getPhoneNo());
        phone.setVisibility(View.INVISIBLE);

        return myView;
    }
}
