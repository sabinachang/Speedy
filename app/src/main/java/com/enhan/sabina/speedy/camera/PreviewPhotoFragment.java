package com.enhan.sabina.speedy.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.PreviewPhotoCallback;
import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;
import com.enhan.sabina.speedy.data.DataRepository;
import com.enhan.sabina.speedy.utils.ImageUtils;

public class PreviewPhotoFragment extends Fragment implements PreviewPhotoContract.View{

    private static final String PATH_KEY = "path_key";
    private ImageView mPreviewImageView;
    private FrameLayout mAcceptBtn;
    private FrameLayout mDenyBtn;
    private PreviewPhotoContract.Presenter mPresenter;
    private Bitmap mBitmap;

    public PreviewPhotoFragment() {

    }

    public static PreviewPhotoFragment newInstance() {
        return  new PreviewPhotoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview_photo,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        mPresenter = new PreviewPhotoPresenter(this);
        mPreviewImageView = view.findViewById(R.id.image_preview);
        mAcceptBtn = view.findViewById(R.id.btn_accept);
        mDenyBtn = view.findViewById(R.id.btn_deny);
        mDenyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onPhotoDenied();
            }
        });

        mAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.storeImageForScan(mBitmap);
                mPresenter.onPhotoAccepted();
            }
        });

        String imagePath = getArguments().getString(PATH_KEY,null);
        mPresenter.compressImageForDisplay(imagePath);
    }

    @Override
    public void setPresenter(PreviewPhotoContract.Presenter presenter) {

    }

    @Override
    public void displayCompressedImage(Bitmap compressedImage) {
        mBitmap = compressedImage;
        mPreviewImageView.setImageBitmap(mBitmap);
    }
}
