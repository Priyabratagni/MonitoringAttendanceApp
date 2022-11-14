package com.example.attendance;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    ArrayList<StudentItem> studentItems;
    Context context;
    
    private onItemClickListener onItemClickListener;


    public interface onItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    
    public StudentAdapter(Context context, ArrayList<StudentItem> studentItems){
        this.studentItems = studentItems;
        this.context = context;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView roll;
        TextView name;
        TextView status;
        CardView cardView;
        
        public StudentViewHolder(@NonNull View itemView, onItemClickListener onItemClickListener ){
            super(itemView);
            roll = itemView.findViewById(R.id.roll);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public  StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return  new StudentViewHolder(itemView, onItemClickListener);
    }




    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position){
        holder.roll.setText(studentItems.get(position).getRoll()+"");
        holder.name.setText(studentItems.get(position).getName());
        holder.status.setText(studentItems.get(position).getStatus());
        holder.cardView.setCardBackgroundColor(getColor(position));
    }

    private int getColor(int position) {
        String status = studentItems.get(position).getStatus();
        if(status.equals("P"))
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
        else if (status.equals("A"))
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context,R.color.absent)));

        return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context,R.color.normal)));

    }

    @Override
    public int getItemCount(){ return studentItems.size(); }
    
    
}
