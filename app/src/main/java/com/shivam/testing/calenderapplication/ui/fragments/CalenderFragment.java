package com.shivam.testing.calenderapplication.ui.fragments;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.EventDecorator;
import com.shivam.testing.calenderapplication.R;
import com.shivam.testing.calenderapplication.adapter.ShowSelectedDates;
import com.shivam.testing.calenderapplication.ui.activities.BaseActivity;
import com.shivam.testing.calenderapplication.ui.activities.HomeActivity;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment implements OnDateSelectedListener {

    private MaterialCalendarView mCalender;
    private RecyclerView mRecyclerView;
    private View mRootView;
    private ShowSelectedDates mSelectedDatesAdapter;
    private ArrayList<String> mDates;
    ArrayList<CalendarDay> DateList;

    public CalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_calender, container, false);
        ((HomeActivity)getActivity()).setActionBarTitle("Calender");
        initViews();
        return mRootView;
    }

    private void initViews() {
        mDates = new ArrayList<>();
        DateList = new ArrayList<>();
        mCalender = (MaterialCalendarView) mRootView.findViewById(R.id.calendarView);
        mCalender.setDateTextAppearance(R.style.CustomTextAppearance);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayout);
        mCalender.setOnDateChangedListener(this);
        mCalender.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        Date d1 = BaseActivity.StringToDate("2016-4-29");
        mCalender.setSelectedDate(d1);

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        CalendarDay selectedDate = widget.getSelectedDate();
        String dateInString = selectedDate.getYear() + "-" + selectedDate.getMonth() + "-" + selectedDate.getDay();
        DateList.add(date);
        mDates.add(dateInString);
        System.out.println("selected date >>>" + selectedDate.getDate().toString());
        mSelectedDatesAdapter = new ShowSelectedDates(getActivity(), mDates);

     /*   widget.addDecorator(new EventDecorator(15, Color.WHITE, DateList, Color.BLACK));
        widget.invalidateDecorators();*/
        mRecyclerView.setAdapter(mSelectedDatesAdapter);

//        mSelectedDatesAdapter.notifyDataSetChanged();

    }
}
