package com.example.jubs2020;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private String[] p;

    private ArrayList<String> respostas = new ArrayList<>();
    private ArrayList<Integer> resultado = new ArrayList<>();


    private TextView txt_pergunta;
    private Button bt_sim, bt_nao;

    private int cont_pergunta, fase;
    private Integer[] cont_sim = new Integer[3];

    private FirebaseFirestore bd;
    private ImageView notas_perguntas;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bd = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);

        // recuperando bloco de perguntas
        p = getResources().getStringArray(R.array.perguntas);
        cont_pergunta = 0;

        fase = 1;
        verificarFase(fase);

        txt_pergunta = (TextView) findViewById(R.id.txt_pergunta);
        txt_pergunta.setText(p[0]);

        bt_sim = (Button) findViewById(R.id.bt_sim);
        bt_nao = (Button) findViewById(R.id.bt_nao);

        cont_sim[0] = 0;
        cont_sim[1] = 0;
        cont_sim[2] = 0;
    }

    public void onClick(View view) {

        if (cont_pergunta < p.length) {
            //verifica a fase e  chama a tela de orientações
            if (p[cont_pergunta].equals("fase_II")) {
                verificarFase(fase);
            } else if (p[cont_pergunta].equals("fase_III")) {
                verificarFase(fase);
            } else {

                txt_pergunta.setText(p[cont_pergunta]);
                // conta as respostas por fase
                switch (view.getId()) {
                    case R.id.bt_sim:
                        respostas.add("SIM");

                        if (fase == 1) {
                            cont_sim[0]++;
                        } else if (fase == 2) {
                            cont_sim[1]++;
                        } else {
                            cont_sim[2]++;
                        }

                        cont_pergunta++;
                        break;
                    case R.id.bt_nao:
                        respostas.add("NAO");
                        cont_pergunta++;
                        break;

                }
            }
        }else{

                resultado.add(cont_sim[0]);
                resultado.add(cont_sim[1]);
                resultado.add(cont_sim[2]);

                pd.setTitle(" Calculando Resultado");
                pd.show();

                mAuth = FirebaseAuth.getInstance();
                bd.collection("Dados").document(mAuth.getCurrentUser().getUid())
                       .update("respostas",respostas, "resultado", resultado)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                pd.dismiss();
                                startActivity(new Intent(Main2Activity.this, Main3Activity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Main2Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
        }

    }

    public void verificarFase(int i) {

        Intent intent = new Intent(this, Main4Activity.class);
        intent.putExtra("fase", i);
        startActivity(intent);

        cont_pergunta++;
        fase++;
    }

}