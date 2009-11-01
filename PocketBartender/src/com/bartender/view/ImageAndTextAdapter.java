package com.bartender.view;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bartender.R;
import com.bartender.dao.DataDAO;

public class ImageAndTextAdapter extends SimpleCursorAdapter {

	private Context context;
	public ImageAndTextAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		
		this.context = context;
		
	}
	
	
	/* (non-Javadoc)
	 * @see android.widget.SimpleCursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		Log.v(getClass().getSimpleName(),cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME)));
		
		Drawable d =null;
			
		if("rocks".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.rocks);
		}else if("highball".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.highball);
		}
		else if("mug".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.mug);
		}
		else if("hurricane".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.hurricane);
		}
		else if("pint".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.pint);
		}
		else if("white wine".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.whitewine);
		}
		else if("red wine".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.redwine);
		}
		else if("cocktail".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.cocktail);
		}
		else if("champagne".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.champ);
		}
		else if("margarita".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.margarita);
		}
		else if("shot".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.shot);
		}
		else if("sour".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.sour);
		}
		else if("pousse cafe".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.pousse_cafe);
		}
		else if("parfait".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.parfait);
		}
		else if("irish coffee".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.irish);
		}
		else if("pilsner".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.pilsner);
		}
		else if("punch".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.punch);
		}
		else if("snifter".equals(cursor.getString(cursor.getColumnIndex(DataDAO.COL_GLASS_NAME))))
		{
			d = context.getResources().getDrawable(R.drawable.snifter);
		}
		
		((TextView)view).setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
	    super.bindView(view, context, cursor);
	}

}
