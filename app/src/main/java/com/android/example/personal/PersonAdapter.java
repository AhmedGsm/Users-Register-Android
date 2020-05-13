package com.android.example.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
     private final Context mContext;
     private  List<Person> mPersons = new ArrayList<>();

     PersonAdapter(Context context) {
         mContext = context;
     }
     // View Holder class
      static class PersonViewHolder extends  RecyclerView.ViewHolder {
         PersonViewHolder(ConstraintLayout itemView) {
             super(itemView);
         }
    }
     @NonNull
     @Override
     public PersonAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         ConstraintLayout layout =
                 (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
         return new PersonViewHolder(layout);
     }

     @Override
     public void onBindViewHolder(@NonNull PersonAdapter.PersonViewHolder holder, int position) {
         Person person = mPersons.get(position);
         TextView nameTv = holder.itemView.findViewById(R.id.personIdTv);
         nameTv.setText(String.valueOf(person.getName()));

         TextView emailTv = holder.itemView.findViewById(R.id.personEmailTv);
         emailTv.setText( person.getEmail());
     }

     @Override
     public int getItemCount() {
         if(mPersons == null ||mPersons.size() == 0) return 0;
         return mPersons.size();
     }

    public void swapPersons(List<Person> persons) {
         mPersons = persons;
         notifyDataSetChanged();
    }
 }
