package com.amamparo.checkmate;

import android.os.Bundle;
import android.app.Activity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
        final EditText billAmount = (EditText)findViewById(R.id.etBillAmount);
        billAmount.setFilters(new InputFilter[] {
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    int beforeDecimal = 5, afterDecimal = 2;
                    @Override
                    public CharSequence filter(CharSequence source,int start,int end,Spanned dest,int dstart,int dend){
                        String temp = billAmount.getText() + source.toString();
                        if (temp.equals(".")) {
                            return "0.";
                        }
                        else if (temp.toString().indexOf(".") == -1) {
                            // no decimal point placed yet
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        } else {
                            temp = temp.substring(temp.indexOf(".") + 1);
                            if (temp.length() > afterDecimal) {
                                return "";
                            }
                        }
                        return super.filter(source, start, end, dest, dstart, dend);
                    }
                }
        });
        final SeekBar tipRate;
        tipRate = (SeekBar)findViewById(R.id.sbTipPercent);
        tipRate.incrementProgressBy(5);
        tipRate.setMax(50);
        tipRate.setProgress(0);
        final TextView tipRateDisplay = (TextView)findViewById(R.id.tvTipRateDisplay);
        tipRateDisplay.setText(String.valueOf(tipRate.getProgress()) + "%");
        tipRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = i / 5;
                i = i * 5;
                tipRateDisplay.setText(String.valueOf(i) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        List<String> spinnerList =  new ArrayList<String>();
        spinnerList.add("Nope");
        spinnerList.add("2-way");
        spinnerList.add("3-way");
        spinnerList.add("4-way");
        spinnerList.add("5-way");
        spinnerList.add("6-way");
        spinnerList.add("7-way");
        spinnerList.add("8-way");
        spinnerList.add("9-way");
        spinnerList.add("10-way");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner split = (Spinner) findViewById(R.id.split);
        split.setAdapter(adapter);

        final TextView tipAmount = (TextView)findViewById(R.id.tvTipAmount);
        final TextView totalAmount = (TextView)findViewById(R.id.tvTotal);
        final TextView perPersonAmount = (TextView)findViewById(R.id.tvPerPerson);
        Button calculate;
        calculate = (Button)findViewById(R.id.bCalculate);
        calculate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(billAmount.getText().toString().matches("")){
                    //do nothing
                    Toast.makeText(getBaseContext(),"Must enter bill amount!",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    double tip;
                    double total;
                    double perPerson;
                    double tipPercent = (double)tipRate.getProgress();
                    double bill = Double.parseDouble(billAmount.getText().toString());
                    double s = 0;
                    String splitSelected = split.getSelectedItem().toString();
                    if(splitSelected=="Nope"){
                        s = 1;
                    } else if (splitSelected == "2-way"){
                        s = 2;
                    } else if (splitSelected == "3-way"){
                        s = 3;
                    } else if (splitSelected == "4-way"){
                        s = 4;
                    } else if (splitSelected == "5-way"){
                        s = 5;
                    } else if (splitSelected == "6-way"){
                        s = 6;
                    } else if (splitSelected == "7-way"){
                        s = 7;
                    } else if (splitSelected == "8-way"){
                        s = 8;
                    } else if (splitSelected == "9-way"){
                        s = 9;
                    } else if (splitSelected == "10-way"){
                        s = 10;
                    }
                    tip = (bill * tipPercent)/100.0;
                    total = bill + tip;
                    perPerson = total/s;
                    tipAmount.setText(priceFormat.format(tip));
                    totalAmount.setText(priceFormat.format(total));
                    perPersonAmount.setText(priceFormat.format(perPerson));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
