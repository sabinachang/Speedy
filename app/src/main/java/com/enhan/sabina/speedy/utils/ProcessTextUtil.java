package com.enhan.sabina.speedy.utils;

import android.graphics.Rect;
import android.util.Log;
import android.widget.TextView;

import com.enhan.sabina.speedy.callbacks.DetectTextCallback;
import com.enhan.sabina.speedy.callbacks.ProcessTextCallback;
import com.enhan.sabina.speedy.data.DataSource;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;

public class ProcessTextUtil implements DetectTextCallback{

    private static final String TAG = "Process text";
    private ProcessTextCallback mProcessTextCallback;

    public ProcessTextUtil(ProcessTextCallback callback) {
        mProcessTextCallback = callback;

    }
    @Override
    public void onDetectSuccessful(FirebaseVisionDocumentText documentText) {

        mProcessTextCallback.processedText(documentText.getText());

        Log.d(TAG,"all= " + documentText.getText());

//        for (FirebaseVisionDocumentText.Block block : documentText.getBlocks()) {
//
//            Log.d(TAG,"Bc = " + block.getConfidence());
//            Rect rect = block.getBoundingBox();
//            Log.d(TAG,"Bounding "+ "height =" + rect.width() );
//            Log.d(TAG,"bt = " + block.getText());
//            Log.d(TAG,"bbt = " + block.getRecognizedBreak().getDetectedBreakType());
//            Log.d(TAG,"--------------------Paragraph--------------------------------");
//            for (FirebaseVisionDocumentText.Paragraph paragraph : block.getParagraphs()) {
//                Rect rec = paragraph.getBoundingBox();
//                Log.d(TAG,"pc = " + paragraph.getConfidence());
//                Log.d(TAG,"Bounding " +"height =" + rec.width() );
//                Log.d(TAG,"pt = " + paragraph.getText());
//                Log.d(TAG,"pbt = " + paragraph.getRecognizedBreak().getDetectedBreakType());
//                Log.d(TAG,"................................");
//            }
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
//        }
    }

    @Override
    public void onDetectFailed() {

    }

}
