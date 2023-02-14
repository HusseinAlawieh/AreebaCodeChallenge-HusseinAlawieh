package com.example.areebacodechallenge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.areebacodechallenge.ui.englishNews.articleDetailsActivity;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class englishNewsAdapter extends ArrayAdapter<article> {
    // constructor for our list view adapter.
    public englishNewsAdapter(@NonNull Context context, ArrayList<article> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_article, parent, false);
        }

        article dataModal = getItem(position);
        String webUrl = dataModal.getWebUrl();

        try {
            Document doc = Jsoup.connect(webUrl).get();
            Elements elements = doc.select("img");
            String imageUrl = elements.first().absUrl("src");
            ImageView imageView = listitemView.findViewById(R.id.articleImage);
            Picasso.get().load(imageUrl).into(imageView);
        } catch (Exception e) {
            //in case no image found in webUrl
            ImageView imageview = listitemView.findViewById(R.id.articleImage);
            imageview.setImageDrawable(imageview.getResources().getDrawable(R.drawable.news));
        }
        // initializing our UI components of list view item.

        TextView title = listitemView.findViewById(R.id.webTitle);
        TextView description = listitemView.findViewById(R.id.description);
        TextView webPublicationDate = listitemView.findViewById(R.id.webPublicationDate);
        title.setText(dataModal.getSectionName());
        description.setText(dataModal.getWebTitle());
        webPublicationDate.setText(dataModal.getWebPublicationDate());



        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                Intent intent = new Intent(getContext(), articleDetailsActivity.class);
                intent.putExtra("thisArticle",dataModal);
                getContext().startActivity(intent);

            }
        });

        return listitemView;
    }
}
