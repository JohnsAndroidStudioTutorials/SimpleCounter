package com.johnsandroidstudiotutorials.simplecounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Counter extends AppCompatActivity {

    int count;
    TextView countValueDisplay;

    Button incrementButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    AlertDialog.Builder builder;
    EditText inputNewValue;
    EditText changeAmountInput;

    LinearLayout AlertDialogLayout;
    LinearLayout.LayoutParams layoutParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        defineValues();
        loadValues();
        setCount();
        buttonHandler();
    }

    public void defineValues() {
        countValueDisplay = (TextView) findViewById(R.id.count_value_display);
        incrementButton = (Button) findViewById(R.id.increment_button);
        sharedPreferences = getSharedPreferences("count", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        builder = new AlertDialog.Builder(this);
        inputNewValue = new EditText(this);
    }

    public void loadValues() {
        sharedPreferences = getSharedPreferences("count", Context.MODE_PRIVATE);
    }

    private void setCount() {
        count = sharedPreferences.getInt("count", 0);
        if (count == 0) {
            countValueDisplay.setText("counter");
        } else {
            countValueDisplay.setText(Integer.toString(count));
        }
    }

    public void buttonHandler() {
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 0) {
                    count = sharedPreferences.getInt("count", 0);
                    count += 1;
                    countValueDisplay.setText(Integer.toString(count));
                } else {
                    count += 1;
                    countValueDisplay.setText(Integer.toString(count));
                }
                commitToSharedPreferences();
            }
        });
    }

    public void commitToSharedPreferences() {
        editor.putInt("count", count);
        editor.commit();
    }

    public void createAlertDialog() {
        builder = new AlertDialog.Builder(this);

        AlertDialogLayout = new LinearLayout(this);
        layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        AlertDialogLayout.setOrientation(LinearLayout.VERTICAL);
        AlertDialogLayout.setLayoutParams(layoutParameters);

        AlertDialogLayout.setGravity(Gravity.CENTER);
        AlertDialogLayout.setPadding(2, 2, 2, 2);

        changeAmountInput = new EditText(this);
        changeAmountInput.setHint("example: 200");
        changeAmountInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialogLayout.addView(changeAmountInput, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        builder.setView(AlertDialogLayout);
        builder.setCancelable(true);

        // Setting Negative "Cancel" Button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        // Setting Positive "Yes" Button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                count = Integer.parseInt(changeAmountInput.getText().toString());
                countValueDisplay.setText(Integer.toString(count));
                commitToSharedPreferences();
            }
        });
        builder.setIcon(R.drawable.ic_info_black_24dp);
        builder.setTitle("Input the correct value");

        AlertDialog alertDialog = builder.create();

        try {
            alertDialog.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would
            // not display the 'Force Close' message
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_counter_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_value:
                createAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }
}