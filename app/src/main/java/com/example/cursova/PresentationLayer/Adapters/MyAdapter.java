package com.example.cursova.PresentationLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursova.BusinessLogicLayer.BusinessLogic;
import com.example.cursova.R;

import java.util.ArrayList;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private final Context context;
    private final ArrayList<String> user_emails;

    public MyAdapter(ArrayList<String> user_emails, Context context)
    {
        this.user_emails = user_emails;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_recycler_view, parent, false);
        return new ViewHolder(view, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userEmail_txt.setText(String.valueOf(user_emails.get(position)));
    }

    @Override
    public int getItemCount() {
        return user_emails.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView userEmail_txt;
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            userEmail_txt = itemView.findViewById(R.id.user_email);
            userEmail_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new BusinessLogic(context).userEmailOnClick(userEmail_txt.getText().toString());
                }
            });
        }
    }
}
