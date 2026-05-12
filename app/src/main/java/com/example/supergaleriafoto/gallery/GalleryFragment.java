package com.example.supergaleriafoto.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supergaleriafoto.R;
import com.example.supergaleriafoto.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    // Grid cu 3 coloane
    private static final int SPAN_COUNT = 3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        binding = FragmentGalleryBinding.bind(view);
//
//        setupToolbar();
//        setupRecyclerView();
//    }
//
//    private void setupToolbar() {
//        // UI
//    }
//
//    private void setupRecyclerView() {
//        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), SPAN_COUNT);
//        binding.recyclerPhotos.setLayoutManager(layoutManager);
//
//        // TODO: adapter + date reale
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}