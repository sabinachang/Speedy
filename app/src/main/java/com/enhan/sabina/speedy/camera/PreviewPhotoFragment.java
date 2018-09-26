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
import android.widget.ImageView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.PreviewPhotoCallback;
import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;
import com.enhan.sabina.speedy.utils.ImageUtils;

public class PreviewPhotoFragment extends Fragment implements PreviewPhotoContract.View{

    private ImageView mPreviewImageView;
    private Button mAcceptBtn;
    private Button mDenyBtn;
    private PreviewPhotoContract.Presenter mPresenter;

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
                mPresenter.onPhotoAccepted();
            }
        });

        String imagePath = mPresenter.providePath();
//        final Bitmap[] bitmap = new Bitmap[1];
//        previewImageView.post(new Runnable() {
//            @Override
//            public void run() {
//
//                bitmap[0] = ImageUtils.getCompressBitmap(getActivity(),uriData,view.getWidth());
//                Log.d("Preview",""+bitmap[0].getWidth());
//                Log.d("Preview","" + bitmap[0].getHeight());
//                mPreviewImageView.getWidth();
//                mPreviewImageView.setImageBitmap(bitmap[0]);
//            }
//        });
        mPreviewImageView.setImageBitmap(ImageUtils.getCompressBitmap(getActivity(),imagePath));
    }

    @Override
    public void setPresenter(PreviewPhotoContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
