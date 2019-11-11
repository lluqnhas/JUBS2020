package com.example.jubs2020;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    private TextView txt_orientacao;
    private ImageView img_fase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        try {
            int id = getIntent().getIntExtra("fase", 0);
            String[] o = getResources().getStringArray(R.array.orientacao);

            txt_orientacao = (TextView) findViewById(R.id.txt_orientacao);
            img_fase = (ImageView) findViewById(R.id.img_fase);

            switch (id){
                case 1:{
                    txt_orientacao.setText(o[0]);
                    img_fase.setImageResource(R.drawable.estressado_01);
                    break;
                }case 2:{
                    txt_orientacao.setText(o[1]);
                    img_fase.setImageResource(R.drawable.estressado_02);
                    break;
                }case 3:{
                    txt_orientacao.setText(o[2]);
                    img_fase.setImageResource(R.drawable.estresse_03);
                    break;
                }
            }

        }catch (NullPointerException e){
            Toast.makeText(this, "Erro.", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 10000);
    }
}
