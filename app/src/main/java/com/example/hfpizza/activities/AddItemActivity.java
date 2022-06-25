package com.example.hfpizza.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hfpizza.R;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Category;
import com.example.hfpizza.model.Item;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddItemActivity extends AppCompatActivity {

    private ArrayAdapter<String> categoryAdapter;
    private AutoCompleteTextView categoryAutoCompleteTV;
    private EditText itemNameET, itemDescriptionET, itemAmountET;
    private CheckBox doesHaveSizeCheckBox;
    private Button saveItemButton;
    private TextInputLayout itemAmountTextInputLayout;
    private CircleImageView itemCircleImageView;
    private Uri imageUri;
    private static final int GALLERY_REQUEST_CODE = 1;

    private DatabaseHandler databaseHandler;
    private ArrayList<Category> categories;
    Item itemToBeAdded;

    private BottomSheetDialog bottomSheetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        fieldsInitialization();

        populateCategoriesAutoCompleteTV();

        itemCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });

        doesHaveSizeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    openEnterSizeAmountsBottomSheetDialog();
                    itemAmountTextInputLayout.setVisibility(View.GONE);
                } else {
                    itemAmountTextInputLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveItemToDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fieldsInitialization() {
        itemNameET = findViewById(R.id.addItemActivityEnterNameEditTextId);
        itemDescriptionET = findViewById(R.id.addItemActivityEnterDescriptionEditTextId);
        itemAmountET = findViewById(R.id.addItemActivityEnterAmountEditTextId);
        itemAmountTextInputLayout = findViewById(R.id.addItemActivityAmountTextInputLayoutId);
        doesHaveSizeCheckBox = findViewById(R.id.addItemActivityHaveSizeCheckBoxId);
        categoryAutoCompleteTV = findViewById(R.id.addItemActivitySelectCategoryAutoCompleteTextViewId);
        saveItemButton = findViewById(R.id.addItemActivitySaveItemButtonId);
        itemCircleImageView = findViewById(R.id.addItemActivityProfilePictureCircleImageViewId);
        databaseHandler = new DatabaseHandler(this);
        categories = databaseHandler.getAllCategories();
        itemToBeAdded = new Item();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            imageUri = data.getData();
            itemCircleImageView.setImageURI(imageUri);
        }
    }

    private void populateCategoriesAutoCompleteTV() {
        ArrayList<String> categoriesName = new ArrayList<>();
        for (Category category : categories) {
            categoriesName.add(category.getCategoryName());
        }

        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriesName);
        categoryAutoCompleteTV.setAdapter(categoryAdapter);
    }

    private void openEnterSizeAmountsBottomSheetDialog() {
        View customBottomSheetDialog = LayoutInflater.from(this).inflate(R.layout.custom_size_amount_bottom_sheet_dialog, null);

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(customBottomSheetDialog);

        EditText regularSizeAmountET = customBottomSheetDialog.findViewById(R.id.customSizeAmountBottomSheetDialogRegularEditTextId);
        EditText mediumSizeAmountET = customBottomSheetDialog.findViewById(R.id.customSizeAmountBottomSheetDialogMediumEditTextId);
        EditText largeSizeAmountET = customBottomSheetDialog.findViewById(R.id.customSizeAmountBottomSheetDialogLargeEditTextId);
        Button saveAmountsButton = customBottomSheetDialog.findViewById(R.id.customSizeAmountBottomSheetDialogSaveItemButtonId);

        saveAmountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regularSizeAmountString = regularSizeAmountET.getText().toString().trim();
                String mediumSizeAmountString = mediumSizeAmountET.getText().toString().trim();
                String largeSizeAmountString = largeSizeAmountET.getText().toString().trim();
                if (!TextUtils.isEmpty(regularSizeAmountString) && !TextUtils.isEmpty(mediumSizeAmountString) &&
                        !TextUtils.isEmpty(largeSizeAmountString)) {
//                    Line No. 128
                    itemToBeAdded.setItemRegularPrice(Double.parseDouble(regularSizeAmountString));
                    itemToBeAdded.setItemMediumPrice(Double.parseDouble(mediumSizeAmountString));
                    itemToBeAdded.setItemLargePrice(Double.parseDouble(largeSizeAmountString));
                    bottomSheetDialog.dismiss();
                } else {
                    Toast.makeText(AddItemActivity.this, "Please enter amount to all", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bottomSheetDialog.show();
    }

    private void saveItemToDatabase() throws IOException {
        String itemNameString = itemNameET.getText().toString().trim();
        String itemDescriptionString = itemDescriptionET.getText().toString().trim();
        String itemSelectedCategoryString = categoryAutoCompleteTV.getText().toString().trim();
        String itemAmountString = itemAmountET.getText().toString().trim();

        if (!TextUtils.isEmpty(itemNameString) && !TextUtils.isEmpty(itemDescriptionString)
                && !TextUtils.isEmpty(itemSelectedCategoryString) && imageUri != null) {

//            String itemImageString = imageUri.toString().trim();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            byte[] bytesImage = byteArrayOutputStream.toByteArray();

            itemToBeAdded.setItemName(itemNameString);
            itemToBeAdded.setItemDescription(itemDescriptionString);
            itemToBeAdded.setItemImage(bytesImage);
            for (Category category : categories) {
                if (category.getCategoryName().compareTo(itemSelectedCategoryString) == 0) {
//                    Toast.makeText(AddItemActivity.this, "category = " + category.getCategoryName(),
//                            Toast.LENGTH_SHORT).show();
                    itemToBeAdded.setItemFromCategoryId(category.getCategoryId());
                }
            }

            if (doesHaveSizeCheckBox.isChecked()) {
                itemToBeAdded.setItemHaveSize(0);

            } else {
                itemToBeAdded.setItemHaveSize(1);
            }

            if (itemToBeAdded.getItemFromCategoryId() != 0 && (!TextUtils.isEmpty(itemAmountString) ||
                    (itemToBeAdded.getItemRegularPrice() != 0 && itemToBeAdded.getItemMediumPrice() != 0
                            && itemToBeAdded.getItemLargePrice() != 0))) {
                if (!TextUtils.isEmpty(itemAmountString)) {
                    itemToBeAdded.setItemPrice(Double.parseDouble(itemAmountString));
                }
                databaseHandler.addItem(itemToBeAdded);
                finish();
            } else {
                Toast.makeText(this, "Please enter the amount of item", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill all fields and choose image", Toast.LENGTH_SHORT).show();
        }
    }
}