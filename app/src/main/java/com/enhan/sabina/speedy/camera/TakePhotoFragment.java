package com.enhan.sabina.speedy.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.TakePhotoCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class TakePhotoFragment extends Fragment implements TakePhotoContract.View{

    private static final String TAG = "Take photo fragment" ;
    private static final int REQUEST_TAKE_PHOTO = 102;
    private CameraPreview mCameraPreview;
    private FrameLayout mCameraPreviewLayout;
    private TakePhotoCallback mTakePhotoCallback;
    private Camera mCamera;
    private Button mButton;
    private final int PICTURE_SIZE_MAX_WIDTH = 1280;
    private final int PREVIEW_SIZE_MAX_WIDTH = 640;
    private int mDisplayOrientation;
    private int mLayoutOrientation;
    private SurfaceHolder mHolder;
    private Uri mPhotoUri;
    private Uri mLocalUri;
    private TakePhotoContract.Presenter mPresenter;

    public TakePhotoFragment() {

    }

    public static TakePhotoFragment newInstance() {
        return  new TakePhotoFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof TakePhotoCallback)) {
            throw new IllegalArgumentException(
                    "Activity has to implement CameraFragmentListener interface"
            );
        }
        mTakePhotoCallback = (TakePhotoCallback) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_take_photo,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter.launchCamera();
//        mCameraPreviewLayout = view.findViewById(R.id.camera_preview);
//        mButton = view.findViewById(R.id.btn_capture);
//        mCameraPreview = new CameraPreview(getActivity());
//        mCameraPreview.getHolder().addCallback(this);
//        mCameraPreviewLayout.addView(mCameraPreview);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO :
                    if (null != mPhotoUri) {
                        mLocalUri = mPhotoUri;
                        mPresenter.onPhotoReceived(mLocalUri);

                    }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        mCamera = checkCameraHardware(getActivity());
//        Log.d(TAG,"in on view created camera");
//        Log.d(TAG,""+mCamera);
    }

    @Override
    public void onPause() {
        super.onPause();
//        stopCameraPreview();
//        mCamera.release();

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"on stop");
    }

    @Override
    public void setPresenter(TakePhotoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void startCameraIntent(Intent intent,Uri photoUri) {
        mPhotoUri = photoUri;
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

//
//    private synchronized void stopCameraPreview() {
//        try {
//            mCamera.stopPreview();
//        } catch (Exception exception) {
//            Log.i(TAG, "Exception during stopping camera preview");
//        }
//    }
//
//
//    private synchronized void startCameraPreview() {
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCamera.takePicture(null, null, pictureCallback);
//            }
//        });
//
//        determineDisplayOrientation();
//        setupCamera();
//        try {
//            mCamera.setPreviewDisplay(mHolder);
//            mCamera.startPreview();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Camera checkCameraHardware(Context context) {
//        Camera camera = null;
//        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//            try {
//                camera = Camera.open();
//
//            } catch (Exception e) {
//                Log.d("Camera Activity","Sth wrong");
//                e.printStackTrace();
//            }
//
//        }
//        return camera;
//    }
//    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
//        @Override
//        public void onPictureTaken(byte[] bytes, Camera camera) {
//            BitmapFactory.Options options = new BitmapFactory.Options();
////            options.inSampleSize = 2;
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            if (bitmap == null) {
//                Log.d("Camera Activty", "Empty image");
//                return;
//            }
//            int rotation = (mDisplayOrientation + mLayoutOrientation) % 360;
//            if (rotation != 0) {
//                Bitmap oldBitmap = bitmap;
//                Log.d(TAG,"height + " + bitmap.getHeight());
//                Log.d(TAG,"width + " + bitmap.getWidth());
//
//                Matrix matrix = new Matrix();
//                matrix.postRotate(rotation);
//
//                bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
//                oldBitmap.recycle();
//            }
//
////            mTakePhotoCallback.onPhotoTaken(bitmap);
//        }
//    };
//
//
//    private void determineDisplayOrientation() {
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        Camera.getCameraInfo(0, cameraInfo);
//
//        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
//        int degrees  = 0;
//
//        switch (rotation) {
//            case Surface.ROTATION_0:
//                degrees = 0;
//                break;
//
//            case Surface.ROTATION_90:
//                degrees = 90;
//                break;
//
//            case Surface.ROTATION_180:
//                degrees = 180;
//                break;
//
//            case Surface.ROTATION_270:
//                degrees = 270;
//                break;
//        }
//
//        int displayOrientation;
//
//        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//            displayOrientation = (cameraInfo.orientation + degrees) % 360;
//            displayOrientation = (360 - displayOrientation) % 360;
//        } else {
//            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
//        }
//
//        mDisplayOrientation = displayOrientation;
//        mLayoutOrientation = degrees;
//
//        mCamera.setDisplayOrientation(displayOrientation);
//
//    }
//
//    public void setupCamera() {
//        Camera.Parameters parameters = mCamera.getParameters();
//
//        Camera.Size bestPreviewSize = determineBestPreviewSize(parameters);
////        Camera.Size bestPictureSize = determineBestPictureSize(parameters);
//
//        parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
////        parameters.setPictureSize(bestPictureSize.width, bestPictureSize.height);
//
//        mCamera.setParameters(parameters);
//    }
//
//    private Camera.Size determineBestPreviewSize(Camera.Parameters parameters) {
//        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
//
//        return determineBestSize(sizes, PREVIEW_SIZE_MAX_WIDTH);
//    }
//
//    private Camera.Size determineBestPictureSize(Camera.Parameters parameters) {
//        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
//
//        return determineBestSize(sizes, PICTURE_SIZE_MAX_WIDTH);
//    }
//
//    protected Camera.Size determineBestSize(List<Camera.Size> sizes, int widthThreshold) {
//        Camera.Size bestSize = null;
//
//        for (Camera.Size currentSize : sizes) {
//            boolean isDesiredRatio = (currentSize.width / 4) == (currentSize.height / 3);
//            boolean isBetterSize = (bestSize == null || currentSize.width > bestSize.width);
//            boolean isInBounds = currentSize.width <= PICTURE_SIZE_MAX_WIDTH;
//
//            if (isDesiredRatio && isInBounds && isBetterSize) {
//                bestSize = currentSize;
//            }
//        }
//
//        if (bestSize == null) {
////            listener.onCameraError();
//
//            return sizes.get(0);
//        }
//
//        return bestSize;
//    }
//
//
//
//    @Override
//    public void surfaceCreated(SurfaceHolder surfaceHolder) {
////        mHolder = surfaceHolder;
////        startCameraPreview();
//
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//
//    }
}
