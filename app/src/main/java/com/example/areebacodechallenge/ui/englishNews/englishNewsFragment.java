package com.example.areebacodechallenge.ui.englishNews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.areebacodechallenge.article;
import com.example.areebacodechallenge.englishNewsAdapter;
import com.example.areebacodechallenge.services.getEnglishNews;
import com.example.areebacodechallenge.databinding.FragmentEnglishNewsBinding;

import java.util.ArrayList;

public class englishNewsFragment extends Fragment {

    private FragmentEnglishNewsBinding binding;
    public  ArrayList<article> articles=new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getEnglishNews getEnglishNews=new getEnglishNews();
        getEnglishNews.setConn();

        binding = FragmentEnglishNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView articlesListView = binding.articlesListView;
        articles = getEnglishNews.articles;

        englishNewsAdapter adapter = new englishNewsAdapter(root.getContext(), articles);
        articlesListView.setAdapter(adapter);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}