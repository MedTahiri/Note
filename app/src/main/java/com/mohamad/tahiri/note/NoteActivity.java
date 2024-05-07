package com.mohamad.tahiri.note;

import android.app.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import android.content.*;

public class NoteActivity extends Activity 
{
	ActionBar ab;
	EditText et;
	String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
		et = (EditText)findViewById(R.id.et);
		ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,100,0)));
		ab.setHomeButtonEnabled(true);
	
		int title = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView Title = (TextView)findViewById(title);
		Title.setTextColor(Color.parseColor("#ffffff"));
	    filename = getIntent().getExtras().get("filename").toString();
			ab.setSubtitle(filename);
		ReadFile();
    }

	private void ReadFile()
	{
		
			try
			{
				InputStream is = new BufferedInputStream(openFileInput(filename));
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				String content = new String(buffer,"UTF-8");
				et.setText(content);
			}
			catch (Exception e)
			{}
		
	}
	private void SaveFile()
	{
		FileOutputStream outputStream = null;
		try
		{
			outputStream = openFileOutput(filename,Context.MODE_PRIVATE);
			outputStream.write(et.getText().toString().getBytes());
			outputStream.close();

		}
		catch (Exception e)
		{}}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if((item.getItemId())==android.R.id.home){	
		SaveFile();
		 finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed()
	{
		SaveFile();
		super.onBackPressed();
	}
    
}
