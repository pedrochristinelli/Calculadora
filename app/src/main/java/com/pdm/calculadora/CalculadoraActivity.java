package com.pdm.calculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.pdm.calculadora.databinding.ActivityCalculadoraBinding;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.Arrays;
import java.util.List;

public class CalculadoraActivity extends AppCompatActivity {
    private ActivityCalculadoraBinding activityCalculadoraBiding;
    private String viewContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        activityCalculadoraBiding = ActivityCalculadoraBinding.inflate(getLayoutInflater());
        setContentView(activityCalculadoraBiding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contex_menu_calculadora, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.advancedItens){
            activityCalculadoraBiding.squareRootButton.setVisibility(View.VISIBLE);
            activityCalculadoraBiding.perCentButton.setVisibility(View.VISIBLE);
            activityCalculadoraBiding.powerButton.setVisibility(View.VISIBLE);
            return true;
        } else {
            activityCalculadoraBiding.squareRootButton.setVisibility(View.GONE);
            activityCalculadoraBiding.perCentButton.setVisibility(View.GONE);
            activityCalculadoraBiding.powerButton.setVisibility(View.GONE);
            return false;
        }
    }


    public void onClick(View v) {
        if (((Button) v).getText().toString().equals("%")){
            List<String> expressionFinal = Arrays.asList(viewContent.split(""));
            String newVewString = "";

            String number = "";
            int stoppedIndex = 0;
            for (int i = expressionFinal.size()-1; i >= 0 ; i--) {
                if (expressionFinal.get(i).matches("[-/*+√^]")){
                    stoppedIndex = i;
                    break;
                } else {
                    number = expressionFinal.get(i)+number;
                    expressionFinal.set(i, "");
                }
            }

            String percentedNumber = "";
            String expression = "";
            double percentage = 0.0;
            if (stoppedIndex != 0){
                for (int i = stoppedIndex-1; i >= 0 ; i--) {
                    if (expressionFinal.get(i).matches("[-/*+√^]")){
                        break;
                    } else {
                        percentedNumber = expressionFinal.get(i)+percentedNumber;
                    }
                }
                percentage = ((Double.valueOf(percentedNumber))*(Double.valueOf(number)/100));
            } else {
                percentage = (1*(Double.valueOf(number)/100));
            }

            for (String s : expressionFinal) {
                newVewString = newVewString+s;
            }

            if(percentage % 1 == 0){
                viewContent = newVewString+((int)percentage);
            } else {
                viewContent = newVewString+(percentage);
            }
            activityCalculadoraBiding.viewTextView.setText(viewContent);

            viewContent = viewContent.concat(expression);
        } else {
            viewContent = viewContent.concat(((Button) v).getText().toString());
        }
        activityCalculadoraBiding.viewTextView.setText(viewContent);
    }

    public void calculateTotal(View v){
        List<String> expressionFinal = Arrays.asList(viewContent.split(""));
        String expression = "";
        for (int i = 0; i < expressionFinal.size(); i++) {
            if (expressionFinal.get(i).equals("√")){
                expressionFinal.set(i, "sqrt(");
                for (int j = i; j < expressionFinal.size(); j++) {
                    if (expressionFinal.get(j).matches("[)]") && j == expressionFinal.size()-1){
                        expressionFinal.set(j, expressionFinal.get(j)+")");
                    } else if (j == expressionFinal.size()-1){
                        expressionFinal.set(j, expressionFinal.get(j)+")");
                    }
                }
            } else if (expressionFinal.get(i).equals("%")){

            }
        }

        for (String s : expressionFinal) {
            expression = expression+s;
        }

        Expression e = new Expression(expression);
        double total = e.calculate();

        if(total % 1 == 0){
            viewContent = ((int)total)+"";
        } else {
            viewContent = Double.toString(total);
        }
        activityCalculadoraBiding.viewTextView.setText(viewContent);
    }

    public void cleanTextView(View v){
        viewContent = "";
        activityCalculadoraBiding.viewTextView.setText(viewContent);
    }
}