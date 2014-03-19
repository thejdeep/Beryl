package com.example.beryl;

import com.example.beryl.DBAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ToDo extends ListActivity {
    DBAdapter mDbAdapter;
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    
    public static final int INSERT_ID = Menu.FIRST;  
    public static final int DELETE_ID = Menu.FIRST + 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbAdapter = new DBAdapter(this);
        mDbAdapter.open();
        fillData();
        registerForContextMenu(getListView());
        
    }

	public void create()
	{
		Intent i = new Intent(this,NewTask.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}
	
	public void fillData()
	{
		Cursor c = mDbAdapter.fetchAllTasks();
		
		String []from = new String[]{DBAdapter.KEY_TASK,DBAdapter.KEY_DESCRIPTION};
		int [] to = new int[]{R.id.row};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, c, from, to);
		setListAdapter(adapter);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fillData();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			mDbAdapter.deleteTask(info.id);
			fillData();
			return true;
		}
	   return super.onContextItemSelected(item);
	   
	   
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.delete);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.add);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case INSERT_ID:
			create();
			return true;
		
		
		}
			
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this,NewTask.class);
		i.putExtra(DBAdapter.KEY_ROW_ID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
	}
    
    
}