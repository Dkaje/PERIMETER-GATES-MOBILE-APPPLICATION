package com.example.perimetergates.Intel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perimetergates.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BoomDemo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FireWork> list;
    private Context ctx;

    public BoomDemo(Context context, List<FireWork> list) {
        this.list = list;
        ctx = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.boomer, viewGroup, false);
        viewHolder = new OriginalViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hotel, int i) {
        if (hotel instanceof OriginalViewHolder) {
            final OriginalViewHolder viewHolder = (OriginalViewHolder) hotel;
            final FireWork get = list.get(i);
            /*if (get.getReply().equals("Pending")) {
                viewHolder.textInputLayout.setVisibility(View.GONE);
            } else {
                viewHolder.textInputLayout.setVisibility(View.VISIBLE);
            }*/
            viewHolder.Reply.setText(get.getReply());
            viewHolder.DateRep.setText(get.getReply_date());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView Reply, DateRep;

        public OriginalViewHolder(@NonNull View view) {
            super(view);
            Reply = view.findViewById(R.id.textRepliedMess);
            DateRep = view.findViewById(R.id.textRplyDate);

        }
    }
}
