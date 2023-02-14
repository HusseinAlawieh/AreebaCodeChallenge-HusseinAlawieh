package com.example.areebacodechallenge.ui.englishNews;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.areebacodechallenge.R;
import com.example.areebacodechallenge.article;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class articleDetailsActivity  extends AppCompatActivity {
    article thisArticle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent().getExtras() != null) {
            thisArticle = (article) getIntent().getSerializableExtra("thisArticle");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar); //here toolbar is your id in xml
        setSupportActionBar(toolbar);
        TextView toolbarText=findViewById(R.id.toolbar_title);
        TextView title=findViewById(R.id.title);
        TextView date=findViewById(R.id.date);
        TextView text=findViewById(R.id.articleText);
        ImageView image=findViewById(R.id.image);

        toolbarText.setText(thisArticle.getSectionName());
        title.setText(thisArticle.getWebTitle());
        date.setText(thisArticle.getWebPublicationDate());
        String webUrl=thisArticle.getWebUrl();

        try {
            Document doc = Jsoup.connect(webUrl).get();
            Elements elements = doc.select("img");
            String imageUrl = elements.first().absUrl("src");
            Picasso.get().load(imageUrl).into(image);
        } catch (Exception e) {
            e.printStackTrace();
            //in case no image found in webUrl
            image.setImageDrawable(image.getResources().getDrawable(R.drawable.news));
        }

        try {
            Document doc = Jsoup.connect(webUrl).get();
            Element body = doc.body();
            Elements articleTexts = body.getElementsByTag("article");

            for (Element article : articleTexts) {
                Element p = article.getElementsByTag("p").first();
                text.append(p.text());
            }

//                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            text.append("Check the website for more info");
            text.append(thisArticle.getWebUrl());
        }

    }

}
