package com.example.duyda.onlinesaleshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duyda.onlinesaleshop.Common.Common;
import com.example.duyda.onlinesaleshop.Interface.ItemClickListener;
import com.example.duyda.onlinesaleshop.Models.Category;
import com.example.duyda.onlinesaleshop.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;
    FirebaseRecyclerAdapter adapter;


    TextView txtName;

    RecyclerView recycler_product;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);


        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtName = headerView.findViewById(R.id.txtName);
        txtName.setText(Common.currentAccount.getName());

        recycler_product = findViewById(R.id.recycler_menu);
        recycler_product.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_product.setLayoutManager(layoutManager);

        loadProduct();

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void loadProduct(){

        Query query = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Category");

        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(query, Category.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @Override
            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_item, parent, false);

                return new MenuViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Category model) {
                holder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(holder.imageView);
                final Category clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        //return null;
                    }
                });
            }

        };

        recycler_product.setAdapter(adapter);
    }


//    private void loadProduct() {
//        FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.menu_item, MenuViewHolder.class, category) {
//            @Override
//            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
//                viewHolder.txtMenuName.setText(model.getName());
//                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
//                final Category clickItem = model;
//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(Home.this, "" + clickItem.getName(), Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        };
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_product) {
        } else if (id == R.id.nav_cart) {
        } else if (id == R.id.nav_order) {
        } else if (id == R.id.nav_SignOut) {
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
