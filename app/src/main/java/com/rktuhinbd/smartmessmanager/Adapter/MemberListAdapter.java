package com.rktuhinbd.smartmessmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rktuhinbd.smartmessmanager.Model.MemberList;
import com.rktuhinbd.smartmessmanager.R;

import java.util.ArrayList;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MemberList> memberLists;

    public MemberListAdapter(Context context, ArrayList<MemberList> memberLists) {
        this.context = context;
        this.memberLists = memberLists;
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
    public MemberListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_items, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberListAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText(memberLists.get(position).getName());
        holder.textViewPhone.setText(memberLists.get(position).getPhone());

        if (!memberLists.get(position).getProfilePhotoUrl().isEmpty()) {
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
        return memberLists.size();
    }
}
