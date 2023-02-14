package com.example.areebacodechallenge.ui.arabicNews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.areebacodechallenge.arabicNewsAdapter;
import com.example.areebacodechallenge.article;
import com.example.areebacodechallenge.databinding.FragmentArabicNewsBinding;
import com.example.areebacodechallenge.services.getEnglishNews;

import java.util.ArrayList;

public class arabicNewsFragment extends Fragment {

    private FragmentArabicNewsBinding binding;
    public ArrayList<article> articles=new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getEnglishNews getEnglishNews=new getEnglishNews();
        getEnglishNews.setConn();

        binding = FragmentArabicNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView articlesListView = binding.articlesListView;
        articles = getEnglishNews.articles;

        arabicNewsAdapter adapter = new arabicNewsAdapter(root.getContext(), articles);
        articlesListView.setAdapter(adapter);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}