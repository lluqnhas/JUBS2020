package com.example.jubs2020;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    // variaveis do sistema
    private EditText txt_modalidade, txt_idade, txt_sexo, txt_email;
    private String modalidade, idade, sexo, email;
    private ImageView bt_play;
    private ProgressDialog pb;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private final String senha = "123456";
    private FirebaseFirestore bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referenciando os componentes
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_modalidade = (EditText) findViewById(R.id.txt_modalidade);
        txt_idade = (EditText) findViewById(R.id.txt_idade);
        txt_sexo = (EditText) findViewById(R.id.txt_sexo);

        pb = new ProgressDialog(this);

        bt_play = (ImageView) findViewById(R.id.bt_play);
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // recebendo dados dos campos
                email = txt_email.getText().toString().trim();
                modalidade = txt_modalidade.getText().toString().trim();
                idade = txt_idade.getText().toString().trim();
                sexo = txt_sexo.getText().toString().trim();

                // verificando se os campos foram preenchidos
                if (email.isEmpty()) {
                    txt_email.setError("campo obrigatorio");
                    txt_email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txt_email.setError("campo obrigatorio");
                    txt_email.requestFocus();
                } else if (modalidade.isEmpty()) {
                    txt_modalidade.setError("campo obrigatorio");
                    txt_modalidade.requestFocus();
                } else if (idade.isEmpty()) {
                    txt_idade.setError("campo obrigatorio");
                    txt_idade.requestFocus();
                } else if (sexo.isEmpty()) {
                    txt_sexo.setError("campo obrigatorio");
                    txt_sexo.requestFocus();
                } else {

                    pb.setTitle("Autenticado ...");
                    pb.show();

                    //autenticando
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    //logando
                                    pb.setTitle("Acessando Dados ...");
                                    mAuth = FirebaseAuth.getInstance();
                                    mAuth.signInWithEmailAndPassword(email, senha)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {

                                                    ArrayList<String> respostas=  new ArrayList<>();
                                                    ArrayList<Integer> resultado = new ArrayList<>();

                                                    //registrando
                                                    pb.setTitle("So mais um instante ...");
                                                    bd = FirebaseFirestore.getInstance();
                                                    String id = mAuth.getCurrentUser().getUid();
                                                    Map<String, Object> dados = new HashMap<>();
                                                    dados.put("id", id);
                                                    dados.put("modalidade", modalidade);
                                                    dados.put("ano nascimento", idade);
                                                    dados.put("sexo", sexo);
                                                    dados.put("respostas", respostas);
                                                    dados.put("resultado", resultado);

                                                    bd.collection("Dados").document(id).set(dados)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    pb.setTitle("Concluido!");
                                                                    pb.dismiss();
                                                                    //proxima tela
                                                                    startActivity(new Intent(MainActivity.this, Main2Activity.class));

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
    }


    /*
    private void inserirAuth(final String email, final String senha, final Atleta a){

        pb.setTitle("Autenticado ...");
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginAuth(email, senha, a);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginAuth(String email, String senha, final Atleta a){

        pb.setTitle("Acessando Dados ...");
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        insertFirestore(a);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void insertFirestore(Atleta a ) {

        bd = FirebaseFirestore.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        Map<String, Object> dados = new HashMap<>();
        dados.put("id", id);
        dados.put("atleta", a);

        pb.setTitle("Falta Pouco");
        bd.collection("Dados").document(id).set(dados)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pb.setTitle("Concluido!");
                        startActivity(new Intent(MainActivity.this, Main2Activity.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
*/
}