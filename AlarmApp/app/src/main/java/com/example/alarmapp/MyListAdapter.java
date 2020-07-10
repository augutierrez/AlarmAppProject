package com.example.alarmapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
        private ArrayList<Alarm> listdata;

        public MyListAdapter(ArrayList<Alarm> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.list_item1, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Alarm myListData = listdata.get(position);
            holder.message.setText(listdata.get(position).getMessage());
            holder.time.setText(listdata.get(position).getTime());
            if(!holder.alarmSwitch.isChecked()){
                holder.alarmSwitch.toggle();
            }
            holder.alarmSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.alarmSwitch.isChecked()){
                        Toast.makeText(v.getContext(), "alarm should be on now", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(v.getContext(), "alarm should be off now", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
                }
            });
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView message;
            public TextView time;
            public Switch alarmSwitch;
            // need to add the switch too
            public ConstraintLayout constraintLayout;
            public ViewHolder(View itemView) {
                super(itemView);
                this.message = (TextView) itemView.findViewById(R.id.alarm_message_tv);
                this.time = (TextView) itemView.findViewById(R.id.alarm_time_tv);
                this.alarmSwitch = (Switch) itemView.findViewById(R.id.alarm_switch1);
                constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.constraint_layout);

            }
        }
    }
