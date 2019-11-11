package com.example.jubs2020;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private String[] p;
    private String[] o;

    private TextView txt_pergunta, txt_orientacao;
    private Button bt_sim, bt_nao;

    private int cont_pergunta, fase;
    private int[] cont_sim = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        p = getResources().getStringArray(R.array.perguntas);
        o = getResources().getStringArray(R.array.orientacao);

        txt_pergunta = (TextView) findViewById(R.id.txt_pergunta);
        txt_pergunta.setText(p[0]);

        txt_orientacao = (TextView) findViewById(R.id.txt_orientacao);
        txt_orientacao.setText(o[0]);
        bt_sim = (Button) findViewById(R.id.bt_sim);
        bt_nao = (Button) findViewById(R.id.bt_nao);

        cont_pergunta = 0;
        fase = 0;

        cont_sim[0] = 0;
        cont_sim[1] = 0;
        cont_sim[2] = 0;
    }

    public void onClick(View view) {
        cont_pergunta++;

        if( cont_pergunta < p.length){
            if(p[cont_pergunta].equals("fase_II") ){
                txt_orientacao.setText(o[1]);
                fase++;
                cont_pergunta++;
            }else if( p[cont_pergunta].equals("fase_III")){
                txt_orientacao.setText(o[2]);
                fase++;
                cont_pergunta++;
            }
            txt_pergunta.setText(p[cont_pergunta]);
        }else{

            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
            Bundle dados = new Bundle();
            dados.putString("faseI", String.valueOf(cont_sim[0]));
            dados.putString("faseII", String.valueOf(cont_sim[1]));
            dados.putString("faseIII", String.valueOf(cont_sim[2]));
            startActivity(intent);
        }

        if(view.getId() == R.id.bt_sim){

            if(fase == 1){
                cont_sim[1]++;
            }else if(fase == 2){
                cont_sim[2]++;
            }else{
                cont_sim[0]++;
            }
        }

    }
}
