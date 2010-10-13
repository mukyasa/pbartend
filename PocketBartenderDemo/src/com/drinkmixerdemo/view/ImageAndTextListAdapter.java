package com.drinkmixerdemo.view;

import com.drinkmixerdemo.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ImageAndTextListAdapter extends ArrayAdapter<String> {

	private Context context;
	private String[] titles;
	private int layout;
	
	public ImageAndTextListAdapter(Context context, int layout, String[] titles) {
		super(context, layout, titles);
		
		this.titles = titles;
		this.context = context;
		this.layout = layout;
		
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {

	 	Activity activity = (Activity) getContext();
        LayoutInflater inflater = activity.getLayoutInflater();

        // Inflate the views from XML
        TextView rowView = (TextView) inflater.inflate(this.layout, null);
        rowView.setText(this.titles[position]);

        Drawable d =null;
			
		if("rocks".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.rocks);
		else if("highball".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.highball);
		else if("mug".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.mug);
		else if("hurricane".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.hurricane);
		else if("pint".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.pint);
		else if("white wine".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.whitewine);
		else if("red wine".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.redwine);
		else if("cocktail".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.cocktail);
		else if("champagne".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.champ);
		else if("margarita".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.margarita);
		else if("shot".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.shot);
		else if("sour".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.sour);
		else if("pousse cafe".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.pousse_cafe);
		else if("parfait".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.parfait);
		else if("irish coffee".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.irish);
		else if("pilsner".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.pilsner);
		else if("punch".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.punch);
		else if("snifter".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.snifter);
		else if("old-fashioned".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.old_fashion);
		else if("cordial".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.cordial);
		else if("collins".equalsIgnoreCase(this.titles[position]))
			d = context.getResources().getDrawable(R.drawable.collins);
		else
			d = context.getResources().getDrawable(R.drawable.detail_info);
		
		rowView.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
		
	    return rowView;
	}
	
	
}
