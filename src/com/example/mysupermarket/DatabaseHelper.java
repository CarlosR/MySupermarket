package com.example.mysupermarket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static String DB_PATH = "/data/data/com.example.mysupermarket/databases/";
	 
	 private static String DB_NAME = "BDD_Prueba";
	 
	 private SQLiteDatabase myDataBase;
	 
	 private final Context myContext;
	 
	 private boolean cartCreated;
	  

	 public DatabaseHelper(Context context) {
	  
		 super(context, DB_NAME, null, 1);
		 this.myContext = context;
	 }	
	 
	 
	public void createDataBase() throws IOException{
	 
		boolean dbExist = checkDataBase();
		 
		if(dbExist){
		
		}else{
		 
			this.getReadableDatabase();
		 
			try {
		 
				copyDataBase();
		 
			} catch (IOException e) {
		 
				throw new Error("Error copying database");
		 
			}
		}
	 
	}
	
	public void newCart(SQLiteDatabase myDB, String TableName, ArrayList<String> cart) throws IOException{
		 
		myDB.execSQL("CREATE TABLE IF NOT EXISTS "
			     + TableName
			     + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			     "cant TEXT NOT NULL,Contenido TEXT NOT NULL);");
		
		for(int i = 0; i<cart.size();i++){	
			myDB.execSQL("INSERT INTO"
					+ TableName  + " (cant, Contenido)"
				    + " VALUES (1, "+cart.get(i)+");");	
		}
	}
		 

	private boolean checkDataBase(){
	 
		SQLiteDatabase checkDB = null;
	 
		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
		}catch(SQLiteException e){
	 
	 
		}
	 
		if(checkDB != null){
	 
			checkDB.close();
	 
		}
	 
		return checkDB != null ? true : false;
	}
	 
	private void copyDataBase() throws IOException{
	 
		InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
		String outFileName = DB_PATH + DB_NAME;
	 
		OutputStream myOutput = new FileOutputStream(outFileName);
	 
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}
	 
		myOutput.flush();
		myOutput.close();
		myInput.close();
	 
	}
	 
	public void openDataBase() throws SQLException{
	 
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	}
	 
	@Override
	public synchronized void close() {
	 
	if(myDataBase != null)
		myDataBase.close();
	 
	super.close();
	 
	}
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
	 
	}
	 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 
	}
	 
	// Add your public helper methods to access and get content from the database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
	// to you to create adapters for your views.
	 
}

