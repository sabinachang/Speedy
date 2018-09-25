package com.enhan.sabina.speedy.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.enhan.sabina.speedy.R;
import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;

public class DetectTextTask extends AsyncTask<Bitmap,Void,String> {

    private DetectTextCallback mDetectTextCallback;

    public DetectTextTask(DetectTextCallback callback) {
        mDetectTextCallback = callback;
    }

    @Override
    protected String doInBackground(Bitmap... bitmaps) {
        final String[] detectedText = new String[1];
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmaps[0]);
        FirebaseVisionDocumentTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getCloudDocumentTextRecognizer();

        textRecognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionDocumentText>() {
                    @Override
                    public void onSuccess(FirebaseVisionDocumentText firebaseVisionText) {
                        detectedText[0] = firebaseVisionText.getText();


//                        for (FirebaseVisionDocumentText.Block block: firebaseVisionText.getBlocks()) {
//                            String blockText = block.getText();
//                            Float blockConfidence = block.getConfidence();
//                            List<RecognizedLanguage> blockRecognizedLanguages = block.getRecognizedLanguages();
//                            Rect blockFrame = block.getBoundingBox();
//                            for (FirebaseVisionDocumentText.Paragraph paragraph: block.getParagraphs()) {
//                                String paragraphText = paragraph.getText();
//                                Float paragraphConfidence = paragraph.getConfidence();
//                                List<RecognizedLanguage> paragraphRecognizedLanguages = paragraph.getRecognizedLanguages();
//                                Rect paragraphFrame = paragraph.getBoundingBox();
//                                for (FirebaseVisionDocumentText.Word word: paragraph.getWords()) {
//                                    String wordText = word.getText();

//                                    Float wordConfidence = word.getConfidence();
//                                    List<RecognizedLanguage> wordRecognizedLanguages = word.getRecognizedLanguages();
//                                    Rect wordFrame = word.getBoundingBox();
//                                    for (Symbol symbol: word.getSymbols()) {
//                                        String symbolText = symbol.getText();
//                                        Float symbolConfidence = symbol.getConfidence();
//                                        List<RecognizedLanguage> symbolRecognizedLanguages = symbol.getRecognizedLanguages();
//                                        Rect symbolFrame = symbol.getBoundingBox();
//                                    }
//                                }
//                            }
//                        }
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("HomeActivity","failed");

                            }
                        }
                );

        return detectedText[0];
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            mDetectTextCallback.onDetectSuccessful(s);
        } else {
            mDetectTextCallback.onDetectFailed();
        }

    }
}
