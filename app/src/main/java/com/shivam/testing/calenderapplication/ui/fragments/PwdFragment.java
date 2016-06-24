package com.shivam.testing.calenderapplication.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shivam.testing.calenderapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PwdFragment extends Fragment {

    EditText psw;
    TextView str;
    static int i = 1;
    ProgressBar pb;
    public PwdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pwd, container, false);
        psw = (EditText) v.findViewById(R.id.editText1);
        str = (TextView) v.findViewById(R.id.textView1);
        pb = (ProgressBar)v.findViewById(R.id.progressBar);

        psw.setError("Enter your password..!");

        psw.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (psw.getText().toString().length() == 0) {
                    psw.setError("Enter your password..!");
                } else {
                    caculation();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

        });
        return v;
    }
    protected void caculation() {
        // TODO Auto-generated method stub
        String temp = psw.getText().toString();
        System.out.println(i + " current password is : " + temp);
        i = i + 1;

        int length = 0, uppercase = 0, lowercase = 0, digits = 0, symbols = 0, bonus = 0, requirements = 0;

        int lettersonly = 0, numbersonly = 0, cuc = 0, clc = 0;

        length = temp.length();
        for (int i = 0; i < temp.length(); i++) {
            if (Character.isUpperCase(temp.charAt(i)))
                uppercase++;
            else if (Character.isLowerCase(temp.charAt(i)))
                lowercase++;
            else if (Character.isDigit(temp.charAt(i)))
                digits++;
            else
                symbols++;

            // symbols = length - uppercase - lowercase - digits;

        }
        if(length<=6){
            pb.setProgress(30);
            pb.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            psw.setText("week");
        }
        if((length>6)&& ((uppercase>0)||digits>0||symbols>0)){
            pb.setProgress(50);
            pb.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            psw.setText("good");
        }
        if((length>6)&& ((uppercase>0)&&digits>0&&symbols>0)){
            pb.setProgress(50);
            pb.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            psw.setText("Strong");
        }

       /* for (int j = 1; j < temp.length() - 1; j++) {

            if (Character.isDigit(temp.charAt(j)))
                bonus++;

        }

        for (int k = 0; k < temp.length(); k++) {

            if (Character.isUpperCase(temp.charAt(k))) {
                k++;

                if (k < temp.length()) {

                    if (Character.isUpperCase(temp.charAt(k))) {

                        cuc++;
                        k--;

                    }

                }

            }

        }

        for (int l = 0; l < temp.length(); l++) {

            if (Character.isLowerCase(temp.charAt(l))) {
                l++;

                if (l < temp.length()) {

                    if (Character.isLowerCase(temp.charAt(l))) {

                        clc++;
                        l--;

                    }

                }

            }

        }

        System.out.println("length" + length);
        System.out.println("uppercase" + uppercase);
        System.out.println("lowercase" + lowercase);
        System.out.println("digits" + digits);
        System.out.println("symbols" + symbols);
        System.out.println("bonus" + bonus);
        System.out.println("cuc" + cuc);
        System.out.println("clc" + clc);

        if (length > 7) {
            requirements++;
        }

        if (uppercase > 0) {
            requirements++;
        }

        if (lowercase > 0) {
            requirements++;
        }

        if (digits > 0) {
            requirements++;
        }

        if (symbols > 0) {
            requirements++;
        }

        if (bonus > 0) {
            requirements++;
        }

        if (digits == 0 && symbols == 0) {
            lettersonly = 1;
        }

        if (lowercase == 0 && uppercase == 0 && symbols == 0) {
            numbersonly = 1;
        }

        int Total = (length * 4) + ((length - uppercase) * 2)
                + ((length - lowercase) * 2) + (digits * 4) + (symbols * 6)
                + (bonus * 2) + (requirements * 2) - (lettersonly * length*2)
                - (numbersonly * length*3) - (cuc * 2) - (clc * 2);

        System.out.println("Total" + Total);

        if(Total<30){
            pb.setProgress(Total-15);
            str.setText("week");
        }

        else if (Total>=40 && Total <50)
        {
            pb.setProgress(Total-20);
            str.setText("good");
        }

        else if (Total>=56 && Total <70)
        {
            pb.setProgress(Total-25);
            str.setText("strong");
        }

        else if (Total>=76)
        {
            pb.setProgress(Total-30);
            str.setText("strongest");
        }
        else{
            pb.setProgress(Total-20);
            str.setText("excel");
        }*/

    }
}
