package com.bartender.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.bartender.R;
import com.bartender.dao.DataDAO;
import com.bartender.domain.NewDrinkDomain;

public class CategoryAndGlassListView extends Activity {
	
	 ListView listCategories,listGlasses; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        
        setContentView(com.bartender.R.layout.cat_glass);

        listCategories = (ListView) findViewById(R.id.listCategories);
        
        listCategories.setAdapter(new ArrayAdapter<String>(this,
        		R.layout.textviewrow, CATEGORIES));
        
        listGlasses = (ListView)findViewById(R.id.listGlasses);
        
        //addes images to list items
        List<Drawable> imageList = new ArrayList<Drawable>(); 
        
        imageList.add(getResources().getDrawable(R.drawable.champ));
        imageList.add(getResources().getDrawable(R.drawable.cocktail));
        imageList.add(getResources().getDrawable(R.drawable.highball));
        imageList.add(getResources().getDrawable(R.drawable.hurricane));
        imageList.add(getResources().getDrawable(R.drawable.irish));
        imageList.add(getResources().getDrawable(R.drawable.margarita));
        imageList.add(getResources().getDrawable(R.drawable.mug));
        imageList.add(getResources().getDrawable(R.drawable.parfait));
        imageList.add(getResources().getDrawable(R.drawable.pilsner));
        imageList.add(getResources().getDrawable(R.drawable.pint));
        imageList.add(getResources().getDrawable(R.drawable.pousse_cafe));
        imageList.add(getResources().getDrawable(R.drawable.punch));
        imageList.add(getResources().getDrawable(R.drawable.rocks));
        imageList.add(getResources().getDrawable(R.drawable.shot));
        imageList.add(getResources().getDrawable(R.drawable.snifter));
        imageList.add(getResources().getDrawable(R.drawable.sour));
        imageList.add(getResources().getDrawable(R.drawable.wine));

        
        listGlasses.setAdapter(new ImageListAdapter(this,imageList,listGlasses));
       
       //inner class
       AdapterView.OnItemClickListener onItemClick = new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View v, int position,long id) {

				Log.v(getClass().getSimpleName(), "class name=" + v.getClass().toString());
				Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		    	//set id and name to create domain
		    	NewDrinkDomain ndd = NewDrinkDomain.getInstance();
		    	//cat info
		    	ndd.setCategoryId(id);
		    	ndd.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_NAME)));
		    	//glass id
		    	ndd.setGlassId(id);
			}};
			
		listGlasses.setOnItemClickListener(onItemClick);
		listCategories.setOnItemClickListener(onItemClick);
		        
    
    }
   
    
    static final String[] CATEGORIES = new String[] {
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


}