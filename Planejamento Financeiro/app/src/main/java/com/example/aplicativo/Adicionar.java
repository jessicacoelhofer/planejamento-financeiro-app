package com.example.aplicativo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Date;

public class Adicionar extends AppCompatActivity implements View.OnClickListener {

    EditText description, value, date;
    RadioGroup type;
    RadioButton radioButtonEntrada, radioButtonSaida;
    Button saveButton, inicioButton;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        description = findViewById(R.id.description);
        value = findViewById(R.id.value);
        date = findViewById(R.id.date);
        type = findViewById(R.id.type);
        radioButtonEntrada = findViewById(R.id.radioButtonEntrada);
        radioButtonSaida = findViewById(R.id.radioButtonSaida);
        saveButton = findViewById(R.id.saveButton);
        inicioButton = findViewById(R.id.inicioButton);

        saveButton.setOnClickListener(this);
        inicioButton.setOnClickListener(this);
        radioButtonEntrada.setOnClickListener(this);
        radioButtonSaida.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.saveButton) {
            if (validaDados()) {
                salvarDados();
            }
        }

        if (v.getId()==R.id.radioButtonEntrada || v.getId()==R.id.radioButtonSaida) {
            int selectedRadioButtonId = type.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            tipo = selectedRadioButton.getText().toString();
        }

        if (v.getId()==R.id.inicioButton) {
            Intent telaConsulta = new Intent(this, Consulta.class);
            startActivity(telaConsulta);}


    }

    private void salvarDados() {
            String descricao = description.getText().toString();
            String valor = value.getText().toString();
            String data = date.getText().toString();

            int selectedRadioButtonId = type.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            tipo = selectedRadioButton.getText().toString();

            BancoController bd = new BancoController(getBaseContext());
            String resultado;

            resultado = bd.insereTransacao(descricao, valor, data, tipo);

            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
            limpar();

    }

    public void limpar() {
        description.setText("") ;
        value.setText("") ;
        date.setText("") ;
        type.clearCheck();
    }

    private boolean validaDados() {
        boolean retorno = true;
        String msg= "";
        if (description.getText().length() == 0 ){
            msg = "Por favor, digite a descrição!";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;}
        if (value.getText().length() == 0 ){
            msg = "Por favor, digite o valor!";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;}
        if (date.getText().length() == 0 ){
            msg = "Por favor, digite a data!";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;}
        if (type.getCheckedRadioButtonId() == -1 ){
            msg = "Por favor, selecione o tipo da transação!";
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            retorno = false;}

        return retorno;
    }
}