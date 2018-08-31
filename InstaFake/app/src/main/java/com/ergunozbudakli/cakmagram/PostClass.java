package com.ergunozbudakli.cakmagram;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String> {
    private final ArrayList<String> usernames;
    private final ArrayList<Bitmap> images;
    private final ArrayList<String> comments;
    private final ArrayList<String> dates;
    private final Activity context;

    public PostClass(ArrayList<String> usernames, ArrayList<Bitmap> images, ArrayList<String> comments, ArrayList<String> dates, Activity context){

        super(context,R.layout.custom_view,dates);
        this.usernames=usernames;
        this.images=images;
        this.comments=comments;
        this.dates=dates;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.custom_view,null,true);
        TextView usernameText= customView.findViewById(R.id.custom_view_username_text);
        ImageView image=customView.findViewById(R.id.custom_view_imageView);
        TextView commentText=customView.findViewById(R.id.custom_view_comment_text);

        usernameText.setText(usernames.get(position)+"                                           "+dates.get(position));
        image.setImageBitmap(images.get(position));
        commentText.setText(comments.get(position));

        return customView;
    }
}
