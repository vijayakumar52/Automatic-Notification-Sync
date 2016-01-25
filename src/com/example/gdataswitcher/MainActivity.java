package com.example.gdataswitcher;

import java.util.Calendar;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.adsonik.autosync.R;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private Button btn19;
	private ImageButton ib19;
	 private Calendar cal19;
	 private int hour19=0;
	 private int min19=0;
	 private int hour191=0;
	 private int min191=0;
	 private EditText et19,et29;
	 ToggleButton chkbox19;
	 TextView t19,t29,t39,t49;
	 int intervaaal=0;
	 int howlongg=0;
	 TextViewView tvv;
	 String howlong1=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(248,174,16)));
		 getSupportActionBar().setDisplayShowTitleEnabled(true);
		 getSupportActionBar().setTitle("Mobile Data");
	   Typeface font1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "othercontent.TTF");
	   setContentView(R.layout.activity_main);
		
        btn19=(Button)findViewById(R.id.benter11);
        chkbox19=(ToggleButton)findViewById(R.id.checkBox11);
        ib19=(ImageButton)findViewById(R.id.ib11);
		et19=(EditText)findViewById(R.id.etinput11);
		et29=(EditText)findViewById(R.id.et11);
		t19=(TextView)findViewById(R.id.tv11);
		t29=(TextView)findViewById(R.id.tv12);
		t39=(TextView)findViewById(R.id.tv13);
		t49=(TextView)findViewById(R.id.tvlast11);
		t19.setTypeface(font1);
		t29.setTypeface(font1);
		t39.setTypeface(font1);
		t49.setTypeface(font1);
		
        cal19 = Calendar.getInstance();
		 hour19 = cal19.get(Calendar.HOUR_OF_DAY);
		  min19 = cal19.get(Calendar.MINUTE);
		  boolean statt=isMyServiceRunning();
			 if(statt){
				 chkbox19.setChecked(true);
			 }else{
				 chkbox19.setChecked(false);
			 }
		  ib19.setOnClickListener(this);
		  btn19.setOnClickListener(this);
		  chkbox19.setOnClickListener(this);
		  if(chkbox19.isChecked()==false){
				
				 t19.setVisibility(View.INVISIBLE);
				 t29.setVisibility(View.INVISIBLE);
				 t39.setVisibility(View.INVISIBLE);
				 et19.setVisibility(View.INVISIBLE);
				 et29.setVisibility(View.INVISIBLE);
				 ib19.setVisibility(View.INVISIBLE);
				 btn19.setVisibility(View.INVISIBLE);
				 t49.setVisibility(View.VISIBLE);
				 
			}
		
		
	}
	public boolean isMyServiceRunning(){
	    ActivityManager manager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
		  for(RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
			  if("com.example.gdataswitcher.TimeService1".equals(service.service.getClassName())){
				return true;
			  }
			  
		  }
		  return false;
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("Mobile Data");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		 case R.id.action_settings:
			 Intent id=new Intent("com.example.gdataswitcher.HELP");
				startActivity(id);
				break;
         case android.R.id.home:
             
             Intent upIntent = new Intent(this, AnimationTextClass.class);
             if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                
                 TaskStackBuilder.from(this)
                        
                         .addNextIntent(upIntent)
                         .startActivities();
                 finish();
             } else {
                 
                 NavUtils.navigateUpTo(this, upIntent);
             }
             return true;
     }

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		final Intent aa1=new Intent(this,TimeService1.class);
		switch(arg0.getId()){

		case R.id.ib11:
			TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                	et29.setText( selectedHour +" "+"hours"+" "+"and"+" "+ selectedMinute+" "+"minutes");
                    hour19=selectedHour;
                    min19=selectedMinute;
                    hour191=hour19;
                    min191=min19;
                }
            }, hour19, min19, true);//Yes 24 hour time
            mTimePicker.setTitle("Hours : Minutes");
            mTimePicker.show();
			break;
		case R.id.benter11:
			//boolean file1=et19.isPressed();
			howlong1=et19.getText().toString();
			 if((hour191>0||min191>0)&&howlong1.length()!=0){
				intervaaal=(hour19*60*60*1000)+(min19*60*1000);
				
				howlongg=Integer.parseInt(howlong1)*60*1000;
				intervaaal=intervaaal+howlongg;
			Bundle basket=new Bundle();
			basket.putInt("secInterval", intervaaal);
			basket.putInt("secHowLong", howlongg);
			aa1.putExtras(basket);
			new AlertDialog.Builder(this)
			 .setMessage("Internet will be Enabled after "+hour19+" hours and "+min19+" minutes"+"\n"+" and disabled after "+howlong1+" minutes")
			 .setCancelable(false)
			 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			     public void onClick(DialogInterface dialog, int id) {
				    	 startService(aa1);
			     }
			 })
			 .setNegativeButton("Edit", null)
			 .show();
			
			 }else{
    		Toast.makeText(getApplicationContext(), "Please provide all the details", Toast.LENGTH_LONG).show();
			 }
			
			break;
		case R.id.checkBox11:
			if(chkbox19.isChecked()==false){
				stopService(aa1);
				 t19.setVisibility(View.INVISIBLE);
				 t29.setVisibility(View.INVISIBLE);
				 t39.setVisibility(View.INVISIBLE);
				 et19.setVisibility(View.INVISIBLE);
				 et29.setVisibility(View.INVISIBLE);
				 ib19.setVisibility(View.INVISIBLE);
				 btn19.setVisibility(View.INVISIBLE);
				 t49.setVisibility(View.VISIBLE);
				 
			}else{
				t19.setVisibility(View.VISIBLE);
				 t29.setVisibility(View.VISIBLE);
				 t39.setVisibility(View.VISIBLE);
				 et19.setVisibility(View.VISIBLE);
				 et29.setVisibility(View.VISIBLE);
				 ib19.setVisibility(View.VISIBLE);
				 btn19.setVisibility(View.VISIBLE);
				 t49.setVisibility(View.INVISIBLE);
			}
			break;
			
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_down,R.anim.slide_in);
	}
	
	
}
