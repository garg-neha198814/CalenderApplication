package com.shivam.testing.calenderapplication.ui.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.shivam.testing.calenderapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    public final static String PREFERENCES_FILE = "PetChecker";

    private Snackbar snackbar;

    public  boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public void replaceFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.main, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void addFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .add(R.id.main, fragment, fragment.getClass().getSimpleName())

                .commit();
    }

    public void addFragmentWithBackStack(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .add(R.id.main, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void replaceFragmentWithBackStack(Fragment fragment, Fragment fragment1) {
        getFragmentManager().beginTransaction()
                .replace(R.id.main, fragment, fragment.getClass().getSimpleName()).addToBackStack(fragment1.getClass().getName())
                .commit();
    }

    public String parseDateToDDMMYY(String dateText) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateText);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }



    public void showSnack(View view, String msg) {
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void dismissSnack() {
        if (snackbar != null)
            snackbar.dismiss();
    }

    public void showSnackWithAction(View view, String msg, View.OnClickListener listener) {
        if (!isNetworkAvailable()) {
            msg = "No Internet Connection";
        }
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).setAction("Retry", listener);
        snackbar.show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    String convertBase64(String fileName) {
        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return encodedString;

    }

    public Uri getImageUri(Activity context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }

    /* Get current path of captured image */
    public String getRealPathFromURI(Activity context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public ArrayList<String> getAddress(Activity activity, double lat, double longi) {
        /*
        * Returns First Address Associate with LAtitude and Longitude
        * */
        Geocoder geocoder;
        ArrayList<String> location = new ArrayList<>();
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else null
            location.add(address);
            location.add(city);
            location.add(state);
            location.add(country);
            location.add(knownName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * Return the string from shared preference for the app.
     *
     * @param context
     */
    public SharedPreferences getAppPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences,
        // but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
    }

    /* *
             * Return Address Form Location Name String
     * @param1 locan name
     * @param2 context
     *
     *
             * */
    public Address getAddressFromLocation(final String locationAddress,
                                          final Context context) {
         /*
        * Returns First Latitude And Longitude  Associate with Location
        * */
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        Address address = null;
        try {
            List
                    addressList = geocoder.getFromLocationName(locationAddress, 1);
            if (addressList != null && addressList.size() > 0) {
                address = (Address) addressList.get(0);
            }
        } catch (Exception e) {
            Log.e("TAG", "Unable to connect to Geocoder", e);
        }

        return address;
    }


    /*
    * Compare Two Dates
    * */
    public  int compareToDay(Date date1, Date date2) {
        /*
        * Return 1 if true
        * */
        if (date1 == null || date2 == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date1).compareTo(sdf.format(date2));
    }
    /*
     * Convert String  to Date
    * */

    public static Date StringToDate(String Date) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(Date);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
    public static String DateToString (Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


        String reportDate = df.format(date);

// Print what date is today!
        System.out.println("Report Date: " + reportDate);
        return reportDate;
    }

}
