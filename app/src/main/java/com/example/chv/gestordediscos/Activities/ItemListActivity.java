package com.example.chv.gestordediscos.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chv.gestordediscos.Database.OperationsDB;
import com.example.chv.gestordediscos.Model.Pelicula;
import com.example.chv.gestordediscos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An activity representing a thingList of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a thingList of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the thingList of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements PeliculaDialog.OnSeleccionDialogListener{
    public static ArrayList<Pelicula> peliculas = new ArrayList<>();
    public PeliculasRecyclerViewAdapter peliculasRecyclerViewAdapter;
    public RecyclerView recyclerView;
    private ItemListActivity mParentActivity = this;
    Context context = this;
    private OperationsDB operationsDB;
    long currentVisiblePosition;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);



        if (savedInstanceState != null){
             // this variable should be static in class

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        //Creamos un objeto de operationsDB
        operationsDB = new OperationsDB(this);
        operationsDB.open();

        // no usar mejor isInLayout (fragment) en vez de findViewById(contenedor)?
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        //Instanciación + inflado del recycler y vinculación con adapter
        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        peliculas = operationsDB.getAllPeliculas();
        peliculasRecyclerViewAdapter = new PeliculasRecyclerViewAdapter(mParentActivity,context, peliculas, mTwoPane);
        recyclerView.setAdapter(peliculasRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Declaración e instanciación de ItemTouchHelper
        ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(peliculas, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                //operationsDB.updateDB());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) {
                    operationsDB.deletePelicula(peliculas.get(viewHolder.getAdapterPosition()));
                    peliculas.remove(viewHolder.getAdapterPosition());
                    ((RecyclerView) recyclerView).getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                }
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.RIGHT);
            }
        };

        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView((RecyclerView) recyclerView);

    }





    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pelicula, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iee1anadirPelicula:
                FragmentManager fManager = getSupportFragmentManager();
                PeliculaDialog sDialog = new PeliculaDialog();
                sDialog.show(fManager, "tagSimpleDialog");
                return true;
            default:
                return true;
        }

    }

    @Override
    public void onPeliculaInserted(String titulo, String descripcion, String urlImagen, String valoracion, String duracion) {
        operationsDB.createPelicula(titulo, descripcion, urlImagen, valoracion, duracion);
        Pelicula pelicula = operationsDB.getUltimaPelicula();
        peliculas.add(pelicula);
        ((RecyclerView)recyclerView).getAdapter().notifyItemInserted(peliculas.size() -1);
    }


    public static class PeliculasRecyclerViewAdapter extends RecyclerView.Adapter<PeliculasRecyclerViewAdapter.PeliculasViewHolder> {

        private final ItemListActivity mParentActivity;
        Context context;
        private ArrayList<Pelicula> mValues;
        private final boolean mTwoPane;


        public PeliculasRecyclerViewAdapter(ItemListActivity mParentActivity, Context context, ArrayList<Pelicula> peliculas, boolean mTwoPane) {
            this.mParentActivity = mParentActivity;
            this.mValues = peliculas;
            this.mTwoPane = mTwoPane;
            this.context = context;
        }


        public static class PeliculasViewHolder extends RecyclerView.ViewHolder {
            TextView tituloItem;
            ImageView imagenItem;
            TextView valoracionItem;
            Pelicula mItem;



            public PeliculasViewHolder(View view) {
                super(view);
                tituloItem = view.findViewById(R.id.titulo);
                imagenItem = view.findViewById(R.id.imagen);
                valoracionItem = view.findViewById(R.id.valoracion);
            }
        }

        @Override
        public PeliculasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Infla el xml, crea el objeto holder y asocia el objeto con el xml
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);

            return new PeliculasViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final PeliculasViewHolder holder, int position) {
            //Escribe datos en el objeto holder creado en el onCreate (pinta los elementos)
            //ItemListActivity.position = position;
            holder.mItem = mValues.get(position);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    holder.imagenItem.setImageResource(R.drawable.interrogacion);
                }

            });
            builder.build().load(mValues.get(position).getImagen()).into(holder.imagenItem);
            holder.tituloItem.setText(peliculas.get(position).getTitulo());
            holder.valoracionItem.setText(peliculas.get(position).getValoracion());
            holder.itemView.setOnClickListener(new View.OnClickListener() { //itemView es el item del recycler, que al asociarlo con onclicklistener conseguimos generar el evento de llamada
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(ItemDetailFragment.POSITION, (int) (long) holder.mItem.getId());
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(mParentActivity, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.POSITION, (int) (long) holder.mItem.getId());
                        mParentActivity.startActivity(intent);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            //Dice al holder cuantos elementos tiene que crear en el recycler
            return mValues.size();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        currentVisiblePosition = 0;
        currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(currentVisiblePosition);
            currentVisiblePosition = 0;
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }
*/
}


