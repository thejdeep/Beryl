package com.example.beryl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter 
{
	private static final String DATABASE_TABLE = "task_data";
	public static final String KEY_ROW_ID = "_id";
	public static final String KEY_TASK = "task";
	public static final String KEY_DESCRIPTION = "description";
	
	
	SQLiteDatabase mDb;
	Context mCtx;
	DBHelper mDbHelper;
	
	public DBAdapter(Context context)
	{
		this.mCtx = context;
	}

	public DBAdapter open() throws SQLException
	{
		mDbHelper = new DBHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		mDbHelper.close();
	}
	
	public long createTask(String task,String desciption)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TASK, task);
		initialValues.put(KEY_DESCRIPTION, desciption);
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public boolean deleteTask(long id)
	{
		return mDb.delete(DATABASE_TABLE, KEY_ROW_ID + " = " + id, null) > 0;
	}
	
	public boolean updateTask(long id,String task,String desciption)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TASK, task);
		initialValues.put(KEY_DESCRIPTION, desciption);
		return mDb.update(DATABASE_TABLE, initialValues, KEY_ROW_ID + " = " + id, null) > 0;
	}
	
	public Cursor fetchAllTasks()
	{
		return mDb.query(DATABASE_TABLE, new String[]{KEY_ROW_ID,KEY_TASK,KEY_DESCRIPTION}, null, null, null, null, null);
	}
	
	public Cursor fetchTask(long id)
	{
		Cursor c = mDb.query(DATABASE_TABLE, new String[]{KEY_ROW_ID,KEY_TASK,KEY_DESCRIPTION}, KEY_ROW_ID + " = " + id, null, null, null, null);
		if(c != null)
		{
			c.moveToFirst();
		}
		return c;
	}
}
