package com.firetera.percyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class CateringInfo extends AppCompatActivity {

    private ImageSlider imageSlider;
    Button cateringinfo_backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catering_info);

        cateringinfo_backarrow = findViewById(R.id.cateringinfo_backarrow);
        imageSlider = findViewById(R.id.precyimage);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/bc/ff/66/bcff6614e1a806a550ba263d38aa9410.jpg", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/f0/b6/b5/f0b6b5417d647c1c89726c75c7b7ca10.jpg", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/e4/99/61/e499614aa2e8f148fa8e408d3580386d.jpg", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/2a/c3/44/2ac344754c3082ebab2afcb0b221685a.jpg", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/35/7e/43/357e433fc7b0684b95817f874e34d31f.jpg", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);

        cateringinfo_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }
}