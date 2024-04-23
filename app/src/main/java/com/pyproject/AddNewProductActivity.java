package com.pyproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNewProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] specifications = new String[]{
                "Battery Life - Up to 22 hours video playback",
                "CPU - Exynos 2100 (International) / Qualcomm Snapdragon 888 (USA)",
                "Display - 6.8-inch Dynamic AMOLED 2X, 120Hz, HDR10+",
                "GPU - Mali-G78 MP14 (International) / Adreno 660 (USA)",
                "Operating System - Android 11, upgradable to Android 12",
                "Storage Options - 128GB, 256GB, 512GB",
                "Other"
        };
        final boolean[] checkedItems = new boolean[specifications.length];


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Specifications");

// when an item is clicked, store its checked status in the checkedItems array
        builder.setMultiChoiceItems(specifications, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked;
            }
        });

// when the "OK" button is clicked, update your UI based on the selected items
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    boolean checked = checkedItems[i];
                    if (checked) {
                        if (specifications[i].equals("Other")) {
                            // if "Other" is selected, show a dialog to input custom specification
                            AlertDialog.Builder otherBuilder = new AlertDialog.Builder(AddNewProductActivity.this);
                            otherBuilder.setTitle("Enter specification");

                            final EditText input = new EditText(AddNewProductActivity.this);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            input.setLayoutParams(lp);
                            otherBuilder.setView(input);

                            otherBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String customSpecification = input.getText().toString();
                                            // add customSpecification to your specs or do something with it
                                        }
                                    });
                            otherBuilder.show();
                        } else {
                            // do something with the selected specification
                        }
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}