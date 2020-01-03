package com.rktuhinbd.smartmessmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rktuhinbd.smartmessmanager.Model.Rents;
import com.rktuhinbd.smartmessmanager.R;

import java.util.ArrayList;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Rents> rents;

    public ExpenseRecyclerAdapter(Context context, ArrayList<Rents> rents) {
        this.context = context;
        this.rents = rents;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ExpenseRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_rent, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseRecyclerAdapter.ViewHolder holder, int position) {
        holder.textViewRentCategory.setText(rents.get(position).getRentCategory());
        holder.textViewMonth.setText(rents.get(position).getRentMonth());
        holder.textViewRentAmount.setText(rents.get(position).getRentAmount() + "");

        switch (rents.get(position).getRentCategory()) {
            case "House Rent":
                holder.imageViewLogo.setImageResource(R.drawable.ic_house_rent);
                break;
            case "Garage Rent":
                holder.imageViewLogo.setImageResource(R.drawable.ic_garage);
                break;
            case "Electricity Bill":
                holder.imageViewLogo.setImageResource(R.drawable.ic_electricity);
                break;
            case "Gas Bill":
                holder.imageViewLogo.setImageResource(R.drawable.ic_gas);
                break;
            case "Water Bill":
                holder.imageViewLogo.setImageResource(R.drawable.ic_faucet);
                break;
            case "Garbage Cleaning Bill":
                holder.imageViewLogo.setImageResource(R.drawable.ic_garbage);
                break;
            case "Maid Salary":
                holder.imageViewLogo.setImageResource(R.drawable.ic_maid);
                break;
            case "Service Charge":
                holder.imageViewLogo.setImageResource(R.drawable.ic_service);
                break;
            case "Miscellaneous":
                holder.imageViewLogo.setImageResource(R.drawable.ic_miscellaneous);
                break;
            case "Other":
                holder.imageViewLogo.setImageResource(R.drawable.ic_ufo);
                break;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLogo;
        TextView textViewRentCategory, textViewRentAmount, textViewMonth;

        ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            imageViewLogo = itemView.findViewById(R.id.imageView_rentLogo);
            textViewRentCategory = itemView.findViewById(R.id.textView_rentCategory);
            textViewMonth = itemView.findViewById(R.id.textView_rentMonth);
            textViewRentAmount = itemView.findViewById(R.id.textView_rentAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = ViewHolder.this.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rents.size();
    }
}
