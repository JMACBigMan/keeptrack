package jmac.keeptrack.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {
    private EditText foodName, foodCals;
    private Button submitButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dba = new DatabaseHandler(MainActivity.this);

        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCals = (EditText) findViewById(R.id.caloriesEditText);
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToDB();

            }
        });
    }

    private void saveDataToDB() {

        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calsString = foodCals.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(calsString)) {

            Toast.makeText(getApplicationContext(), "No empty fields allowed", Toast.LENGTH_LONG).show();

        } else {

            int cals = Integer.parseInt(calsString);

            food.setFoodName(name);
            food.setCalories(cals);

            long result = dba.addFood(food);
            dba.close();

            foodName.setText("");
            foodCals.setText("");

            if(result!=-1)
                startActivity(new Intent(MainActivity.this, DisplayFoodsActivity.class));
            else
                Toast.makeText(this, "Oops! Could not save your Record", Toast.LENGTH_SHORT).show();
        }


    }


    }


