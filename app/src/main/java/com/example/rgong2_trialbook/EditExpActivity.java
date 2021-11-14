package com.example.rgong2_trialbook;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditExpActivity extends AppCompatActivity {

    //date sources already provided in ExperimentAdapter
    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.CANADA);

    //constants as intent extra names
    public static final String SAVE = "com.example.rgong2_trialbook.EditExpActivity.SAVE";
    public static final String DELETE = "com.example.rgong2_trialbook.EditExpActivity.DELETE";
    public static final String ACTION = "com.example.rgong2_trialbook.EditExpActivity.ACTION";
    public static final String EXPERIMENT = "com.example.rgong2_trialbook.EditExpActivity.EXPERIMENT";
    public static final String INDEX = "com.example.rgong2_trialbook.EditExpActivity.INDEX";
    /*
    From:Android documentation
    Date: Dec 10, 2020
    Licences: Apache 2.0 license
    Link: https://developer.android.com/training/basics/firstapp/starting-activity
    Usage: retrieve intent info
   */
    private BinaryExperiment exp;
    private int index;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edit experiment");
        setContentView(R.layout.edit_exp);
        Intent intent = getIntent();
        //here Binary experiment is assumed since it is the only experiment type availble for this project
        exp = (BinaryExperiment) intent.getSerializableExtra(ExperimentAdapter.EXPERIMENT);
        index = intent.getIntExtra(ExperimentAdapter.INDEX, 0);


        //initialize text
        setText(R.id.editExpTitle, exp.getTitle());
        setText(R.id.editExpNotes, exp.getDescription());
        setText(R.id.editExpStartDate, dateFormat.format(exp.getStartDate()));
        setText(R.id.editSuccessCount, exp.getSuccessCount() + "");
        setText(R.id.editFailCount, exp.getFailCount() + "");
        updateRatioTotal();
    }

    //updates both success raete and total number
    private void updateRatioTotal() {

        float percentage = exp.getPercentageSuccess() * 100;
        String percentText = String.format("%.2f %% Success", exp.getPercentageSuccess() * 100);
        //no percentage should be displayed when total is 0
        if (Float.isNaN(percentage)) {
            percentText = "- %Success";
        }
        setText(R.id.editSuccessPercent, percentText);
        setText(R.id.editTotal, exp.getTotal() + " Total");
    }

    public void plusSuccess(View v) {
        exp.incrementSuccess();
        setText(R.id.editSuccessCount, exp.getSuccessCount() + "");
        updateRatioTotal();
    }

    public void minusSuccess(View v) {
        exp.decrementSuccess();
        setText(R.id.editSuccessCount, exp.getSuccessCount() + "");
        updateRatioTotal();
    }

    public void plusFail(View v) {
        exp.incrementFailure();
        setText(R.id.editFailCount, exp.getFailCount() + "");
        updateRatioTotal();
    }

    public void minusFail(View v) {
        exp.decrementFailure();
        setText(R.id.editFailCount, exp.getFailCount() + "");
        updateRatioTotal();
    }

    /*
        From:ThePCWizard
        Date: July 17, 2019
        Licences: CC BY-SA 4.0
        Link: https://stackoverflow.com/a/12409215/12471420
        Usage: Returning to activity without starting new activity
    */

    //asks main activity to save experiments
    public void save(View v) {
        //retrieve info from text fields and putting them in Experiment object
        exp.setTitle(fetchString(R.id.editExpTitle));
        exp.setDescription(fetchString(R.id.editExpNotes));
        try {
            exp.setStartDate(dateFormat.parse(fetchString(R.id.editExpStartDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent i = new Intent(this, MainActivity.class);

        i.putExtra(ACTION, SAVE);
        i.putExtra(EXPERIMENT, exp);
        i.putExtra(INDEX, index);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

    //asks main activity to delete the current experiment
    public void delete(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(ACTION, DELETE);
        i.putExtra(EXPERIMENT, exp);
        i.putExtra(INDEX, index);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(i, 0);
    }


    /*
        From:Android_coder
        Date:Feb 18, 2013
        Licences: CC BY-SA 4.0
        Link: https://stackoverflow.com/a/14933515/12471420
        Usage: Show date picker
       */
    //popup a date picker window then update the date text view
    public void handleDatePicking(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MARCH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                setText(R.id.editExpStartDate, dateFormat.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    //get string conflicts with super classes's getString method, so I named it fetch
    //helper method to get string from text fields
    private String fetchString(int id) {
        TextView text = (TextView) findViewById(id);
        return text.getText().toString();
    }

    //helper function to set text of textView quicker
    private void setText(int id, String newText) {
        TextView text = (TextView) findViewById(id);
        text.setText(newText);
    }
}
