package com.example.rgong2_trialbook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExperimentAdapter extends RecyclerView.Adapter<ExperimentAdapter.ViewHolder> {
    /*
        From:Android documentation
        Date:Nov 11, 2020
        Licences: Apache 2.0 license
        Link: https://developer.android.com/guide/topics/ui/layout/recyclerview
        Usage: Basic recyclerview setup
       */
    public static final String EXPERIMENT = "com.example.rgong2_trialbook.ExperimentAdapter.EXPERIMENT";
    public static final String INDEX = "com.example.rgong2_trialbook.ExperimentAdapter.INDEX";


    //constants to identify if the item is experiment or the add button
    private static class ItemType {
        static final int EXPERIEMENT = 0;
        static final int ADDBUTTON = 1;
    }

    private final ExperimentManager expManager;

    //custom view holder for experiments
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout container;
        private final Context context;

        //context needs to be accessed later when inserting views into the linear layout
        public ViewHolder(Context c, View v) {
            super(v);

            LinearLayout temp = (LinearLayout) v.findViewById(R.id.container);
            if (temp == null) {
                //if R.id.container cannot be found, then the layout been inflated must then the add button
                temp = (LinearLayout) v.findViewById(R.id.addButtonContainer);
            }
            container = temp;
            context = c;
        }

        public LinearLayout getLayout() {
            return container;
        }

        public Context getContext() {
            return context;
        }
    }

    //custom constructor
    public ExperimentAdapter(ExperimentManager expManager) {
        this.expManager = expManager;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;

        //inflate different layouts depending on if the item add button or experiment
        switch (viewType) {
            case ItemType.EXPERIEMENT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_container, parent, false);
                break;
            case ItemType.ADDBUTTON:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_exp_button, parent, false);
                //add onclick to the add button so

                break;
        }
        //the same view holder created above in this class
        return new ViewHolder(parent.getContext(), v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ItemType.EXPERIEMENT:
                //remove all views before adding the new or updated view
                holder.getLayout().removeAllViews();
                holder.getLayout().addView(ExpListViewMaker.makeView(holder.getContext(), expManager.getExperiment(position)));
                holder.getLayout().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /*+
                            From:Android documentation
                            Date: Dec 10, 2020
                            Licences: Apache 2.0 license
                            Link: https://developer.android.com/training/basics/firstapp/starting-activity
                            Usage: start activity to edit an activity
                           */

                        //TODO if more than 1 type of experiment exist, this port needs to be modified
                        // so that it can point to different menus depending on experiment type

                        Intent intent = new Intent(view.getContext(), EditExpActivity.class);
                        intent.putExtra(EXPERIMENT, expManager.getExperiment(position));
                        intent.putExtra(INDEX, position);
                        view.getContext().startActivity(intent);
                    }
                });
                break;
            case ItemType.ADDBUTTON:
                 holder.getLayout().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*
                    From:Mostafa
                    Date:April 15, 2015
                    Licences: CC BY-SA 4.0
                    Link: https://stackoverflow.com/a/29452807/12471420
                    Usage: Recycler view insertion
                    */
                        //insert empty experiment at the end of list
                        addExperiment(new BinaryExperiment(), expManager.getSize());
                        Intent intent = new Intent(view.getContext(), EditExpActivity.class);
                        intent.putExtra(EXPERIMENT, expManager.getExperiment(expManager.getSize() - 1));
                        intent.putExtra(INDEX, expManager.getSize() - 1);
                        view.getContext().startActivity(intent);
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        //+ 1 because of the add button at the end of list
        return expManager.getSize() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        //identify if the element is a experiment or the add button
        //add button should always be the last element
        if (position == expManager.getSize()) {
            return ItemType.ADDBUTTON;
        } else {
            return ItemType.EXPERIEMENT;
        }
    }

    //returns true if successful
    public boolean addExperiment(Experiment exp, int position) {
        if (position > expManager.getSize() || position < 0) {
            // illegal insert position
            return false;
        }
        expManager.addExperiment(position, exp);
        //notifyItemInserted(expManager.getSize() - 1);
        notifyItemChanged(position);
        Log.d("Stuff", "Size " + getItemCount() + "List length: " + expManager.getSize());
        return true;
    }
}
