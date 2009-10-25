package com.bartender.view;

import java.util.HashMap;
import java.util.Iterator;
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

public class ImageListAdapter extends ArrayAdapter<HashMap<Integer, Drawable>> {

	List<HashMap<Integer, Drawable>> images;
	
	public ImageListAdapter(Activity activity, List<HashMap<Integer, Drawable>> images, ListView listView) {
		super(activity, 0, images);
		this.images = images;
		
	}
	
   @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();
        LayoutInflater inflater = activity.getLayoutInflater();

        // Inflate the views from XML
        View rowView = inflater.inflate(R.layout.imagerow, null);

        HashMap<Integer, Drawable> map = (HashMap<Integer, Drawable>)images.get(position);
        Iterator<Integer> iter = map.keySet().iterator();
        
        Drawable drawable=null;
        int id=0;
        while (iter.hasNext()) {
	        Integer key = (Integer) iter.next();
	        drawable = (Drawable)map.get(key);
	        id = key;
        }
        
        // Load the image and set it on the ImageView
        
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ivGlass);
        imageView.setImageDrawable(drawable);
        rowView.setId(id);
        
        return rowView;
    }

	
	

}
