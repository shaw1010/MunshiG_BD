package com.munshig.shaw.munshig_business.Adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.munshig.shaw.munshig_business.Activities.KiranaDetails;
import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.R;

import java.util.List;

public class KiranalistAdapter extends RecyclerView.Adapter<KiranalistAdapter.MyViewHolder> {

    List<KiranaModel> name;
    Context context;

    public KiranalistAdapter(List<KiranaModel> name, Context context) {
        this.name = name;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kirana, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final KiranaModel kirana = name.get(position);

        holder.title_text.setText(kirana.getName().toUpperCase());
        holder.vendor_text.setText(kirana.getVendor_name().toUpperCase());
        holder.list_rowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, KiranaDetails.class);
                intent.putExtra("name", kirana.getName());
                intent.putExtra("vendor_name", kirana.getVendor_name());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_text, vendor_text;
        LinearLayout list_rowlayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            title_text = itemView.findViewById(R.id.title_text);
            vendor_text = itemView.findViewById(R.id.vendor_text);
            list_rowlayout = itemView.findViewById(R.id.list_rowlayout);
        }
    }

    public void filterList(List<KiranaModel> filterList){
        name = filterList;
        notifyDataSetChanged();
    }
}
