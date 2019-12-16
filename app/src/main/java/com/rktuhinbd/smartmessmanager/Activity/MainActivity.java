package com.rktuhinbd.smartmessmanager.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Keys;
import com.rktuhinbd.smartmessmanager.Utility.SharedPrefs;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SharedPrefs sharedPrefs;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private CardView cardViewMembers, cardViewRent, cardViewMeal, cardViewExpense, cardViewMealRate, cardViewBalanceSheet;
    private TextView textViewNumberOfMembers, textViewTotalRent;

    private int numberOfMembers = 0, totalRent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = new SharedPrefs(this);
        setSpinner();
        setCardViewMembers();
        setCardViewHouseRent();
        setCardViewMeal();
        setCardViewExpense();
        setCardViewMealRate();
        setCardViewBalanceSheet();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        setSpinner();
        setCardViewMembers();
        setCardViewHouseRent();
        setCardViewMeal();
        setCardViewExpense();
        setCardViewMealRate();
        setCardViewBalanceSheet();
    }

    //Members CardView Action Setting
    private void setCardViewMembers() {
        numberOfMembers = sharedPrefs.getSharedPrefDataInt(Keys.MEMBERS);
        totalRent = sharedPrefs.getSharedPrefDataInt(Keys.TOTAL_RENT);

        textViewNumberOfMembers = findViewById(R.id.textView_numberOfMembers);
        if (numberOfMembers > 0) {
            textViewNumberOfMembers.setText(String.valueOf(numberOfMembers));
        }

        textViewTotalRent = findViewById(R.id.textView_baseHouseRent);
        if (totalRent > 0) {
            textViewTotalRent.setText(String.valueOf(totalRent));
        }

        cardViewMembers = findViewById(R.id.cardView_members);
        cardViewMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MembersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //House Rent CardView Action Setting
    private void setCardViewHouseRent() {
        cardViewRent = findViewById(R.id.cardView_rent);

        cardViewRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Meals CardView Action Setting
    private void setCardViewMeal() {
        cardViewMeal = findViewById(R.id.cardView_meal);

        cardViewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Expense CardView Action Setting
    private void setCardViewExpense() {
        cardViewExpense = findViewById(R.id.cardView_expense);

        cardViewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpenseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Meal Rate CardView Action Setting
    private void setCardViewMealRate() {
        cardViewMealRate = findViewById(R.id.cardView_mealRate);

        cardViewMealRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealRateActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Members CardView Action Setting
    private void setCardViewBalanceSheet() {
        cardViewBalanceSheet = findViewById(R.id.cardView_balanceSheet);

        cardViewBalanceSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BalanceSheetActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Spinner Initialization and Action
    private void setSpinner() {
        int spinnerPosition = sharedPrefs.getSharedPrefDataInt(Keys.MONTH_SELECTED);

        spinner = findViewById(R.id.spinner_months);
        // Create an ArrayAdapter using the string array and a default spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.months, R.layout.spinner_background_white);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_background_martinique);
        // Apply the spinnerAdapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        if (spinnerPosition > -1) {
            spinner.setSelection(spinnerPosition);      //Set spinner position according the data from shared preference
        }
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sharedPrefs.setSharedPrefDataInt(Keys.MONTH_SELECTED, position);     //Store selected spinner position to shared preference
        String month = "";
        switch (position) {
            case 0:
                month = "January";
                break;
            case 1:
                month = "February";
                break;
            case 2:
                month = "March";
                break;
            case 3:
                month = "April";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "June";
                break;
            case 6:
                month = "July";
                break;
            case 7:
                month = "August";
                break;
            case 8:
                month = "September";
                break;
            case 9:
                month = "October";
                break;
            case 10:
                month = "November";
                break;
            case 11:
                month = "December";
                break;
        }
        sharedPrefs.setSharedPrefDataString(Keys.MONTH, month);     //Store month according to the position of spinner to shared preference
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
