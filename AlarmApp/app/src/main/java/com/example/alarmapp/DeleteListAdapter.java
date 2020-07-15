package com.example.alarmapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeleteListAdapter extends RecyclerView.Adapter<DeleteListAdapter.ViewHolder> {
    private ArrayList<Alarm> alarms;
    //ArrayList<Integer> selectedAlarmPositions; // I think these positions will have to be saved somewhere else, probably in the class that controls the activity
    //check if this array saves previous selections even without hitting delete button.

    public DeleteListAdapter(ArrayList<Alarm> alarms){
        this.alarms = alarms;
        //selectedAlarmPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public DeleteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.delete_item, parent, false);
        DeleteListAdapter.ViewHolder viewHolder = new DeleteListAdapter.ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final DeleteListAdapter.ViewHolder holder, final int position) {
        //the click listener for each item in the list.
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.delete_check_alarm){
                    holder.checkBox.toggle();
                    /*
                    if(holder.radioButton.isChecked()){
                        //clicking the android radio button makes it toggle, messing up the algorithm
                        //The only time this causes a problem is if the button is unchecked then gets checked
                        //clicking a radio button once it is checked doesn't automatically uncheck it.
                        holder.radioButton.setChecked(false);
                    }

                     */
                }
                CheckBox cb = holder.checkBox;
                //rb.toggle();  //check if it toggles if the user clicks on the button
                if(cb.isChecked()){

                    cb.setChecked(false);

                    Log.i("deleteAlarms", "removing position");
                    if(DeleteAlarmsActivity.selectedAlarmPositions.contains(position)){
                        DeleteAlarmsActivity.selectedAlarmPositions.remove(new Integer(position));
                    }
                }
                else {

                    cb.setChecked(true);

                    Log.i("deleteAlarms", "adding position");
                    if(!DeleteAlarmsActivity.selectedAlarmPositions.contains(position)){
                        DeleteAlarmsActivity.selectedAlarmPositions.add(new Integer(position));
                    }
                }
            }
        };
        final Alarm alarm = alarms.get(position);
        holder.message.setText(alarm.getMessage());
        holder.time.setText(alarm.getTime());
        holder.constraintLayout.setOnClickListener(listener);
        holder.checkBox.setOnClickListener(listener);




    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView time;
        public CheckBox checkBox;
        public ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.message = (TextView) itemView.findViewById(R.id.delete_message_tv);
            this.time = (TextView) itemView.findViewById(R.id.delete_time_tv);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.delete_check_alarm);
            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.delete_item_layout);

        }
    }
}


