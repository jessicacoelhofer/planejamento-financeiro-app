package com.example.aplicativo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastre_se extends AppCompatActivity implements View.OnClickListener {

    EditText txtCADNome, txtCADSobrenome, txtCADNascimento, txtCADEmail, txtCADSenha1, txtCADSenha2;
    Button btCADSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastre_se);

        txtCADNome = (EditText) findViewById(R.id.txtCADNome);
        txtCADSobrenome = (EditText) findViewById(R.id.txtCADSobrenome);
        txtCADNascimento = (EditText) findViewById(R.id.txtCADNascimento);
        txtCADEmail = (EditText) findViewById(R.id.txtCADEmail);
        txtCADSenha1 = (EditText) findViewById(R.id.txtCADSenha1);
        txtCADSenha2 = (EditText) findViewById(R.id.txtCADSenha2);
        btCADSalvar = (Button) findViewById(R.id.btCADSalvar);

        btCADSalvar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (validaDados()==true){
            SalvarDados();
        }
    }

    public boolean validaDados() {
        boolean retorno = true;
        String msg = "";
        if (txtCADNome.getText().length()==0){
            msg = "O campo NOME deve ser preenchido";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if (txtCADSobrenome.getText().length()==0){
            msg = "O campo SOBRENOME deve ser preenchido";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }

        if (txtCADNascimento.getText().length()==0) {
            msg = "O campo NASCIMENTO deve ser preenchido";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }

        if (txtCADEmail.getText().length()==0){
            msg = "O campo EMAIL deve ser preenchido";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }

        if (txtCADSenha1.getText().length()==0 || txtCADSenha2.getText().length()==0 ){
            msg = "Os campos de SENHA devem ser preenchidos";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }

        if (!txtCADSenha1.getText().toString().equals(txtCADSenha2.getText().toString())){
            msg = "Os campos de SENHA e CONFIRMA SENHA devem ser iguais";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }
        return retorno;
    }

    public void SalvarDados() {
        String nome = txtCADNome.getText().toString();
        String sobrenome = txtCADSobrenome.getText().toString();
        String nascimento = txtCADNascimento.getText().toString();
        String email = txtCADEmail.getText().toString();
        String senha = txtCADSenha1.getText().toString();

        BancoController bd = new BancoController(getBaseContext());
        String resultado;

        resultado = bd.insereDadosUsuario(nome, sobrenome, nascimento, email, senha);

        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
        limpar();
    }

    public void limpar() {
        txtCADNome.setText("") ;
        txtCADSobrenome.setText("") ;
        txtCADNascimento.setText("") ;
        txtCADEmail.setText("") ;
        txtCADSenha1.setText("");
        txtCADSenha2.setText("");
    }
}