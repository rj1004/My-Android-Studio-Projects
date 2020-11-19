package com.rahulcompany.instapostsaver;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {

    String image;

    public ImageFragment(String image) {
        this.image = image;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_image, container, false);
        ImageView iv = v.findViewById(R.id.imagepreview);
        try {
            Glide.with(getContext()).load(new URL(image)).into(iv);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return v;
    }


}
