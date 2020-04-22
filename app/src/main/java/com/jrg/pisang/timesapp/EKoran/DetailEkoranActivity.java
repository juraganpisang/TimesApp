package com.jrg.pisang.timesapp.EKoran;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jrg.pisang.timesapp.Adapter.EkoranSliderAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.DataKoranModel;
import com.jrg.pisang.timesapp.Model.EKoranDetailModel;
import com.jrg.pisang.timesapp.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEkoranActivity extends AppCompatActivity {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private EkoranSliderAdapter ekoranSliderAdapter;
    private DataKoranModel korans;
    private String mId;
    private int id;
    private SliderView sliderView;
    private ArrayList<String> listImage = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ekoran);

        //get intent
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        id = Integer.valueOf(mId);
        Log.e("ID", mId);

        sliderView = findViewById(R.id.imageSlider);

        loadJSON();

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(1000); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    private void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<EKoranDetailModel> callKoran;

        callKoran = apiInterface.getEKoranDetail(id, key);
        callKoran.enqueue(new Callback<EKoranDetailModel>() {
            @Override
            public void onResponse(Call<EKoranDetailModel> call, Response<EKoranDetailModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    korans = response.body().getData();
                    if (!korans.getImg1().equals("")) {
                        listImage.add(korans.getImg1());
                    }
                    if (!korans.getImg2().equals("")) {
                        listImage.add(korans.getImg2());
                    }
                    if (!korans.getImg3().equals("")) {
                        listImage.add(korans.getImg3());
                    }
                    if (!korans.getImg4().equals("")) {
                        listImage.add(korans.getImg4());
                    }
                    if (!korans.getImg5().equals("")) {
                        listImage.add(korans.getImg5());
                    }
                    if (!korans.getImg6().equals("")) {
                        listImage.add(korans.getImg6());
                    }
                    if (!korans.getImg7().equals("")) {
                        listImage.add(korans.getImg7());
                    }
                    if (!korans.getImg8().equals("")) {
                        listImage.add(korans.getImg8());
                    }
                    if (!korans.getImg9().equals("")) {
                        listImage.add(korans.getImg9());
                    }
                    if (!korans.getImg10().equals("")) {
                        listImage.add(korans.getImg10());
                    }
                    if (!korans.getImg11().equals("")) {
                        listImage.add(korans.getImg11());
                    }
                    if (!korans.getImg12().equals("")) {
                        listImage.add(korans.getImg12());
                    }
                    if (!korans.getImg13().equals("")) {
                        listImage.add(korans.getImg13());
                    }
                    if (!korans.getImg14().equals("")) {
                        listImage.add(korans.getImg14());
                    }
                    if (!korans.getImg15().equals("")) {
                        listImage.add(korans.getImg15());
                    }

                    ekoranSliderAdapter = new EkoranSliderAdapter(listImage, getBaseContext());
                    sliderView.setSliderAdapter(ekoranSliderAdapter);

                } else {
                    Toast.makeText(DetailEkoranActivity.this, "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EKoranDetailModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DetailEkoranActivity.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
