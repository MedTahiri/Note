package com.mohamad.tahiri.note;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.view.*;
import android.content.*;
import android.content.res.*;
import android.view.View.*;
import android.widget.AdapterView.*;
import java.security.*;
import java.io.*;
import android.widget.SearchView.*;

public class MainActivity extends Activity 
{
	final Context context = this;
	ActionBar ab;
	GridView lv;
	String[] files;
	ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		lv = (GridView) findViewById(R.id.lv);
		ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 100, 0)));
		int title = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView Title = (TextView)findViewById(title);
		Title.setTextColor(Color.parseColor("#ffffff"));
	
    }

	@Override
	protected void onResume()
	{
	    FillListView();
		super.onResume();
	}

	private void FillListView()
	{
		files = getFilesDir().list();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					Intent i = new Intent(getApplicationContext(), NoteActivity.class);
					String fname = files[p3];
					i.putExtra("filename", fname);
					startActivity(i);
				}
			});
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
                    deletefile(files[p3]);
					return true;
				}

				private void deletefile(final String fname)
				{
					// TODO: Implement this method
					AlertDialog.Builder adb = new AlertDialog.Builder(context);

					adb.setMessage("delete note");
					AlertDialog ad = adb.create();
					ad.setButton("Cancel", new DialogInterface.OnClickListener(){ public void onClick(DialogInterface p1 , int p2)
							{
								p1.cancel();
							}
						}
					);
					ad.setButton2("Delete", new DialogInterface.OnClickListener(){ public void onClick(DialogInterface p1 , int p2)
							{ 
								File f = new File(getFilesDir(), fname); 
								try
								{
									f.delete(); 
								}
								catch (Exception e)
								{}
								FillListView();
							}
						}
					);
					ad.show();	
				}
			}
		);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		getMenuInflater().inflate(R.menu.sv, menu);
		MenuItem mi = menu.findItem(R.id.sss);
		SearchView sv= (SearchView) mi.getActionView();
		sv.setOnQueryTextListener(
			new OnQueryTextListener(){
				@Override
				public boolean onQueryTextSubmit(String p1)
				{
					return false;
				}
				@Override
				public boolean onQueryTextChange(String ppp)
				{
					adapter.getFilter().filter(ppp);
					return false;
				}
			}
		);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.add:
				addnote();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void addnote()
	{
		LayoutInflater li = LayoutInflater.from(context);
		View view = li.inflate(R.layout.custom_add, null);
		AlertDialog.Builder adb = new AlertDialog.Builder(context);
		adb.setView(view);
		adb.setMessage("entri note titel");
		final EditText et = (EditText)view.findViewById(R.id.customaddEditText1);
        AlertDialog ad = adb.create();
		ad.setButton("Cancel", new DialogInterface.OnClickListener(){ public void onClick(DialogInterface p1 , int p2)
				{
					p1.cancel();
				}
			}
		);
		ad.setButton2("Create", new DialogInterface.OnClickListener(){ public void onClick(DialogInterface p1 , int p2)
				{
					String fname = et.getText().toString(); 
					Intent i = new Intent(getApplicationContext(), NoteActivity.class);
					i.putExtra("filename", fname); 
					startActivity(i);}});
		ad.show();
	}

}
