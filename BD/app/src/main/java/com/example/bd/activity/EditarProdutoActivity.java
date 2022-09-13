package com.example.bd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bd.R;
import com.example.bd.data.Produto;
import com.example.bd.data.ProdutoDAO;

public class EditarProdutoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNome;
    private EditText edtValor;
    private Button btnProcessasr;
    private Button btnCancelar;
    private ProdutoDAO produtoDAO;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produto);

        edtNome = findViewById(R.id.edtNome);
        edtValor = findViewById(R.id.edtValor);

        btnProcessasr = findViewById(R.id.btnProcessar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnProcessasr.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        
        produtoDAO = produtoDAO.getInstance(this);
        
        produto = (Produto) getIntent().getSerializableExtra("produto");

        if (produto != null){
            edtNome.setText(produto.getNome());
            edtValor.setText(String.valueOf(produto.getValor()));
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnProcessar){
            String nome = edtNome.getText().toString();
            double valor = Double.parseDouble(edtValor.getText().toString());
            String msg;

            if (produto == null){
                Produto produto = new Produto(nome, valor);
                produtoDAO.insert(produto);
                msg = "Produto gravado com id " + produto.getId();
            }
            else {
                produto.setNome(nome);
                produto.setValor(valor);
                produtoDAO.update(produto);
                msg = "Produto atualizado com id "+ produto.getId();
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
        else if (view.getId() == R.id.btnCancelar){
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}