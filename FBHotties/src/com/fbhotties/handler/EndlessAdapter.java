package com.fbhotties.handler;

import java.util.concurrent.atomic.AtomicBoolean;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fbhotties.utils.Constants;

abstract public class EndlessAdapter extends AdapterWrapper {

	abstract protected View getPendingView(ViewGroup parent);

	abstract protected void rebindPendingView(int position, View convertView);

	abstract protected boolean appendInBackground();

	private View pendingView = null;
	private int pendingPosition = -1;
	protected AtomicBoolean keepOnAppending = new AtomicBoolean(true);
	
	/**
	 * Constructor wrapping a supplied ListAdapter
	*/
	public EndlessAdapter(BaseAdapter wrapped) {
		super(wrapped);
	}
	

    public int getCount() {
    	//Log.v("", "COUNT="+ super.getCount() + " TOTAL=" +  Constants.TOTAL_RESULTS);
		if (keepOnAppending.get() && Constants.TOTAL_RESULTS > super.getCount()) {
			return (super.getCount()+1); // one more for "pending"
		}
		return (super.getCount());
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	if (position == super.getCount() && keepOnAppending.get()) {
			pendingView = getPendingView(parent);
			pendingPosition = position;

			new AppendTask().execute();
	        
			return (pendingView);
		}

		return (super.getView(position, convertView, parent));
    }
    
    class AppendTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			keepOnAppending.set(appendInBackground());

			return (null);
		}

		@Override
		protected void onPostExecute(Void unused) {
			
			rebindPendingView(pendingPosition, pendingView);
			pendingView = null;
			pendingPosition = -1;
		}
	}

}
