package com.example.beryl;

import com.example.beryl.DBAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class NewTask extends Activity {
	DBAdapter mDbAdapter;
	Long mRowId;
	EditText mTask;
	EditText mDesc;
	DatePicker mDate;
	Spinner mCategorry;
	Button mAdd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbAdapter = new DBAdapter(this);
		mDbAdapter.open();
		setContentView(R.layout.new_biz);
		
		
		mTask = (EditText)findViewById(R.id.txtTask);
		mDesc = (EditText)findViewById(R.id.txtDesc);
		mAdd = (Button)findViewById(R.id.btnAdd);
		
		mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(DBAdapter.KEY_ROW_ID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(DBAdapter.KEY_ROW_ID)
									: null;
		}
		
		populateFields();
		
		mAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK);
				finish();
				
			}
		});
	}
	
	private void populateFields() {
		if (mRowId != null) {
            Cursor c = mDbAdapter.fetchTask(mRowId);
            startManagingCursor(c);
            mTask.setText(c.getString(
                    c.getColumnIndexOrThrow(DBAdapter.KEY_TASK)));
            mDesc.setText(c.getString(
                    c.getColumnIndexOrThrow(DBAdapter.KEY_DESCRIPTION)));
            
        }
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(DBAdapter.KEY_ROW_ID, mRowId);
	}
	
	private void saveState() {
		String task = mTask.getText().toString();
		String desc = mDesc.getText().toString();		
        if (mRowId == null) {
            long id = mDbAdapter.createTask(task, desc);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbAdapter.updateTask(mRowId, task, desc);
        }
    }

}
