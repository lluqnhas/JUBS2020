package com.example.jubs2020;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private int faseI;
    private int faseII;
    private int faseIII;
    private TextView txt_resultado;

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        txt_resultado = (TextView) findViewById(R.id.txt_resultado);

        txt_resultado.setText("deu bommmm");

        pieChart = (PieChart) findViewById(R.id.graficopizza);
        gerarGreafico();

    }

    private void gerarGreafico() {
        // descrição do grafico
        Description description = new Description();
        description.setText("grafico pizza ");

        pieChart.setDescription(description);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(1,6));
        pieEntries.add(new PieEntry(2,6));
        pieEntries.add(new PieEntry(3,6));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "1 >> faseI, 2 >> faseII, 3 >> faseIII");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

    }

}
