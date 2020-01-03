package com.rktuhinbd.smartmessmanager.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SharedPrefs sharedPrefs;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private CardView cardViewMembers, cardViewExpenses, cardViewMeal, cardViewExpense, cardViewMealRate, cardViewBalanceSheet;
    private TextView textViewNumberOfMembers, textViewTotalExpense;

    private int numberOfMembers = 0, totalRent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = new SharedPrefs(this);
        setSpinner();
        setCardViewMembers();
        setCardViewExpenses();
        setCardViewMeal();
        setCardViewExpense();
        setCardViewMealRate();
        setCardViewBalanceSheet();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("On Resume", "Called");
        sharedPrefs = new SharedPrefs(this);
        setSpinner();
        setCardViewMembers();
        setCardViewExpenses();
        setCardViewMeal();
        setCardViewExpense();
        setCardViewMealRate();
        setCardViewBalanceSheet();
    }

    //Members CardView Action Setting
    private void setCardViewMembers() {
        if (sharedPrefs.getSharedPrefDataInt(Keys.MEMBERS) > 0) {
            numberOfMembers = sharedPrefs.getSharedPrefDataInt(Keys.MEMBERS);
        }
        if (sharedPrefs.getSharedPrefDataInt(Keys.TOTAL_RENT) > 0) {
            totalRent = sharedPrefs.getSharedPrefDataInt(Keys.TOTAL_RENT);
        }

        textViewNumberOfMembers = findViewById(R.id.textView_numberOfMembers);
        textViewNumberOfMembers.setText(String.valueOf(numberOfMembers));

        textViewTotalExpense = findViewById(R.id.textView_baseExpense);
        if (totalRent > 0) {
            textViewTotalExpense.setText(String.valueOf(totalRent));
        } else {
            textViewTotalExpense.setText(String.valueOf(0));
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
    private void setCardViewExpenses() {
        cardViewExpenses = findViewById(R.id.cardView_expenses);

        cardViewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpenseActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), MealsActivity.class);
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
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        spinner = findViewById(R.id.spinner_months);
        // Create an ArrayAdapter using the string array and a default spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.months, R.layout.spinner_background_white);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_background_martinique);
        // Apply the spinnerAdapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        if (spinnerPosition > -1) {
            spinner.setSelection(spinnerPosition);      //Set spinner position according the data from shared preference
        } else {
            spinner.setSelection(currentMonth);
        }
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String thisYear = dateFormat.format(date);

        sharedPrefs.setSharedPrefDataInt(Keys.MONTH_SELECTED, position);     //Store selected spinner position to shared preference
        String month = "";
        switch (position) {
            case 0:
                month = "Jan, " + thisYear;
                break;
            case 1:
                month = "Feb, " + thisYear;
                break;
            case 2:
                month = "Mar, " + thisYear;
                break;
            case 3:
                month = "Apr, " + thisYear;
                break;
            case 4:
                month = "May, " + thisYear;
                break;
            case 5:
                month = "Jun, " + thisYear;
                break;
            case 6:
                month = "Jul, " + thisYear;
                break;
            case 7:
                month = "Aug, " + thisYear;
                break;
            case 8:
                month = "Sep, " + thisYear;
                break;
            case 9:
                month = "Oct, " + thisYear;
                break;
            case 10:
                month = "Nov, " + thisYear;
                break;
            case 11:
                month = "Dec, " + thisYear;
                break;
        }
        Log.e("This month", month);
        sharedPrefs.setSharedPrefDataString(Keys.MONTH, month);     //Store month according to the position of spinner to shared preference
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
