package com.bartender.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.bartender.R;
import com.bartender.common.AppCommon;
import com.bartender.dao.DataDAO;
import com.bartender.dao.DatabaseAdapter;
import com.bartender.domain.NewDrinkDomain;

public class AmountPicker extends Activity  implements OnClickListener{
	
	 private ListView listWholeNums,listHalfNums,measurementtypes;
	 private Button btnSave,btnCancel;
	 private Intent intent;
	 protected DatabaseAdapter myDatabaseAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        
        setContentView(R.layout.amounts);

        listWholeNums = (ListView) findViewById(R.id.listWholeNums);
        listHalfNums = (ListView) findViewById(R.id.listHalfNums); 
        measurementtypes = (ListView)findViewById(R.id.listAmounts);
        
        listHalfNums.setOnItemClickListener(onHalfItemListener);
        listWholeNums.setOnItemClickListener(onWholeItemListener);
        measurementtypes.setOnItemClickListener(onMeasureItemListener);
        
        listHalfNums.setAdapter(new ArrayAdapter<String>(this,
        		R.layout.textviewrow, COUNTRIES));

        listWholeNums.setAdapter(new ArrayAdapter<String>(this,
        		R.layout.textviewrow, WHOLENUMBERS));
        
        measurementtypes.setAdapter(new ArrayAdapter<String>(this,
        		R.layout.textviewrow, AMOUNTS));
        		
    }
    
    public void onClick(View v) {

    	if(v==btnSave)
		{
			intent = new Intent(this, CreateUpdateView.class);
			startActivity(intent);
		}
		else if(v==btnCancel)
		{
			intent = new Intent(this, CreateUpdateView.class);
			startActivity(intent);
		}
    	
	}
    
    static final String[] AMOUNTS = new String[] {"cup","oz","pint","quart","gallon","tsp","tbsp","lb","bottles(s)"
    	,"can(s)"
    	,"dashe(es)"
    	,"drop(s)"
    	,"part(s)"
    	,"pieces(s)"
    	,"scoop(s)"
    	,"splash(es)"
    	,"sprig(s)"
    	,"stick(s)"};
    
    static final String[] WHOLENUMBERS = new String[] {"AZ","CA","MO","UT","FL","NY","OH","AR"};
    
    static final String[] COUNTRIES = new String[] {
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
        "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
        "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
        "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
        "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
        "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
        "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
        "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",
        "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
        "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
        "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
        "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
        "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
        "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
        "Former Yugoslav Republic of Macedonia", "France", "French Guiana", "French Polynesia",
        "French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar",
        "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
        "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras", "Hong Kong", "Hungary",
        "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
        "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
        "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
        "Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
        "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova",
        "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
        "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand",
        "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Marianas",
        "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru",
        "Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
        "Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe", "Saint Helena",
        "Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon",
        "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal",
        "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
        "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Korea",
        "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden",
        "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Bahamas",
        "The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
        "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
        "Ukraine", "United Arab Emirates", "United Kingdom",
        "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
        "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara",
        "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"
      };
    
    private void setBackgroundDefault(AdapterView<?> parent)
    {
    	for(int i=0;i<parent.getCount();i++)
		{
			View vv = parent.getChildAt(i);
			if(vv != null)
				vv.setBackgroundColor(AppCommon.defaultColor);
		}
    }
    
    AdapterView.OnItemClickListener onWholeItemListener = new OnItemClickListener(){
		
    	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

    		//set all to white
    		setBackgroundDefault(parent);
    		NewDrinkDomain.getInstance().
    		
			v.setBackgroundColor(AppCommon.color);
			
			}};
			
	AdapterView.OnItemClickListener onHalfItemListener = new OnItemClickListener(){
		
		public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
	
			//set all to white
			setBackgroundDefault(parent);
			
			v.setBackgroundColor(AppCommon.color);
			
			}};
					
	AdapterView.OnItemClickListener onMeasureItemListener = new OnItemClickListener(){
		
    	public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

    		//set all to white
    		setBackgroundDefault(parent);
    		
			v.setBackgroundColor(AppCommon.color);
			
			}};

}
