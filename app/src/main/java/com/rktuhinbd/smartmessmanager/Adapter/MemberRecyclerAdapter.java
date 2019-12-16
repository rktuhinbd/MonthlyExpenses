package com.rktuhinbd.smartmessmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rktuhinbd.smartmessmanager.Model.Members;
import com.rktuhinbd.smartmessmanager.R;

import java.util.ArrayList;

public class MemberRecyclerAdapter extends RecyclerView.Adapter<MemberRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Members> members;

    public MemberRecyclerAdapter(Context context, ArrayList<Members> members) {
        this.context = context;
        this.members = members;
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
    public MemberRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_member, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberRecyclerAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText(members.get(position).getName());
        holder.textViewPhone.setText(members.get(position).getPhone());

        if (!members.get(position).getProfilePhotoUrl().isEmpty()) {
            //Load image url in the imageView using Picasso image loader library
        } else {
            //Load nothing
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPhone;
        ImageView imageViewProfilePhoto;

        ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textView_memberName);
            textViewPhone = itemView.findViewById(R.id.textView_memberPhone);
            imageViewProfilePhoto = itemView.findViewById(R.id.imageView_memberPhoto);

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
        return members.size();
    }
}
