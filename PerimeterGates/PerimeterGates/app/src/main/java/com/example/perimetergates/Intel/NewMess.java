package com.example.perimetergates.Intel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perimetergates.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class NewMess extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FeedMode> list;
    private Context ctx;

    public NewMess(Context context, List<FeedMode> list) {
        this.list = list;
        ctx = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rada, viewGroup, false);
        viewHolder = new OriginalViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hotel, int i) {
        if (hotel instanceof OriginalViewHolder) {
            final OriginalViewHolder viewHolder = (OriginalViewHolder) hotel;
            final FeedMode get = list.get(i);
            /*if (get.getReply().equals("Pending")) {
                viewHolder.textInputLayout.setVisibility(View.GONE);
            }else {
                viewHolder.textInputLayout.setVisibility(View.VISIBLE);
            }*/
            viewHolder.Message.setText(get.getMessage());
            viewHolder.DateMes.setText(get.getReg_date());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView Message, DateMes; /*Reply, DateRep;
        public TextInputLayout textInputLayout;*/

        public OriginalViewHolder(@NonNull View view) {
            super(view);
            Message = view.findViewById(R.id.tetMessage);
            DateMes = view.findViewById(R.id.textDate);

        }
    }


}
