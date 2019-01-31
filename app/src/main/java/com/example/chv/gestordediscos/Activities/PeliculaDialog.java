package com.example.chv.gestordediscos.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chv.gestordediscos.R;


/**
 * Created by Pitxulas on 25/07/2017.
 */

public class PeliculaDialog extends DialogFragment {

    private OnSeleccionDialogListener listener;

    View view;
    EditText titulo, descripcion, urlImagen, valoracion, duracion;
    Button addPelicula;

    public interface  OnSeleccionDialogListener{
        public void onPeliculaInserted(String titulo, String precio, String urlImagen, String valoracion, String duracion);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (OnSeleccionDialogListener) context;
        } catch (ClassCastException e){}
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.new_pelicula_dialog, null);

        addPelicula = (Button) view.findViewById(R.id.addPelicula);
        addPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo = (EditText) view.getRootView().findViewById(R.id.etTitulo);
                descripcion = (EditText) view.getRootView().findViewById(R.id.etDescripcion);
                urlImagen = (EditText) view.getRootView().findViewById(R.id.eTurlImagenAlta);
                valoracion = (EditText) view.getRootView().findViewById(R.id.etValoracion);
                duracion = (EditText) view.getRootView().findViewById(R.id.etDuracion);

                boolean cerrar = true;
                if (titulo.getText().length() == 0){
                    cerrar = false;
                    titulo.setError(getResources().getText(R.string.errorObligatorio));
                } else if (descripcion.getText().length() == 0) {
                    cerrar = false;
                    descripcion.setError(getResources().getText(R.string.errorObligatorio));
                } else if (urlImagen.getText().length() == 0) {
                    cerrar = false;
                    urlImagen.setError(getResources().getText(R.string.errorObligatorio));
                } else if (valoracion.getText().length() == 0) {
                    cerrar = false;
                    valoracion.setError(getResources().getText(R.string.errorObligatorio));
                } else if (duracion.getText().length() == 0) {
                    cerrar = false;
                    duracion.setError(getResources().getText(R.string.errorObligatorio));
                }

                if (cerrar){
                    listener.onPeliculaInserted(titulo.getText().toString(), descripcion.getText().toString(), urlImagen.getText().toString(), valoracion.getText().toString(), duracion.getText().toString());
                    dismiss();
                }
            }
        });
        builder.setView(view);

        return builder.create();
    }

}
