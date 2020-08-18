package com.example.splashactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.splashactivity.Model.ProductList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductFragment extends Fragment {

    private View view;
    private RecyclerView productList;
    private DatabaseReference mDatabase;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_product, container, false);
        productList = (RecyclerView) view.findViewById(R.id.recyclerview_productlist);
        productList.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ProductList");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ProductList> option =
                new FirebaseRecyclerOptions.Builder<ProductList>()
                .setQuery(mDatabase, new SnapshotParser<ProductList>() {
                    @NonNull
                    @Override
                    public ProductList parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new ProductList(snapshot.child("image").getValue().toString(),
                                snapshot.child("title").getValue().toString(),
                                snapshot.child("price").getValue().toString());
                    }
                })
                .build();

        FirebaseRecyclerAdapter<ProductList, productViewHolder> adapter
                = new FirebaseRecyclerAdapter<ProductList, productViewHolder>
                (option) {
            @NonNull
            @Override
            public productViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productlist_layout, viewGroup, false);
                return new productViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final productViewHolder productViewHolder, int position, @NonNull ProductList productList) {
                productViewHolder.setTitle(productList.getTitle());
                productViewHolder.setPrice(productList.getPrice());
                productViewHolder.setImage(productList.getImage());

                productViewHolder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }


        };
        productList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class productViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout root;

        View mView;
        public productViewHolder(View itemView){
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            mView=itemView;
        }
        public void setTitle(String title){
            TextView product_title = (TextView)mView.findViewById(R.id.product_title);
            product_title.setText(title);
        }
        public void setPrice(String price){
            TextView product_price = (TextView)mView.findViewById(R.id.product_price);
            product_price.setText(price);
        }
        public void setImage(String image){
            ImageView product_image = (ImageView)mView.findViewById(R.id.product_image);
            Picasso.get().load(image).into(product_image);
        }
    }
}