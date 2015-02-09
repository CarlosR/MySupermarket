package com.example.mysupermarket;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN) public class MainActivity extends Activity implements OnClickListener{
	
	
	private Button scanBtn,saveBtn;	
	private TextView formatTxt, contentTxt;
	private ListView lv;
	private String[] lv_arr = {};
    private ArrayList<String> contents = new ArrayList<String>();
    private int cont = 0;
    private String total = "0.00";
    private float sumador = 0;
    private boolean entro;
    
    //SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	Parse.initialize(this, "h9zxLQYEUs5SKU3lyq2KFi4tFZzacE6SQjkUk1He", "upXRkcYoz3elF33RX7FEFLl2JhUEv2HKw2V85hTY"); 
        
        setContentView(R.layout.activity_main);
        scanBtn = (Button)findViewById(R.id.scan_button);
        saveBtn = (Button)findViewById(R.id.save_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        lv = (ListView) findViewById(R.id.ListView01);
        scanBtn.setOnClickListener(this);

		

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View v){
    	
    	if(v.getId()==R.id.scan_button){
    		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
    		scanIntegrator.initiateScan();
        }
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	
	    	IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
	    	
	    	if (scanningResult.getContents() != null) {
	    		String scanContent = scanningResult.getContents();
	    		String scanFormat = scanningResult.getFormatName();
	    		formatTxt.setText("TOTAL: L." + total);
	    		contentTxt.setText("CODIGO: " + scanContent);
	    		
	    		
	    		//contents.add(scanContent);
	        		
	        	//lv_arr = (String[])contents.toArray(new String[0]);
	        		
	        	/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
	        	       android.R.layout.simple_list_item_1, lv_arr);
	        	lv.setAdapter(adapter);*/
	    		int cant = cont;
	    		sumador = Float.valueOf(total);
	        	 
	    		ParseQuery<ParseObject> query = ParseQuery.getQuery("misupermarket2");
	    		//query.whereEqualTo("codigo",scanContent);
	    		

	    		List<ParseObject> results;
				try {
					results = query.find();
					if(!results.isEmpty()){
		    			for(int i=0;i<results.size();i++){
		    				if(results.get(i).get("codigo").equals(scanContent)){
		    					cont+=1;
							    contents.add(results.get(i).getString("Descripcion")+" L."+results.get(i).getString("Precio"));
							    String num =  results.get(i).getString("Precio");
							    sumador = sumador + Integer.parseInt(num);
							    entro = true;
							    break;
		    				}
		    			}
		    			    		
		    		} else {
			            entro = false;
			        }
				} catch (ParseException e) {
					e.printStackTrace();
				}
	        	
	    		
	    			   
		    		
	    			if(entro==true){
		    			lv_arr = (String[])contents.toArray(new String[0]);
		  				
					    ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getApplicationContext(), 
		  	        	        android.R.layout.simple_list_item_1, lv_arr);
		  				
		  				
		  				total = String.valueOf(sumador);
		  				formatTxt.setText("TOTAL: L." + total);
		  	        	
		  				lv.setAdapter(adaptor);
	    			}
	    		

	    		//DatabaseHelper myDbHelper = new DatabaseHelper(this);
	        	  
	        	 //try {
	        		 //File file = new File("/data/data/com.example.mysupermarket/databases/BDD_Prueba");
	        		 //database.deleteDatabase(file);
	        		 
	        		 /*myDbHelper.createDataBase();
	        		 myDbHelper.openDataBase();
	        		 database = myDbHelper.getReadableDatabase();
	        		 Cursor cursor;
	        		 cursor = database.query("Productos", new String[] { "_id", "Codigo", "Nombre", "Precio" }, 
	        				  null, null, null, null, null);
	        		cursor.moveToFirst();
	        		
	        		while(!cursor.isAfterLast()){
	        			
	        			if(scanContent.equals(cursor.getString(1))){
	        				cont+=1;
	        				contents.add(cursor.getString(2)+" "+cursor.getString(3));
	        				lv_arr = (String[])contents.toArray(new String[0]);
	        				
	        				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
	        	        	        android.R.layout.simple_list_item_1, lv_arr);
	        				
	        				sumador = sumador + Float.valueOf(cursor.getString(3));
	        				total = String.valueOf(sumador);
	        				formatTxt.setText("TOTAL: L." + total);
	        	        	
	        				lv.setAdapter(adapter);
	        	        	
	        	        	break;
	        	        	
	        			}else{
	        				cursor.moveToNext();
	        			}
	        		}*/
	        		
	        		if(cant==cont){
	        			Toast toast = Toast.makeText(getApplicationContext(),
			    		"El producto no se encuentra!", Toast.LENGTH_SHORT);
				        toast.show();
	        		}
	        		
	        		//myDbHelper.close();
	        		//cursor.close();
	        		
	        		 
	        	  
	        	 //}catch(SQLException sqle){
	        	  
	        		 //throw sqle;
	        	  
	        	 //} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
	    		
	    	}else{
			    Toast toast = Toast.makeText(getApplicationContext(),
	    		"No se recibio scan data!", Toast.LENGTH_SHORT);
		        toast.show();
		    }
    	
    }
    
    
    public void saveHistory(View view) {
    	Toast toast = Toast.makeText(getApplicationContext(),
		"Se guardo el carrito!", Toast.LENGTH_SHORT);
        toast.show();
    }
    
    
    
    
    
}
