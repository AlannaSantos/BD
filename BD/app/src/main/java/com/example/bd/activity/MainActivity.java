package com.example.bd.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bd.R;
import com.example.bd.adapter.ProdutoAdapter;
import com.example.bd.data.Produto;
import com.example.bd.data.ProdutoDAO;
import com.example.bd.dialog.DeleteDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, DeleteDialog.OnDeleteListener{

    private ListView lista;
    private ProdutoAdapter adapter;
    private ProdutoDAO produtoDAO;
    private static final int REQ_EDIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.lista);

        adapter = new ProdutoAdapter(this);
        lista.setAdapter(adapter);
        
        produtoDAO = ProdutoDAO.getInstance(this);
        updateList();

        lista.setOnItemClickListener(this); //editar

        lista.setOnItemLongClickListener(this); //excluir
    }

    private void updateList() {
        List<Produto> products = produtoDAO.list();
        adapter.setItems(products);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.actions, menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if (item.getItemId() == R.id.act_add){
           Intent intent = new Intent(this, EditarProdutoActivity.class);
           startActivityForResult(intent, REQ_EDIT);
       }
       return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_EDIT || requestCode == RESULT_OK){
            updateList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, EditarProdutoActivity.class);
        intent.putExtra("produto", adapter.getItem(i));
        startActivityForResult(intent, REQ_EDIT);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Produto produto = adapter.getItem(i);

        DeleteDialog dialog = new DeleteDialog();
        dialog.setProduto(produto);
        dialog.show(getSupportFragmentManager(), "deleteDialog");

        return true;
    }

    @Override
    public void onDelete(Produto produto) {
        adapter.remove(produto);
    }
}