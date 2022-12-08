package com.example.apihumans;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class Adapter extends BaseAdapter {

    private Context nContext;
    List<mask> maskList;

    public Adapter(Context nContext, List<mask> BD)
    {
        this.nContext=nContext;
        this.maskList=BD;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i) { return maskList.get(i).getId();}

    public static Bitmap loadContactPhoto(ContentResolver cr, long id, Context context) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
            Resources res = context.getResources();
            return BitmapFactory.decodeResource(res, R.drawable.human);
        }
        return BitmapFactory.decodeStream(input);
    }


    private Bitmap getUserImage(String encodedImg)
    {
        byte[] bytes;
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {

            return BitmapFactory.decodeResource(nContext.getResources(), R.drawable.human);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(nContext,R.layout.activity_mask, null);
        ImageView Image=v.findViewById(R.id.Image);
        TextView Products=v.findViewById(R.id.textName);
        TextView Amount=v.findViewById(R.id.textCount);

        mask mask=maskList.get(i);
        Products.setText(mask.getProducts());
        Amount.setText(Integer.toString(mask.getAmount()));
        Image.setImageBitmap(getUserImage(mask.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenDetalis=new Intent(nContext,Edit_timetable.class);
                intenDetalis.putExtra("Bar",mask);
                nContext.startActivity(intenDetalis);

            }
        });


        return v;
    }
}
