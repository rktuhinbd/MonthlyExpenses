package com.rktuhinbd.smartmessmanager.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rktuhinbd.smartmessmanager.Adapter.RentRecyclerAdapter;
import com.rktuhinbd.smartmessmanager.Database.DatabaseHelper;
import com.rktuhinbd.smartmessmanager.Dialog.AddRentDialog;
import com.rktuhinbd.smartmessmanager.Dialog.RentInformationBottomSheet;
import com.rktuhinbd.smartmessmanager.Listener.AddRentDialogListener;
import com.rktuhinbd.smartmessmanager.Model.Rents;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Keys;
import com.rktuhinbd.smartmessmanager.Utility.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class RentActivity extends AppCompatActivity implements AddRentDialogListener, RentInformationBottomSheet.BottomSheetListener {

    private PieChart pieChart;
    private FloatingActionButton fab;

    private RecyclerView recyclerView;
    private RentRecyclerAdapter rentRecyclerAdapter;
    private LinearLayoutManager layoutManager;

    private DatabaseHelper databaseHelper;
    private SharedPrefs sharedPrefs;
    private ArrayList<Rents> rents;
    private List<PieEntry> pieEntries;
    private PieData pieData;

    private String rentId, rentCategory, rentDescription, rentRentDate;
    private int rentAmount;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.house_rent);
        setSupportActionBar(toolbar);

        initiateProperties();
        initiateRecyclerView();
        initiatePieChart();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAddRentDialog();
            }
        });
    }

    //Initiate properties
    private void initiateProperties() {
        recyclerView = findViewById(R.id.recyclerView_rents);
        fab = findViewById(R.id.fab);
        pieChart = findViewById(R.id.pieChart_rents);

        rents = new ArrayList<>();
        pieEntries = new ArrayList<>();

        sharedPrefs = new SharedPrefs(this);
        rentRentDate = sharedPrefs.getSharedPrefDataString(Keys.MONTH);

        databaseHelper = new DatabaseHelper(this);
    }

    //Initiate RecyclerView
    private void initiateRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        rents = databaseHelper.getRents(rentRentDate);
        int totalRent = 0;
        for (int i = 0; i < rents.size(); i++) {
            totalRent += rents.get(i).getRentAmount();
        }
        sharedPrefs.setSharedPrefDataInt(Keys.TOTAL_RENT, totalRent);

        rentRecyclerAdapter = new RentRecyclerAdapter(this, rents);
        recyclerView.setAdapter(rentRecyclerAdapter);

        rentRecyclerAdapter.setOnItemClickListener(new RentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                rentId = rents.get(position).getRentId();
                rentCategory = rents.get(position).getRentCategory();
                rentAmount = rents.get(position).getRentAmount();
                rentDescription = rents.get(position).getRentDescription();
                rentDetailsBottomSheet(position);
            }
        });
    }

    //Initiate Rent Pie Chart
    private void initiatePieChart() {
        for (int i = 0; i < rents.size(); i++) {
            pieEntries.add(new PieEntry(rents.get(i).getRentAmount(), rents.get(i).getRentCategory()));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setSliceSpace(1f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pieDataSet.setValueTypeface(getResources().getFont(R.font.arima_madurai_bold));
        }
//        pieDataSet.setDrawValues(false);
        pieDataSet.setValueTextColor(Color.WHITE);      //Set Pie chart value text color
        pieDataSet.setValueTextSize(12f);
        pieData = new PieData(pieDataSet);

        pieChart.setDescription(null);                  //Hide description label in Pie chart

        pieChart.setDrawSliceText(false);               //Hide Pie chart text
        pieChart.animateX(1000);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    //Open bottom sheet to show rent details
    private void rentDetailsBottomSheet(int position) {
        RentInformationBottomSheet bottomSheet = new RentInformationBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.POSITION, position);
        bundle.putString(Keys.ID, rentId);
        bundle.putString(Keys.RENT_CATEGORY, rentCategory);
        bundle.putInt(Keys.RENT_AMOUNT, rentAmount);
        bundle.putString(Keys.RENT_DATE, rentRentDate);
        bundle.putString(Keys.RENT_DESCRIPTION, rentDescription);
        bottomSheet.setArguments(bundle);
        bottomSheet.show(getSupportFragmentManager(), "Rent info bottom sheet");
    }

    //Update Rent Pie Chart
    private void updatePieChart(int amount, String rentCategory) {
        pieEntries.add(new PieEntry(amount, rentCategory));

        pieChart.animateX(1000);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    //Open Add rent dialog
    private void OpenAddRentDialog() {
        AddRentDialog dialog = new AddRentDialog();
        dialog.setCancelable(true);
        dialog.setDialogListener(this);
        dialog.show(getSupportFragmentManager(), "Add rent");
    }

    //Store rent information to database if any value is saved in dialog
    @Override
    public void stateChanged(boolean updateToken, int amount, String category, String description) {
        if (updateToken) {
            databaseHelper.addRent(amount, category, description, rentRentDate);
            updatePieChart(amount, category);
            initiateRecyclerView();
        }
    }

    //Bottom sheet
    @Override
    public void onBottomSheetItemClick(String key, int position) {
        if (key.equals("updated")) {
            pieEntries.clear();
            rents.clear();
            rents.addAll(databaseHelper.getRents(rentRentDate));
            rentRecyclerAdapter.notifyDataSetChanged();

            int totalRent = 0;
            for (int i = 0; i < rents.size(); i++) {
                totalRent += rents.get(i).getRentAmount();
                updatePieChart(rents.get(i).getRentAmount(), rents.get(i).getRentCategory());
            }
            if (pieEntries.size() == 0) {
                pieChart.setVisibility(View.GONE);
            } else {
                pieChart.setVisibility(View.VISIBLE);
            }
            sharedPrefs.setSharedPrefDataInt(Keys.TOTAL_RENT, totalRent);
        } else {
            rents.remove(position);
            rentRecyclerAdapter.notifyItemRemoved(position);

            pieEntries.clear();
            int totalRent = 0;
            for (int i = 0; i < rents.size(); i++) {
                totalRent += rents.get(i).getRentAmount();
                updatePieChart(rents.get(i).getRentAmount(), rents.get(i).getRentCategory());
            }
            if (pieEntries.size() == 0) {
                pieChart.setVisibility(View.GONE);
            } else {
                pieChart.setVisibility(View.VISIBLE);
            }
            sharedPrefs.setSharedPrefDataInt(Keys.TOTAL_RENT, totalRent);
        }
    }
}
