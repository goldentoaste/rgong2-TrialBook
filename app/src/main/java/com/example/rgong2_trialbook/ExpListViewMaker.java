package com.example.rgong2_trialbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ExpListViewMaker {
    /*
        From:Android_coder
        Date:Feb 18, 2013
        Licences: CC BY-SA 4.0
        Link: https://stackoverflow.com/a/14933515/12471420
        Usage: Date formatting
       */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.CANADA);

    //make view for an experiment to be displayed in the recycler view. returns null if the view is not supported
    public static View makeView(Context context, Experiment exp){
        if (exp.isBinary()){
            return makeBinaryExpView(context, exp);
        }
        return null;
    }

    //make binary experiment views given exp is known to be a binaryExperiment
    @SuppressLint("DefaultLocale")
    private static View makeBinaryExpView(Context context, Experiment exp){
        /*
        From:Rohit Mhatre
        Date:Dec 31, 2018
        Licences: CC BY-SA 4.0
        Link: https://stackoverflow.com/a/53984241/12471420
        Usage: Reference on inflating views
        */
        BinaryExperiment bin = (BinaryExperiment)exp;
        View v = LayoutInflater.from(context).inflate(R.layout.list_bin_exp, null);
        setText(v, R.id.listTitle, exp.getTitle());
        setText(v, R.id.listDate, dateFormat.format(exp.getStartDate()));
        float percentage = bin.getPercentageSuccess() * 100;
        String percentText = String.format("%.2f %% Success",bin.getPercentageSuccess() * 100);
        if (Float.isNaN(percentage)){
            percentText = "- % Success";
        }
        setText(v, R.id.listPercent, percentText);
        setText(v, R.id.listDescription, exp.getDescription());
        setText(v, R.id.listTotal, bin.getTotal() + " Total");

        return v;
    }

    //helper function to set text of textView quicker
    private static void setText(View v, int id, String newText){
        TextView text = (TextView) v.findViewById(id);
        text.setText(newText);
    }
}
