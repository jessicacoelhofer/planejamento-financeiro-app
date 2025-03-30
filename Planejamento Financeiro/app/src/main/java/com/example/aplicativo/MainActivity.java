package com.example.aplicativo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtLOGEmail, txtLOGSenha;
    Button btLOGEntrar, btLOGCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLOGEmail = (EditText) findViewById(R.id.txtLOGEmail);
        txtLOGSenha = (EditText) findViewById(R.id.txtLOGSenha);
        btLOGEntrar = (Button) findViewById(R.id.btLOGEntrar);
        btLOGCadastro = (Button) findViewById(R.id.btLOGCadastro);

        btLOGEntrar.setOnClickListener(this);
        btLOGCadastro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btLOGCadastro){
            Intent telaCadastro = new Intent(this,Cadastre_se.class);
            startActivity(telaCadastro);
        }
        if (view.getId() == R.id.btLOGEntrar){
            if (validaDados()) {
                Intent telaConsulta = new Intent(this,Consulta.class);
                startActivity(telaConsulta);
            }
        }
}

    public boolean validaDados() {
        boolean retorno = true;
        String msg= "";
        if (txtLOGEmail.getText().length() == 0 ){
            msg = "Por favor, digite o seu e-mail!";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }else{
            if (txtLOGSenha.getText().length() == 0) {
                msg = "Por favor, digite a sua senha!";
                Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
                retorno = false;
            }
            else{BancoController bd = new BancoController(getBaseContext());

                Cursor dados = bd.carregaDadosPeloEmailSenha(txtLOGEmail.getText().toString() , txtLOGSenha.getText().toString()) ;

                if(dados.moveToFirst()){
                    msg = "Email / Senha encontrado";
                }
                else{
                    msg= "E-mail ou senha inválidos!";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    retorno = false;
                }
            }
        }
        return retorno;
    }
    }