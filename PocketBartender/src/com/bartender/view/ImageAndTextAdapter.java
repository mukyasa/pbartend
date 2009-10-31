package com.bartender.view;

import com.bartender.domain.ScreenType;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class ImageAndTextAdapter extends SimpleCursorAdapter {

	private Context context;
	public ImageAndTextAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		
		this.context = context;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.v(getClass().getSimpleName(),position + convertView.toString() + parent.toString());
		return null;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Log.v(getClass().getSimpleName(),view.toString()+context.toString()+cursor.toString());
	}

}
