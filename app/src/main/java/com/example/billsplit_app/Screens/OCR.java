package com.example.billsplit_app.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOverlay;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.billsplit_app.Dish;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OCR extends AppCompatActivity {
    private PreviewView previewView;

    private final int REQUEST_CODE_PERMISSIONS = 10;
    private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private ImageCapture imageCapture;
    private ProcessCameraProvider cameraProvider;
    private boolean photoTaken = false;
    static String clipText = "Text copied to clipboard.";
    static String copiedText = "";
    static ArrayList<String> copiedTexts = new ArrayList<>();
    static ArrayList<Dish> tempDishes = new ArrayList<>();

    private ImageButton cameraCaptureButton;
    private Button cameraRetakeButton;
    private Button cameraCopyButton;

    Activity activity = OCR.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocr_layout);
        previewView = findViewById(R.id.view_finder);
        cameraCaptureButton = findViewById(R.id.camera_capture_button);
        cameraRetakeButton = findViewById(R.id.camera_retake_button);
        cameraCopyButton = findViewById(R.id.camera_copy_button);

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        cameraCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                photoTaken = true;
            }
        });
        cameraRetakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copiedTexts.clear();
                ViewOverlay overlay = previewView.getOverlay();
                overlay.clear();
                cameraProvider.unbindAll();
                startCamera();
                photoTaken = false;
                cameraRetakeButton.animate().translationXBy(250f);
                cameraCopyButton.setVisibility(View.GONE);
                cameraCopyButton.animate().translationXBy(-250f);
                cameraRetakeButton.setVisibility(View.GONE);
            }
        });

        cameraCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(tempDishes.size());
                for (Dish d : tempDishes) {
                    System.out.println("Name: " + d.getName() + ", price: " + d.getPrice());
                    MainActivity.dishList.add(d);
                    MainActivity.doStuff();
                }
                IndividualBillScreen.ItemViewAdapter.notifyDataSetChanged();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(clipText, String.join("\n", copiedTexts));
                clipboard.setPrimaryClip(clip);
                alertPopup(OCR.this);
                finish();
            }
        });
    }

    private boolean allPermissionsGranted() {
        for (int i = 0; i < REQUIRED_PERMISSIONS.length; ++i) {
            if (ContextCompat.checkSelfPermission(this, REQUIRED_PERMISSIONS[i]) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private void startCamera() {
        cameraCaptureButton.setVisibility(View.VISIBLE);
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                try {
                    cameraProvider.unbindAll();
                    imageCapture = new ImageCapture.Builder().build();
                    cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture);
                } catch (Exception e) {
                    Toast.makeText(this, "Binding failed", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Camera failed", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (imageCapture == null) {
            return;
        }
        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                super.onCaptureSuccess(image);
                Toast.makeText(OCR.this, "Capture succeeded! ", Toast.LENGTH_SHORT).show();
                runTextRecognition(image);
                image.close();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                super.onError(exception);
                Toast.makeText(OCR.this, "Capture failed! " + exception, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void runTextRecognition(ImageProxy imageProxy) {
        @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"}) Image mediaImage = imageProxy.getImage();
        if (mediaImage == null) {
            return;
        }
        InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
        TextRecognizer recognizer = TextRecognition.getClient();
        recognizer.process(image).addOnSuccessListener((text) -> {
            List<Text.TextBlock> blocks = text.getTextBlocks();
            cameraProvider.unbindAll();
            if (blocks.size() == 0) {
                Toast.makeText(OCR.this, "No text", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(OCR.this, "Found: " + blocks.size(), Toast.LENGTH_SHORT).show();
            }
            float width_ratio = (float) previewView.getWidth() / (float) image.getWidth();
            float height_ratio = (float) previewView.getHeight() / (float) image.getHeight();
            ViewOverlay overlay = previewView.getOverlay();
            overlay.clear();
            for (Text.TextBlock block : text.getTextBlocks()) {
                String blockText = block.getText();
                copiedTexts.add(blockText);
                Rect blockFrame = block.getBoundingBox();
                GradientDrawable drawable = new GradientDrawable();
                drawable.setBounds((int) (blockFrame.left * width_ratio), (int) (blockFrame.top * height_ratio), (int) (blockFrame.right * width_ratio), (int) (blockFrame.bottom * height_ratio));
                drawable.setStroke(5, Color.WHITE);
                overlay.add(drawable);
                cameraRetakeButton.animate().translationXBy(-250f);
                cameraCopyButton.animate().translationXBy(250f);
                cameraCaptureButton.setVisibility(View.GONE);
                cameraRetakeButton.setVisibility(View.VISIBLE);
                cameraCopyButton.setVisibility(View.VISIBLE);
                copiedText = blockText;
            }
            tempDishes.clear();
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> prices = new ArrayList<>();
            for (int i = 0; i < copiedTexts.size(); i++) {
                String str1 = copiedTexts.get(i);
                if (str1.startsWith("$") || Character.isDigit(str1.charAt(0))) {
                    prices.add(str1.substring(1));
                } else {
                    names.add(str1);
                }
            }
            System.out.println(prices.size());
            System.out.println(names.size());
            for (int i = 0; i < names.size(); i++) {
                tempDishes.add(new Dish(names.get(i), prices.get(i)));
            }
        });
    }

    private void alertPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Success!")
                .setMessage(clipText)
                .setPositiveButton("OK", null)
                .show();
    }
}