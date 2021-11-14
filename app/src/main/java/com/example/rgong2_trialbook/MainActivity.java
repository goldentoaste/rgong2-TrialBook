package com.example.rgong2_trialbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private ExperimentAdapter adapter;
    private ExperimentManager manager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_list);

        /*
        From: Suragch
        Date: Dec 18, 2019
        Licences: CC BY-SA 4.0
        Link: https://stackoverflow.com/a/40584425/12471420
        Usage: Recycler view initialization
        */
        manager = new ExperimentManager();
        adapter = new ExperimentAdapter(manager);

        recyclerView = findViewById(R.id.experimentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        From: Leo Droidcoder
        Date: Dec 17, 2016
        Licences: CC BY-SA 4.0
        Link:   https://stackoverflow.com/a/41201865/12471420
        Usage: Adding simple divider to recyclerview
        */
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(adapter);

    }
    /*
           From: Gaurav Das
           Date: Aug 24, 11
           Licences: CC BY-SA 4.0
           Link: https://stackoverflow.com/a/7175308/12471420
           Usage: Set intent
           */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Experiment exp = (Experiment)intent.getSerializableExtra(EditExpActivity.EXPERIMENT);
        String action = intent.getStringExtra(EditExpActivity.ACTION);
        int index = intent.getIntExtra(EditExpActivity.INDEX, 0);
        //only binary experiment is implemented for now
        if(exp != null && exp.isBinary()){
            if(action != null && action.equals(EditExpActivity.SAVE)){
                manager.updateExperiment(index, exp);
                adapter.notifyItemChanged(index);
            }
            else if (action != null && action.equals(EditExpActivity.DELETE)){
                manager.removeByIndex(index);
                adapter.notifyItemRemoved(index);
            }
        }
    }


}