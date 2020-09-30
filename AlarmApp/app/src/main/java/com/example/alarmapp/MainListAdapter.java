package com.example.alarmapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
        private ArrayList<Alarm> listData;

        public MainListAdapter(ArrayList<Alarm> listData) {
            this.listData = listData;
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
            final Alarm myListData = listData.get(position);
            holder.message.setText(listData.get(position).getMessage());
            holder.time.setText(listData.get(position).getTime());
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
            return listData.size();
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
