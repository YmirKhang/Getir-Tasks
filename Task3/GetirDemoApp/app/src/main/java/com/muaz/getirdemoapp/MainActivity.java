package com.muaz.getirdemoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.muaz.getirdemoapp.models.RequestObj;
import com.muaz.getirdemoapp.models.objResponse;
import com.muaz.getirdemoapp.network.GetirService;
import com.muaz.getirdemoapp.network.NetworkAPI;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    @BindView(R.id.cal_date_range)
    CalendarPickerView calDateRange;

    @BindView(R.id.et_max_count)
    EditText edtMaxCount;

    @BindView(R.id.et_min_count)
    EditText edtMinCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

    }


    private void init(){

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -10);


        calDateRange.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withSelectedDate(new Date());
        ArrayList<Integer> list = new ArrayList<>();

        calDateRange.deactivateDates(list);



    }

    @OnClick(R.id.ll_search)
    public void searchClicked(){
        ArrayList<Date> dates = (ArrayList<Date>) calDateRange.getSelectedDates();
        String startDate= "",endDate= "";

        if(dates.size()>1){
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");

            startDate = dtf.format(dates.get(0));
            endDate = dtf.format(dates.get(dates.size()-1));
        }
        int max = edtMaxCount.getText().toString().equals("") ? 0 : Integer.parseInt(edtMaxCount.getText().toString());
        int min = edtMinCount.getText().toString().equals("") ? 0 : Integer.parseInt(edtMinCount.getText().toString());

        RequestObj req = new RequestObj();

        req.setEndDate(endDate);
        req.setStartDate(startDate);
        req.setMaxCount(max);
        req.setMinCount(min);


        Intent intent = new Intent(this,ActivityResults.class);
        intent.putExtra("requestObject",req);
        startActivity(intent);



    }

}
