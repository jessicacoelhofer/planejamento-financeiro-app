package com.example.aplicativo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Consulta extends AppCompatActivity {

    private EditText searchBar;
    private Spinner monthFilter;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        exibirTransacoesNoScrollView();
        atualizarSaldoNaView();

        // Configuração da barra de pesquisa
        searchBar = findViewById(R.id.searchBar);

        // Configuração do Spinner para filtro por mês
        monthFilter = findViewById(R.id.monthFilter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.months_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthFilter.setAdapter(adapter);

        // Configuração do botão de adicionar lançamento
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            // Código para adicionar um novo lançamento
            Intent telaAdicionar = new Intent(this, Adicionar.class);
            startActivity(telaAdicionar);});

        // Listener para a barra de pesquisa (Exemplo de funcionalidade)
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString();
            // Código para tratar a pesquisa
            Toast.makeText(Consulta.this, "Pesquisando por: " + query, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void atualizarSaldoNaView() {
        
        BancoController bd = new BancoController(getBaseContext());
        TextView textViewSaldo = findViewById(R.id.textViewSaldo);

        // Calcular o saldo
        double saldo = bd.calcularSaldo();

        // Formatar o saldo como uma string (opcional)
        String saldoFormatado = String.format("%.2f", saldo);

        // Atualizar o texto do TextView com o saldo calculado
        textViewSaldo.setText("Saldo: R$" + saldoFormatado);

    }

    private void exibirTransacoesNoScrollView() {


        BancoController bd = new BancoController(getBaseContext());
        // Carregar dados do banco de dados
        List<Transacao> transacoes = bd.getTransacoes();

        // Referenciar o LinearLayout dentro do ScrollView
        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        // Para cada transação, criar uma visualização (TextView) e adicioná-la ao LinearLayout
        for (Transacao transacao : transacoes) {
            TextView textView = new TextView(this);
            textView.setText(transacao.toString());
            linearLayout.addView(textView);
        }
    }



}