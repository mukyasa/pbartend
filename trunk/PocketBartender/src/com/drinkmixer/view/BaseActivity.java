/**
 * Date: Oct 29, 2009
 * Project: PocketBartender
 * User: dmason
 * This software is subject to license of
 * IBBL-TGen
 * http://www.gouvernement.lu/
 * http://www.tgen.org 
 */
package com.drinkmixer.view;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.drinkmixer.R;
import com.drinkmixer.dao.DrinkIngredientsInsert;
import com.drinkmixer.dao.DrinkInserts;
import com.drinkmixer.dao.MixerDbHelper;
import com.drinkmixer.utils.Constants;

/**
 * @author dmason
 * @version $Revision$ $Date$ $Author$ $Id$
 */
public class BaseActivity extends Activity implements OnTouchListener {

	protected int DIALOG_TYPE_ERROR = 0;
	protected int DIALOG_TYPE_SUCCESS = 1;

	protected Dialog onCreateDialog(int id) {

		if (id == DIALOG_TYPE_ERROR) {
			return new AlertDialog.Builder(BaseActivity.this).setIcon(
					R.drawable.info).setMessage(
					"Looks like you forgot to choose a type.").setTitle(
					"Add New Error").setNegativeButton("Close",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							dismissDialog(DIALOG_TYPE_ERROR);
						}
					}).create();
		} else {
			return new AlertDialog.Builder(BaseActivity.this).setIcon(
					R.drawable.info).setMessage(
					"You have succesfully added a new item.").setTitle(
					"Add New").setNegativeButton("Close",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							dismissDialog(DIALOG_TYPE_SUCCESS);
						}
					}).create();
		}
	}

	public boolean onTouch(View v, MotionEvent event) {

		if (v instanceof Button) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundResource(R.drawable.button_over);
				v.setPadding(0, 0, 0, 10);
			}

			else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.setBackgroundResource(R.drawable.button_bg);
				v.setPadding(0, 0, 0, 10);
			}
		} else if (v instanceof EditText) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// clear base text
				if (((EditText) v).getText().toString().equals(
						this.getString(R.string.create_drink_title))
						|| ((EditText) v).getText().toString().equals(
								this.getString(R.string.instructionsText))
						|| ((EditText) v).getText().toString().equals(
								this.getString(R.string.newLiquorName))
						|| ((EditText) v).getText().toString().equals(
								this.getString(R.string.newMixerName))
						|| ((EditText) v).getText().toString().equals(
								this.getString(R.string.newGarnishName))) {
					((EditText) v).setText("");
					((EditText) v).setTextColor(Color.BLACK);
				}

			}

		}

		return false;
	}
}
