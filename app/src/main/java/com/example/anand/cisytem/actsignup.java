package com.example.anand.cisytem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import MyCustomPackage.constants;
import customfonts.MyEditText;
import customfonts.MyTextView;


public class actsignup extends AppCompatActivity  implements View.OnClickListener{
    private static final String REGISTER_URL = constants.url+"/CIS/student_signup.php";

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TABLE = "table";

    private MyEditText  Textname;
    private MyEditText TextEmail;
    private MyEditText TextconPassword;
    private MyEditText TextPassword;
    private Spinner seluser;
    private MyTextView buttonSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actsignup);
        Textname = (MyEditText) findViewById(R.id.txt_name);
        TextEmail = (MyEditText) findViewById(R.id.txt_email);
        TextPassword = (MyEditText) findViewById(R.id.txt_password);
        TextconPassword = (MyEditText) findViewById(R.id.txt_repassword);
        seluser=(Spinner)findViewById(R.id.spinner);

        buttonSubmit = (MyTextView) findViewById(R.id.btn_submit);

        buttonSubmit.setOnClickListener(this);
    }

    private void registerUser(String tablelocal){
        System.out.println("inside registerUser");
        final String table=tablelocal;
        final String username = Textname.getText().toString().trim();
        final String email = TextEmail.getText().toString().trim();
        final String password = TextPassword.getText().toString().trim();
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(actsignup.this,response,Toast.LENGTH_LONG).show();
                        Intent inn = new Intent(actsignup.this, actlogin.class);
                        startActivity(inn);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(actsignup.this, volleyError.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }

                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_NAME,username);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_TABLE, table);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean validate(){
        boolean valid =true;
        if(Textname.getText().toString().trim().isEmpty() || Textname.length()>32)
        {
            Textname.setError("Please enter a valid name");
            valid=false;
        }
        if(TextEmail.getText().toString().trim().isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(TextEmail.getText().toString().trim()).matches()) {
            TextEmail.setError("Please enter a valid email address");
            valid= false;
        }
        if(TextPassword.getText().toString().trim().isEmpty()){
            TextPassword.setError("Please Enter password");
            valid=false;
        }
        if (TextconPassword.getText().toString().trim().isEmpty()){
            TextconPassword.setError("Please Re-Enter Password");
        }
        return valid;
    }
    public void onClick(View v) {
        if(v==buttonSubmit) {
            if(!TextPassword.getText().toString().equals(TextconPassword.getText().toString()))
            {
                System.out.println("inside lkfkj");
                Toast.makeText(actsignup.this,"password mismatched",Toast.LENGTH_LONG).show();
                return;
            }
            String testing=seluser.getSelectedItem().toString();
            System.out.println("testing="+testing);
            if (testing.equals("Admin")) {
                System.out.println("inside admin if");
                if(!validate())
                {
                    Toast.makeText(this,"Sign Up Failed ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerUser("classrepresentative");

                }

            } else if (testing.equals("Student")) {
                System.out.println("inside student if");
                if(!validate())
                {
                    Toast.makeText(this,"Sign Up Failed ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerUser("student");
                }
            }
        }
    }
    public void opensignin(View v)
    {
        // TODO: 12-11-2016 add proper redirect
        //Intent in= new Intent(this,MainActivity.class);
        Intent in= new Intent(this,actlogin.class);
        //Intent in= new Intent(this,actstudent_profile.class);
        //Intent in= new Intent(this,actadmin_profile.class);
        //Intent in= new Intent(this,actsignup.class);
        startActivity(in);
        //bbaah
    }

}
