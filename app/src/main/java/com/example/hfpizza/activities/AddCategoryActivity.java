package com.example.hfpizza.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContentResolverCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hfpizza.R;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Category;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCategoryActivity extends AppCompatActivity {

    private CircleImageView foodCategoryImageView;
    private EditText foodCategoryNameET;
    private Button saveCategoryButton;
    private Uri imageUri;
    private static final int GALLERY_REQUEST_CODE = 1;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        fieldsInitialization();

        foodCategoryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });

        saveCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryNameString = foodCategoryNameET.getText().toString().trim();
                if (!TextUtils.isEmpty(categoryNameString)) {
                    if (imageUri != null) {
                        try {
                            saveToDatabase(categoryNameString, imageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(AddCategoryActivity.this, "Please add a photo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddCategoryActivity.this, "Please type category name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fieldsInitialization() {
        foodCategoryImageView = findViewById(R.id.addCategoryActivityCircleImageViewId);
        foodCategoryNameET = findViewById(R.id.addCategoryActivityEnterNameEditTextId);
        saveCategoryButton = findViewById(R.id.addCategoryActivitySaveCategoryButtonId);
        databaseHandler = new DatabaseHandler(this);
    }

    private void saveToDatabase(String categoryNameString, Uri imageUri) throws IOException {
        Category category = new Category();
        category.setCategoryName(categoryNameString);
//        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.toString());
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] bytesImage = byteArrayOutputStream.toByteArray();
        category.setCategoryImage(bytesImage);
        databaseHandler.addCategory(category);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            imageUri = data.getData();
            foodCategoryImageView.setImageURI(imageUri);
        }
    }
}