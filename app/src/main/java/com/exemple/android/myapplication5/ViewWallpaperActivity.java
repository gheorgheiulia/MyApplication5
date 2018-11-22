package com.exemple.android.myapplication5;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.exemple.android.myapplication5.Helper.SaveImageHelper;
import com.exemple.android.myapplication5.Utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.UUID;

public class ViewWallpaperActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fabDownload,fabWallpaper;
    ImageView i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };


        initialize();

        Picasso.get().load(Utils.selected_wallpaper.getImageLink()).into(i1);
        fabWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.get().load(Utils.selected_wallpaper.getImageLink()).into(target);

            }
        });

        fabDownload.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){

                String fileName = UUID.randomUUID().toString() + "png";

                AlertDialog.Builder b = new AlertDialog.Builder(ViewWallpaperActivity.this);
                b.setMessage("Downloading...");
                AlertDialog alertDialog = b.create();
                alertDialog.show();


                Picasso.get().load(Utils.selected_wallpaper.getImageLink()).into(new SaveImageHelper(ViewWallpaperActivity.this, alertDialog, getApplicationContext().getContentResolver(), fileName ,"image_desc"));

            }

        });
    }

    private void initialize (){
        i1= (ImageView)findViewById(R.id.thumbImage);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingTollBarLayout);
        fabDownload = (FloatingActionButton)findViewById(R.id.fab_download);
        fabWallpaper = (FloatingActionButton)findViewById(R.id.fab_wallpaper);
        collapsingToolbarLayout.setTitle(Utils.CATEGORY_SELECTED);


        Picasso.get().load(Utils.selected_wallpaper.getImageLink()).into(i1);
        fabWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }
}
