package com.example.duyda.onlinesaleshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.duyda.onlinesaleshop.Interface.ItemClickListener;
import com.example.duyda.onlinesaleshop.Models.Product;
import com.example.duyda.onlinesaleshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    TextView product_name, product_price, product_description;
    ImageView product_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton btnNumber;

    String productId = "";

    FirebaseDatabase database;

    DatabaseReference product;

    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        database = FirebaseDatabase.getInstance();
        product = database.getReference("Product");

        btnNumber = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);

        product_description = findViewById(R.id.product_description);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);

        product_image = findViewById(R.id.img_product);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapedAppbar);

        if (getIntent() != null)
            productId = getIntent().getStringExtra("ProductId");
        if (!productId.isEmpty())
            loadProductDetail(productId);

    }



    private void loadProductDetail(final String productId) {
        Log.d("ZUYD",""+productId.toString());
        product.child(productId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                Picasso.with(getBaseContext()).load(product.getImage()).into(product_image);
                collapsingToolbarLayout.setTitle(product.getName());
                product_price.setText(product.getPrice());
                product_name.setText(product.getName());
                product_description.setText(product.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
