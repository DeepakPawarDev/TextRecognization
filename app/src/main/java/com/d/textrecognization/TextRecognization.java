package com.d.textrecognization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.List;

public class TextRecognization extends AppCompatActivity {
    Task<FirebaseVisionText> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognization);
    }

    public void selectImage(View view) {


        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);

    }
    private void runTextRecognition() {
       // Bitmap bitmap=((ImageView)findViewById(R.id.imageView)).getDrawingCache();
        Bitmap bitmap = ((BitmapDrawable)((ImageView)findViewById(R.id.imageView)).getDrawable()).getBitmap();

        FirebaseVisionImage image =
                FirebaseVisionImage.fromBitmap(bitmap);


final StringBuilder getOnDeviceTextRecognizer=new StringBuilder();
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
         result=
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                // Task completed successfully
                                // ...
                                getOnDeviceTextRecognizer.append(firebaseVisionText.getText()+"\n");
                               System.out.println("************* "+firebaseVisionText.getText());
                                ((TextView)findViewById(R.id.textView2)).setText(firebaseVisionText.getText());
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                        System.out.println("************* "+e.toString());

                                    }
                                });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Uri  uri=data.getData();
        ((ImageView)findViewById(R.id.imageView)).setImageURI(uri);
        runTextRecognition();
    }
}
