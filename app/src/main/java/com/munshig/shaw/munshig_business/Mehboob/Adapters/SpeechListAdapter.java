package com.munshig.shaw.munshig_business.Mehboob.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.munshig.shaw.munshig_business.AppUtilities.Models.SpeechModel;
import com.munshig.shaw.munshig_business.R;
import com.munshig.shaw.munshig_business.Mehboob.Activities.SpeechCards;

import java.util.List;

public class SpeechListAdapter extends RecyclerView.Adapter<SpeechListAdapter.MyViewHolder>{

    Context context;
    List<SpeechModel> speechModelList;

    public SpeechListAdapter(List<SpeechModel> speechModelList, Context context) {
        this.speechModelList = speechModelList;
        Log.i("onCreate:SPEECHLIST ", String.valueOf(speechModelList.size()));
        this.context = context;
    }

    @NonNull
    @Override
    public SpeechListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_speech, viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final SpeechModel speechItem = speechModelList.get(i);
        Log.i( "onBindViewHolder: ", String.valueOf(i));
        myViewHolder.speech_item.setText(speechItem.getSpeechName());
        myViewHolder.speech_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SpeechCards.class);
                intent.putExtra("itemName", speechItem.getSpeechName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return speechModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView speech_item;
        LinearLayout speech_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            speech_item = itemView.findViewById(R.id.speech_item);
            speech_row = itemView.findViewById(R.id.speech_row);
        }
    }
}
