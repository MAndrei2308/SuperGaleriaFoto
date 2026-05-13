package com.example.supergaleriafoto.gallery;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.Toast;

import com.example.supergaleriafoto.R;
import com.example.supergaleriafoto.databinding.FragmentGalleryBinding;
import com.example.supergaleriafoto.models.Photo;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private static final int REQUEST_READ_IMAGES = 1001;
    private FragmentGalleryBinding binding;
    private static final int SPAN_COUNT = 3;
    private PhotoGridAdapter photoGridAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentGalleryBinding.bind(view);

        setupRecyclerView();
        
        if(hasReadImagesPermission()) {
            loadPhotos();
        } else {
            requestReadImagesPermission();
        }
        
    }

    private void setupRecyclerView() {
        photoGridAdapter = new PhotoGridAdapter();

        binding.recyclerPhotos.setHasFixedSize(true);
        binding.recyclerPhotos.setLayoutManager(
                new GridLayoutManager(requireContext(), SPAN_COUNT)
        );
        binding.recyclerPhotos.setAdapter(photoGridAdapter);
    }

    private void requestReadImagesPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_READ_IMAGES);
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_IMAGES);
        }
    }

    private void loadPhotos() {
        new Thread(() -> {
            List<Photo> photoList = new ArrayList<>();

            ContentResolver contentResolver = requireContext().getContentResolver();
            Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] photoProjection = new String[] {
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_TAKEN,
                    MediaStore.Images.Media.SIZE
            };

            String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC";

            try(Cursor cursor = contentResolver.query(
                    collection,
                    photoProjection,
                    null,
                    null,
                    sortOrder
            )) {
                if(cursor != null) {
                    int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                    int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                    int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
                    int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

                    while(cursor.moveToNext()) {
                        long id = cursor.getLong(idColumn);
                        String name = cursor.getString(nameColumn);
                        long dateTaken = cursor.getLong(dateColumn);
                        long sizeBytes = cursor.getLong(sizeColumn);

                        Uri contentUri = Uri.withAppendedPath(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                String.valueOf(id)
                        );

                        photoList.add(new Photo(id, contentUri, name, dateTaken, sizeBytes));
                        //Log.i("Photos", "id: " + id + " contentUri: " + contentUri + " name: " + name + " dateTaken: " + dateTaken + " sizeBytes: " + sizeBytes);
                    }
                }
            }

            requireActivity().runOnUiThread(() -> {
                photoGridAdapter.submitList(photoList);
                Log.d("GalleryFragment", "Loaded " + photoList.size() + " photos");
            });
        }).start();
    }

    private boolean hasReadImagesPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return requireContext().checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else {
            return requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(hasReadImagesPermission()) {
            loadPhotos();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_READ_IMAGES) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadPhotos();
            } else {
                Toast toast = Toast.makeText(this.getContext(), "NUUUUU. ACEPTAAAAAA", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}