package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

public class SheetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);
        showTable();

    }
    // ** Created by Pb **
    private void showTable() {
        DbHelper dbHelper = new DbHelper(this);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        long[] idArray = getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");
        String month = getIntent().getStringExtra("month");

        int DAY_IN_MONTH = getDayInMonth(month);

        //ROW =======================================================================================
        int rowSize = idArray.length+1;
        TableRow[] rows = new TableRow[rowSize];
        TextView[] roll_textView = new TextView[rowSize];
        TextView[] name_textView = new TextView[rowSize];
        TextView[][] status_textView = new TextView[rowSize][DAY_IN_MONTH + 1];

        for (int i = 0; i<rowSize; i++){
            roll_textView[i] = new TextView(this);
            name_textView[i] = new TextView(this);
            for(int j=0; j <= DAY_IN_MONTH; j++){
                status_textView[i][j] = new TextView(this);
            }
        }

        // Header --------------------------------------------------------------------------------------------
        roll_textView[0].setText("Roll");
        roll_textView[0].setTypeface(roll_textView[0].getTypeface(), Typeface.BOLD);
        name_textView[0].setText("Name");
        name_textView[0].setTypeface(name_textView[0].getTypeface(), Typeface.BOLD);
        for(int i = 1; i <= DAY_IN_MONTH; i++){
            status_textView[0][i].setText(String.valueOf(i));
            status_textView[0][i].setTypeface(name_textView[0].getTypeface(), Typeface.BOLD);
        }

        for(int i =1; i<rowSize; i++){
            roll_textView[i].setText(String.valueOf(rollArray[i-1]));
            name_textView[i].setText(nameArray[i-1]);

            for (int j = 1; j <= DAY_IN_MONTH ; j++) {
                String day = String.valueOf(j);
                if(day.length()==1) day = "0"+day;

                String date = day+"."+month;
                String status = dbHelper.getStatus(idArray[i-1],date);
                status_textView[i][j].setText(status);
            }
        }

        for (int i = 0; i < rowSize ; i++) {
            rows[i] = new TableRow(this);

            if(i%2 == 0)
                rows[i].setBackgroundColor(Color.parseColor("#EEEEEE"));
            else rows[i].setBackgroundColor(Color.parseColor("#E4E4E4"));

            roll_textView[i].setPadding(16,16,16,16);
            name_textView[i].setPadding(16,16,16,16);

            rows[i].addView(roll_textView[i]);
            rows[i].addView(name_textView[i]);

            for (int j = 1; j <= DAY_IN_MONTH ; j++) {
                status_textView[i][j].setPadding(16,16,16,16);

                rows[i].addView(status_textView[i][j]);
            }

            tableLayout.addView(rows[i]);
        }
        tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
    }

    // For getting the Days in a Month ---------------------------------------------------------------------------------------
    private int getDayInMonth(String month) {
        //02.2022
        int monthIndex = Integer.parseInt(month.substring(0, 2)) - 1;
        int year = Integer.parseInt(month.substring(3));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,monthIndex);
        calendar.set(Calendar.YEAR,year);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    }
}