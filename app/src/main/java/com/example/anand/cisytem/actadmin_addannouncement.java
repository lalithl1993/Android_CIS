package com.example.anand.cisytem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;

import MyCustomPackage.constants;

public class actadmin_addannouncement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actadmin_addannouncement);

    }



    private void send(){
        TextView message=(TextView)findViewById(R.id.txtannouncement);
        String query="INSERT INTO announcement (crid, classroomid, data) VALUES ('"+constants.id+"','"+constants.classroom_id+"','"+message.getText()+"')";
        // TODO: convert to post if possible
        try{
            query= URLEncoder.encode(query,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String url= constants.url+"/CIS/insert.php?q="+query;
        System.out.println(url);
//        final String table=tablelocal;
//        final String username = Textname.getText().toString().trim();
//        final String email = TextEmail.getText().toString().trim();
//        final String password = TextPassword.getText().toString().trim();
        //final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //loading.dismiss();
                        if(response.equals("1")) {
                            Toast.makeText(actadmin_addannouncement.this, "sent success", Toast.LENGTH_LONG).show();
                            actadmin_addannouncement.super.onBackPressed();
                        }
                        else {
                            Toast.makeText(actadmin_addannouncement.this, "error adding", Toast.LENGTH_LONG).show();
                        }
                        //Toast.makeText(actadmin_addannouncement.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //loading.dismiss();
                        if(volleyError.getMessage()!=null) {
                            Toast.makeText(actadmin_addannouncement.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(actadmin_addannouncement.this, "server connection failed", Toast.LENGTH_LONG).show();
                        }
                        //Toast.makeText(actadmin_addannouncement.this, volleyError.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }

                }){


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void button_send(View v)
    {
        send();
    }

}
