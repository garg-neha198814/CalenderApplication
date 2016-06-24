package com.shivam.testing.calenderapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shivam.testing.calenderapplication.R;

import java.util.ArrayList;

/**
 * Created by root on 14/4/16.
 */
public class ShowSelectedDates extends RecyclerView.Adapter<ShowSelectedDates.DateViewHolder> {

    private ArrayList<String> mSelectedDatesList;
    private Activity activity;

    public ShowSelectedDates(Activity activity, ArrayList<String> mSelectedDatesList) {
        this.activity = activity;
        this.mSelectedDatesList = mSelectedDatesList;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_date, parent, false);
        DateViewHolder mDateView = new DateViewHolder(v);
        return mDateView;
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        System.out.println("adapter item >>"+mSelectedDatesList.get(position));
        holder.mSelectedDate.setText(mSelectedDatesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSelectedDatesList.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder {

        private TextView mSelectedDate;

        public DateViewHolder(View itemView) {
            super(itemView);
            mSelectedDate = (TextView) itemView.findViewById(R.id.selected_date);
        }
    }
}
