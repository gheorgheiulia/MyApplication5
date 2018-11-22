package com.exemple.android.myapplication5;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exemple.android.myapplication5.CategoryViewHolder.WallpaperViewHolder;
import com.exemple.android.myapplication5.Model.WallpaperItem;
import com.exemple.android.myapplication5.Utils.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ListWallpaperActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Query query;

    FirebaseRecyclerOptions<WallpaperItem> options;
    FirebaseRecyclerAdapter<WallpaperItem, WallpaperViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wallpaper);
        recyclerView = (RecyclerView) findViewById(R.id.ryWallpaper);
        recyclerView.setHasFixedSize(true);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        query = FirebaseDatabase.getInstance().getReference("Backgrounds")
                .orderByChild("categoryId").equalTo(Utils.CATEGORY_ID);

        options = new FirebaseRecyclerOptions.Builder<WallpaperItem>()
                .setQuery(query, WallpaperItem.class)
                .build();


        adapter = new FirebaseRecyclerAdapter<WallpaperItem, WallpaperViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final WallpaperViewHolder holder, final int position, @NonNull final WallpaperItem model) {

                Picasso.get().load(model.getImageLink())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(model.getImageLink())
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(holder.imageView);

                            }
                        });

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Utils.CATEGORY_ID = adapter.getRef(position).getKey();
                        Utils.CATEGORY_SELECTED = model.categoryId;
                        Utils.selected_wallpaper = model;


                        Intent my = new Intent(getApplicationContext(),ViewWallpaperActivity.class);
                        startActivity (my);



                    }
                });

            }

            @NonNull
            @Override
            public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext())
                        .inflate (R.layout.layout_wallpaper_item,parent,false);

                int height = parent.getMeasuredHeight()/2;
                v.setMinimumHeight(height);

                return new WallpaperViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart(){
        super.onStart();
        if (adapter!=null);
        {
            adapter.startListening();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }

    }

    @Override
    protected void onDestroy(){
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop(){
        if(adapter!=null){
            adapter.stopListening();
            super.onStop();
        }
    }

}
