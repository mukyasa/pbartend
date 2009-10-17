package com.bartender.view;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bartender.R;

public class ImageListAdapter extends ArrayAdapter<Drawable> {

	List images;
	
	public ImageListAdapter(Activity activity, List<Drawable> images, ListView listView) {
		super(activity, 0, images);
		this.images = images;
		
	}
	
   @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();
        LayoutInflater inflater = activity.getLayoutInflater();

        // Inflate the views from XML
        View rowView = inflater.inflate(R.layout.imagerow, null);

        // Load the image and set it on the ImageView
        Drawable drawable = (Drawable)images.get(position);
        
        
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ivGlass);
        imageView.setImageDrawable(drawable);
        
        return rowView;
    }

	
	

}
