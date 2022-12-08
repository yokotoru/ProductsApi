package com.example.apihumans;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_timetable extends AppCompatActivity {

    String img="";
    private ImageView imageButton;
    private EditText Products, Amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timetable);
        imageButton=findViewById(R.id.ImgBut);
        Products=findViewById(R.id.edtDrink);
        Amount=findViewById(R.id.edtPrice);

    }

    public void onClickChooseImage(View view)
    {
        getImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageButton.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img= Base64.getEncoder().encodeToString(bytes);
            return img;
        }
        return "";
    }


    public void Add(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(Add_timetable.this);
        builder.setTitle("Добавление")
                .setMessage("Подтвердите добавление")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Products.getText().length()==0 || Amount.getText().length()==0 )
                        {
                            Toast.makeText(Add_timetable.this, "Не заполненны обязательные поля", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            if (img=="")
                            {
                                img=null;
                                postAdd(img, Products.getText().toString(),Amount.getText().toString());
                            }
                            else
                            {
                                postAdd(img, Products.getText().toString(),Amount.getText().toString());
                            }
                            new CountDownTimer(1000, 1000) {
                                public void onFinish() {
                                    Next();
                                }

                                public void onTick(long millisUntilFinished) {

                                }
                            }.start();

                        }
                    }

                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void postAdd(String image, String products,String amount)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/кузнецоваон/api/Products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        DataModal modal = new DataModal(image, products,Integer.parseInt(amount));

        Call<DataModal> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(Add_timetable.this, "Вы добавили запись", Toast.LENGTH_SHORT).show();
                Products.setText("");
                Amount.setText("");
                imageButton.setImageResource(R.drawable.human);
                DataModal responseFromAPI = response.body();

            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });
    }

    public void Next()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void Back_bt(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}