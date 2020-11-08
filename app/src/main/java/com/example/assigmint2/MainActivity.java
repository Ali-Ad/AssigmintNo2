package com.example.assigmint2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.BreakIterator;


public class MainActivity extends AppCompatActivity {



    public static final String NAME ="NAME";
    public static final String HEIGHT ="Height";
    public static final String WIGHT="WIGHT";
    public static final String FLAG ="FLAG";
    private CheckBox chkbox;
    private EditText edtName;
    private EditText edtHeight;
    private EditText edtWight;
    private Spinner genderSpin;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Button saveBtn;

    private TextView resbmiview;
    String calculation;
    private Button Activity2Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupView();
        setSharedPrefs();
        checkPrefs();
        Activity2Btn=findViewById(R.id.act2btn);
        Activity2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivty2();
            }
        });



    }
    public void openActivty2(){
        Intent intent =new Intent(this,Activity2.class);
        startActivity(intent);
    }


    private void checkPrefs() {
        boolean flag =prefs.getBoolean(FLAG,false);
        if(flag){
            String name =prefs.getString(NAME,"");
            String height =prefs.getString(HEIGHT,"");
            String wight =prefs.getString(WIGHT,"");
            edtName.setText(name);

            edtHeight.setText(height);
            edtWight.setText(wight);
            chkbox.setChecked(true);
        }
    }

    private void setSharedPrefs(){
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor =prefs.edit();
    }

    private void setupView() {
        edtName =findViewById(R.id.etext_name);
        edtHeight =findViewById(R.id.etext_height);
        edtWight =findViewById(R.id.etext_weight);
        chkbox =findViewById(R.id.c_box);

    }
    public void btnLoginOnClick(View view){
        String name =edtName.getText().toString();
        String height =edtHeight.getText().toString();
        String wight =edtWight.getText().toString();



        if(chkbox.isChecked()){
            editor.putString(NAME ,name);
            editor.putString(HEIGHT,height);
            editor.putString(WIGHT,wight);
            editor.putBoolean(FLAG,true);
            editor.commit();

        }}

    public void btnBmiOnclick(View view) {
        resbmiview=findViewById(R.id.resBmiView);
        edtHeight =findViewById(R.id.etext_height);
        edtWight =findViewById(R.id.etext_weight);

        String Height =edtHeight.getText().toString();
        String wight =edtWight.getText().toString();


        double wightvale =Double.parseDouble(wight);
        double highvalue =Double.parseDouble(Height)/100;

        double bmival = wightvale /( highvalue * highvalue );
        calculation = "" +bmival+ "";
        resbmiview.setText(calculation);

    }


}