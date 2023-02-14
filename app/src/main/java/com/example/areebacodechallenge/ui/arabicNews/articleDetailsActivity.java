package com.example.areebacodechallenge.ui.arabicNews;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.areebacodechallenge.R;
import com.example.areebacodechallenge.article;
import com.example.areebacodechallenge.translate_api;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText=findViewById(R.id.toolbar_title);
        TextView title=findViewById(R.id.title);
        TextView date=findViewById(R.id.date);
        TextView text=findViewById(R.id.articleText);
        ImageView image=findViewById(R.id.image);
        translateToArabic(toolbarText,thisArticle.getSectionName());
        translateToArabic(title,thisArticle.getWebTitle());
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
                translateToArabic(text,p.text());
            }

//                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            text.append("اضغط على الرابط للمزيد");
            text.append(thisArticle.getWebUrl());
        }

    }

    public void translateToArabic(TextView textView,String text){
        translate_api translate=new translate_api();
        translate.setOnTranslationCompleteListener(new translate_api.OnTranslationCompleteListener() {
            @Override
            public void onStartTranslation() {
                // here you can perform initial work before translated the text like displaying progress bar
            }

            @Override
            public void onCompleted(String text) {
                // "text" variable will give you the translated text
                textView.append(text);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        translate.execute(text,"en","ar");
    }


}
