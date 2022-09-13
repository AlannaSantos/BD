package com.example.bd.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bd.data.Produto;

public class DeleteDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private OnDeleteListener listener;
    private Produto produto;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja excluir o produto?");
        builder.setPositiveButton("Sim", this);
        builder.setNegativeButton("Nao", this);
        return builder.create();
    }

    public void setProduto(Produto produto){
        this.produto =  produto;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE){
            listener.onDelete(produto);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDeleteListener){
            listener = (OnDeleteListener) context;
        }else{
            throw new RuntimeException(context.toString() + "erro");
        }
    }

    public interface OnDeleteListener{
         void onDelete(Produto produto);
    }

}
