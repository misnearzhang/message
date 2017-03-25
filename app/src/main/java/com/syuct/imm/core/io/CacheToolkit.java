package com.syuct.imm.core.io;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheToolkit extends SQLiteOpenHelper {

	private static final String DATABASE_NAME  = "CIM_CONFIG_INFO.db";
	private static final int DATABASE_VERSION  = 20160406;

	private static final String TABLE_SQL  = "CREATE TABLE IF NOT EXISTS T_CIM_CONFIG (KEY VARCHAR(64) PRIMARY KEY,VALUE TEXT)";

	private static final String DELETE_SQL  = "DELETE FROM T_CIM_CONFIG WHERE KEY = ?";

	private static final String SAVE_SQL  = "INSERT INTO T_CIM_CONFIG (KEY,VALUE) VALUES(?,?)";

	private static final String QUERY_SQL  = "SELECT VALUE FROM T_CIM_CONFIG WHERE KEY = ?";

	public static final String CIM_CONFIG_INFO  = "CIM_CONFIG_INFO";
	
	public static final String KEY_ACCOUNT = "KEY_ACCOUNT";
	
	public static final String KEY_PASSWORD = "KEY_PASSWORD";
	
	
	public static final String KEY_ADDRESS = "KEY_ADDRESS";
	
	public static final String KEY_POINT = "KEY_POINT";
	
	
	public static final String KEY_NICKNAME = "KEY_NICKNAME";
	
	
	public static final String KEY_HEAD_URL = "KEY_HEAD_URL";
	
	
	public static final String KEY_SEX = "KEY_SEX";
	
	
	public static final String KEY_DECLARATION = "KEY_DECLARATION";
	
	
	public static final String KEY_AREA = "KEY_AREA";
	
	public static final String KEY_PLATFORM = "KEY_PLATFORM";
	
	public static final String KEY_MANUAL_STOP = "KEY_MANUAL_STOP";
	
	public static final String KEY_CIM_DESTROYED = "KEY_CIM_DESTROYED";
	
	public static final String KEY_CIM_SERVIER_HOST = "KEY_CIM_SERVIER_HOST";

	public static final String KEY_CIM_SERVIER_PORT = "KEY_CIM_SERVIER_PORT";
	
	public static final String KEY_CIM_CONNECTION_STATE = "KEY_CIM_CONNECTION_STATE";

	static CacheToolkit toolkit;
	public static CacheToolkit getInstance(Context context){
         if (toolkit==null){
			 toolkit = new CacheToolkit(context);
		 }
		return toolkit;
	}
	public CacheToolkit(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public CacheToolkit(Context context){
		this(context, DATABASE_NAME,null, DATABASE_VERSION);
	}



	public  void remove(String key)
	{
		SQLiteDatabase database = toolkit.getWritableDatabase();
		database.execSQL(DELETE_SQL,new String[]{key});
		database.close();
		toolkit.close();
		toolkit = null;
	}


	public  void putString(String key,String value)
	{
		SQLiteDatabase database = toolkit.getWritableDatabase();
		database.execSQL(DELETE_SQL,new String[]{key});
		database.execSQL(SAVE_SQL, new String[]{key, value});
		database.close();

		toolkit.close();
		toolkit = null;

	}
	
	public  String getString(String key)
	{
		String value = null;
		SQLiteDatabase database = toolkit.getWritableDatabase();
		Cursor cursor = database.rawQuery(QUERY_SQL, new String[]{key});
		if (cursor!=null&&cursor.moveToFirst())
		{
			value = cursor.getString(0);
		}

		cursor.close();
		database.close();
		toolkit.close();
		toolkit = null;
		
		return value;
	}
	
	public  void putBoolean(String key,boolean value)
	{
		putString(key,Boolean.toString(value));
	}
	
	public  boolean getBoolean(String key)
	{
		String value = getString(key);
		return value == null?false:Boolean.parseBoolean(value);
	}
	
	
	public  void putInt(String key,int value)
	{
		putString(key, String.valueOf(value));
	}
	
	public  int getInt(String key)
	{
		String value = getString(key);
		return value == null?0:Integer.parseInt(value);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

	}
}
