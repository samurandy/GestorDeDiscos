package com.example.chv.gestordediscos.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chv.gestordediscos.Database.OperationsDB;
import com.example.chv.gestordediscos.Model.Pelicula;
import com.example.chv.gestordediscos.R;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    ImageView imagenPelicula;
    TextView tituloDetalle;
    TextView valoracionDetalle;
    TextView descripcionPelicula;
    TextView duracionPelicula;
    Pelicula mItem;
    OperationsDB operationsDB;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String POSITION = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        operationsDB = new OperationsDB(getContext());
        operationsDB.open();

        if (getArguments().containsKey(POSITION)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            mItem = operationsDB.getPelicula((long) (int) getArguments().getInt(POSITION));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTitulo());


            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        if (mItem != null) {
            tituloDetalle = (TextView) rootView.findViewById(R.id.textView_titulo);
            tituloDetalle.setText(mItem.getTitulo());

            valoracionDetalle = (TextView) rootView.findViewById(R.id.textView_valoración);
            valoracionDetalle.setText(mItem.getValoracion());

            imagenPelicula = (ImageView) rootView.findViewById(R.id.imageView_pelicula);
            Picasso.get().load(mItem.getImagen()).fit().into(imagenPelicula);


            descripcionPelicula = (TextView) rootView.findViewById(R.id.descripcion_detail);
            descripcionPelicula.setText(mItem.getDescripcion());

            duracionPelicula = (TextView) rootView.findViewById(R.id.duracion_detail);
            duracionPelicula.setText(mItem.getDuracion());
        }

        return rootView;
    }
}
