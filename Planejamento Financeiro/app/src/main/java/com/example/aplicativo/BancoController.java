package com.example.aplicativo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context) {
        banco = new CriaBanco(context);
    }

    public Cursor carregaDadosPeloEmailSenha(String email, String senha) {
        Cursor cursor;
        String[] campos = {"idUser", "nome", "sobrenome", "nascimento", "email", "senha"};
        String where = "email = '" + email + "' and senha = '" + senha + "' ";
        db = banco.getReadableDatabase();
        cursor = db.query("usuarios", campos, where, null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String insereDadosUsuario(String nome, String sobrenome, String nascimento, String email, String senha) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("sobrenome", sobrenome);
        valores.put("nascimento", nascimento);
        valores.put("email", email);
        valores.put("senha", senha);

        resultado = db.insert("usuarios", null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir os dados do usuário!";
        else
            return "Dados cadastrados com sucesso!";
    }

    public String insereTransacao(String descricao, String valor, String data, String tipo) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("descricao", descricao);
        valores.put("valor", valor);
        valores.put("data", data);
        valores.put("tipo", tipo);

        resultado = db.insert("adicionar", null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir transação!";
        else
            return "Transação inserida com sucesso!";
    }

    public List<Transacao> getTransacoes() {
        List<Transacao> transacoes = new ArrayList<>();

        // Abrir o banco de dados em modo de leitura
        SQLiteDatabase db = banco.getReadableDatabase();

        // Definir as colunas que queremos recuperar (todas, neste caso)
        String[] projection = {
                "descricao",
                "valor",
                "data",
                "tipo"
        };

        // Executar a consulta
        Cursor cursor = db.query(
                "adicionar",         // Tabela
                projection,          // Colunas a serem retornadas
                null,                // Cláusula WHERE (vazia, para retornar todas as linhas)
                null,                // Argumentos da cláusula WHERE
                null,                // GROUP BY
                null,                // HAVING
                null                 // ORDER BY
        );

        // Processar o resultado do cursor e criar objetos Transacao
        while (cursor.moveToNext()) {
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
            String valor = cursor.getString(cursor.getColumnIndexOrThrow("valor"));
            String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));

            // Criar um objeto Transacao e adicionar à lista
            Transacao transacao = new Transacao(descricao, valor, data, tipo);
            transacoes.add(transacao);
        }

        // Fechar o cursor e o banco de dados
        cursor.close();
        db.close();

        // Retornar a lista de transações
        return transacoes;
    }

    public double calcularSaldo() {
        double saldo = 0.0;

        // Carregar dados do banco de dados
        List<Transacao> transacoes = getTransacoes();

        // Iterar sobre cada transação e calcular o saldo
        for (Transacao transacao : transacoes) {
            if (transacao.getTipo().equals("Entrada")) {
                // Se a transação for de receita, adicionar o valor ao saldo
                saldo += Double.parseDouble(transacao.getValor());
            } else if (transacao.getTipo().equals("Saída")) {
                // Se a transação for de despesa, subtrair o valor do saldo
                saldo -= Double.parseDouble(transacao.getValor());
            }
        }

        // Retornar o saldo calculado
        return saldo;
    }
}

