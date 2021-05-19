package com.example.a3shwa2y;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class DB_Connection {

    private String _URL;
    private Context context;
    private User user;
    private String  currentDateTimeString;
    private boolean check;
    private String info[];

    DB_Connection(Context context){
        _URL="http://192.168.1.6/points/public/api";
        this.context=context;
    }
    DB_Connection(Context context, User user){
        _URL="http://192.168.1.6/points/public/api";
        this.context=context;
        this.user=user;
    }

    public void add(){

     final  RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest postreq=new StringRequest(Request.Method.POST, _URL+'/'+"store",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Rest Response",response);
                     if(user.getU_image()!=null) {
                         StringRequest addImage = new StringRequest(Request.Method.POST, _URL + '/' + "addImage", new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                                   Log.d("Imageeee",response);
                             }
                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Log.e("Rest Response", error.toString());
                             }
                         }) {
                             @Override


                             protected Map<String, String> getParams() {
                                 Map<String, String> params2 = new HashMap<String, String>();

                                 params2.put("User_id", String.valueOf(user.getID()));
                                 params2.put("image", user.getU_image());


                                 return params2;
                             }
                         };
                         requestQueue.add(addImage);
                     }
                        save s=new save();
                        s.save(context,"check","true");
                        s.save(context,"email",user.getU_Email());
                        s.save(context,"password",user.getU_password());
                        s.save(context,"image",user.getU_image());
                        Toast.makeText(context, "Welcome", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(context, main_page1.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        user.setU_image("");
                        myIntent.putExtra("MyClass", user);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(myIntent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response",error.toString() );

                    }
                }
        ){
            @Override


                protected Map<String, String> getParams () {
                Map<String, String> params = new HashMap<String, String>();

               params.put("name", user.getU_name());
               params.put("mail", user.getU_Email());
               params.put("password", user.getU_password());
               params.put("status", String.valueOf( user.getU_status()));
               params.put("points",String.valueOf(user.getU_points()));
               params.put("qr_code",user.getEncodedImage());



                return params;
            }


        };

        requestQueue.add(postreq);

    }
    public void transaction(final User model, final int taken_id, final int points){

        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        check=true;
        Toast.makeText(context,currentDateTimeString,Toast.LENGTH_LONG).show();
         final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest get_points=new StringRequest(Request.Method.GET, _URL + '/' + "get_points" + '/' + model.getID(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(Integer.parseInt(response)<points && model.getU_status()==0){
                    check=false;
                    Toast.makeText(context,"Sorry you don't have enough money",Toast.LENGTH_LONG).show();
                }
                else {
                    StringRequest postreq = new StringRequest(Request.Method.POST, _URL + '/' + "store_tran",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Rest Response", response);

                                    model.setU_points(model.getU_points()-points);
                                    Toast.makeText(context, "Welcome", Toast.LENGTH_LONG).show();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Rest Response", error.toString());

                                }
                            }
                    ) {
                        @Override


                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("given_id", String.valueOf(model.getID()));
                            params.put("taken_id", String.valueOf(taken_id));
                            params.put("points", String.valueOf(points));
                            params.put("date", currentDateTimeString);


                            return params;
                        }


                    };
                    StringRequest update_points1 = new StringRequest(Request.Method.GET, _URL + '/' + "update_points" + '/' + model.getID() + '/' + (-1 * points), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Rest Response", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Rest Response", error.toString());
                        }
                    });
                    StringRequest update_points2 = new StringRequest(Request.Method.GET, _URL + '/' + "update_points" + '/' + taken_id + '/' + points, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Rest Response", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Rest Response", error.toString());
                        }
                    });


                    requestQueue.add(postreq);
                    requestQueue.add(update_points1);
                    requestQueue.add(update_points2);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response",error.toString() );
            }
        });
        requestQueue.add(get_points);

    }
    public void search() {
        String password = "";
        String email="";


            password = user.getU_password();
            email=user.getU_Email();

        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, _URL + '/' + "search" + '/' + email+'/'+password, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response

                        Log.d("Response", response.toString());

                        int  len_of_res=-1;
                        len_of_res=response.length();
                         if(len_of_res==0){
                             Toast.makeText(context,"Email or password doesn't excist",Toast.LENGTH_LONG).show();

                         }
                         else {
                             try {
                             JSONObject obj = response.getJSONObject(0);
                             final User model = new User();
                             model.setID(Integer.parseInt(obj.getString("id")));
                             model.setU_name(obj.getString("name"));
                             model.setU_Email(obj.getString("mail"));
                             model.setU_password(obj.getString("password"));
                             model.setU_points(Integer.parseInt(obj.getString("points")));
                             model.setU_status(Integer.parseInt(obj.getString("status")));
                             model.setEncodedImage(obj.getString("qr_code"));
                             JsonArrayRequest getImage=new JsonArrayRequest(Request.Method.GET, _URL + '/' + "getImage" + '/' + model.getID(), null, new Response.Listener<JSONArray>() {
                                 @Override
                                 public void onResponse(JSONArray response) {
                                           try {
                                               if(response.length()!=0) {
                                                   JSONObject image = response.getJSONObject(0);
                                                   model.setU_image(image.getString("image"));

                                               }
                                                   save s=new save();
                                                   s.save(context,"check","true");
                                                   s.save(context,"email",model.getU_Email());
                                                   s.save(context,"password",model.getU_password());
                                                   s.save(context,"image",model.getU_image());
                                                   model.setU_image("");
                                                   Toast.makeText(context, "Welcome", Toast.LENGTH_LONG).show();
                                                   Intent myIntent = new Intent(context, main_page1.class);
                                                   myIntent.putExtra("MyClass", model);
                                                   myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                   context.startActivity(myIntent);


                                           }catch (JSONException e) {
                                               e.printStackTrace();
                                           }
                                 }
                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {
                                     Log.d("Error.Response", error.toString());
                                 }
                             });
                             requestQueue.add(getImage);





                         } catch (JSONException e) {
                            e.printStackTrace();
                        }

                         }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        requestQueue.add(getRequest);

    }
    public void get_next_id(){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest getRequest=new StringRequest(Request.Method.GET, _URL+'/'+"get_id",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Rest Response",response);
                        user.setID(Integer.parseInt(response));
                        String input=String.valueOf(user.getID());
                        Bitmap bitmap;
                        QRGEncoder qrgEncoder=new QRGEncoder(input,null, QRGContents.Type.TEXT,200);
                        try {
                            bitmap=qrgEncoder.encodeAsBitmap();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                            user.setEncodedImage(encodedImage);

                        }
                        catch (WriterException e){

                        }
                        add();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response",error.toString() );

                    }
                });


// add it to the RequestQueue
        requestQueue.add(getRequest);


    }


    public void report(final int  id, final View view) throws ExecutionException, InterruptedException {

       // RequestFuture<JSONObject> future = RequestFuture.newFuture();
          RequestQueue requestQueue=Volley.newRequestQueue(context);
          JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, _URL + '/' + "report" + '/' + id, null,
                  new Response.Listener<JSONArray>() {
              @Override
              public void onResponse(JSONArray response) {
                  try{
                       info=new String[response.length()];
                      for (int k = 0; k< response.length(); k++){

                          JSONObject jsonInnerObject = response.getJSONObject(k);
                          if(Integer.parseInt( jsonInnerObject.getString("given_id"))!=id){
                             String   given=jsonInnerObject.getString("given");
                             String taken="You";
                              String points=jsonInnerObject.getString("points");
                              String date=jsonInnerObject.getString("date");
                              info[k]=given+" "+"Give"+" "+taken+" "+points+" "+"points"+" "+"at"+" "+date;
                          }
                          else{
                              String given="You";
                              String taken =jsonInnerObject.getString("taken");
                              String points=jsonInnerObject.getString("points");
                              String date=jsonInnerObject.getString("date");
                              info[k]=given+" "+"Give"+" "+taken+" "+points+" "+"points"+" "+"at"+" "+date;
                          }

                      }
                      ListAdapter report=new ArrayAdapter(context,android.R.layout.simple_list_item_1,info);
                      ListView report_view=view.findViewById(R.id.report);
                      report_view.setAdapter(report);


                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Log.e("Rest Response",error.toString());
              }
          });

       requestQueue.add(jsonArrayRequest);
        //JSONObject response=future.get();
    }
    public void changeImage(  User model,final String image){
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        StringRequest changeImage=new StringRequest(Request.Method.POST, _URL + '/' + "changeImage" + '/' + 48, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responce",response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response", error.toString());
            }
        }
        )
        {
                @Override


                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("image", image);

                    return params;
                }
        };

        requestQueue.add(changeImage);
    }
    public String get_URL() {
        return _URL;
    }

    public void set_URL(String _URL) {
        this._URL = _URL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }




}
