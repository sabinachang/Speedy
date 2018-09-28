package com.enhan.sabina.speedy.callbacks;

import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;

public interface DetectTextCallback {
    void onDetectSuccessful(FirebaseVisionDocumentText firebaseDocumentText);
    void onDetectFailed();
}
