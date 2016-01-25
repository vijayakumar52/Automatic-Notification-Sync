package com.example.gdataswitcher;

import java.util.Calendar;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.ActivityManager.RunningServiceInfo;
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

public class MainActivity1 extends ActionBarActivity implements OnClickListener {
	
	private Button btn1;
	private ImageButton ib1;
	 private Calendar cal1;
	 private int hour1=0;
	 private int min1=0;
	 private int hour11=0;
	 private int min11=0;
	 private int intervaal;
	 private int howlong;
	 private EditText et1,et2;
	 ToggleButton chkbox1;
	 TextView t1,t2,t3,t4;
	 String howlong2=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		 getSupportActionBar().setDisplayUseLogoEnabled(true);
		 getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(248,174,16)));
		   Typeface font1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "othercontent.TTF");
		
        setContentView(R.layout.activity_main1);
        btn1=(Button)findViewById(R.id.benter);
        chkbox1=(ToggleButton)findViewById(R.id.checkBox1);
       
        ib1=(ImageButton)findViewById(R.id.ib);
		et1=(EditText)findViewById(R.id.etinput);
		et2=(EditText)findViewById(R.id.et1);
		t1=(TextView)findViewById(R.id.tv1);
		t2=(TextView)findViewById(R.id.tv2);
		t3=(TextView)findViewById(R.id.tv3);
		t4=(TextView)findViewById(R.id.tvlast);
		t1.setTypeface(font1);
		t2.setTypeface(font1);
		t3.setTypeface(font1);
		t4.setTypeface(font1);
        cal1 = Calendar.getInstance();
		 hour1 = cal1.get(Calendar.HOUR_OF_DAY);
		  min1 = cal1.get(Calendar.MINUTE);
		  
		 boolean statt=isMyServiceRunning();
		 if(statt){
			 chkbox1.setChecked(true);
		 }else{
			 chkbox1.setChecked(false);
		 }
		  ib1.setOnClickListener(this);
		  btn1.setOnClickListener(this);
		  chkbox1.setOnClickListener(this);
		  if(chkbox1.isChecked()==false){
				
				 t1.setVisibility(View.INVISIBLE);
				 t2.setVisibility(View.INVISIBLE);
				 t3.setVisibility(View.INVISIBLE);
				 et1.setVisibility(View.INVISIBLE);
				 et2.setVisibility(View.INVISIBLE);
				 ib1.setVisibility(View.INVISIBLE);
				 btn1.setVisibility(View.INVISIBLE);
				 t4.setVisibility(View.VISIBLE);
				 
			}
		
    }
    public boolean isMyServiceRunning(){
    ActivityManager manager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
	  for(RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
		  if("com.example.gdataswitcher.TimeService".equals(service.service.getClassName())){
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
		actionBar.setTitle("WiFi Data");
		return true;
	}

	@SuppressWarnings("deprecation")
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
	final Intent aa=new Intent(this,TimeService.class);
		switch(arg0.getId()){

		case R.id.ib:
			TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    et2.setText( selectedHour +" "+"hours"+" "+"and"+" "+ selectedMinute+" "+"minutes");
                    hour1=selectedHour;
                    min1=selectedMinute;
                    hour11=hour1;
                    min11=min1;
                }
            }, hour1, min1, true);//Yes 24 hour time
            mTimePicker.setTitle("Hours : Minutes");
            mTimePicker.show();
			break;
		case R.id.benter:
			//boolean file=et1.isPressed();
			howlong2=et1.getText().toString();
			if((hour11>0||min11>0)&&howlong2.length()!=0){
			intervaal=(hour1*60*60*1000)+(min1*60*1000);
			
			howlong=Integer.parseInt(howlong2)*60*1000;
			intervaal=intervaal+howlong;
			Bundle basket=new Bundle();
			basket.putInt("secInterval", intervaal);
			basket.putInt("secHowLong", howlong);
			aa.putExtras(basket);
			new AlertDialog.Builder(this)
			 .setMessage("Internet will be Enabled after "+hour1+" hours and "+min1+" minutes"+"\n"+" and disabled after "+howlong2+" minutes")
			 .setCancelable(false)
			 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			     public void onClick(DialogInterface dialog, int id) {
				    	 startService(aa);
			     }
			 })
			 .setNegativeButton("Edit", null)
			 .show();
			
			}else{
				Toast.makeText(getApplicationContext(), "Please provide all the details", Toast.LENGTH_LONG).show();
			}

			break;
			
		case R.id.checkBox1:
			if(chkbox1.isChecked()==false){
				stopService(aa);
				 t1.setVisibility(View.INVISIBLE);
				 t2.setVisibility(View.INVISIBLE);
				 t3.setVisibility(View.INVISIBLE);
				 et1.setVisibility(View.INVISIBLE);
				 et2.setVisibility(View.INVISIBLE);
				 ib1.setVisibility(View.INVISIBLE);
				 btn1.setVisibility(View.INVISIBLE);
				 t4.setVisibility(View.VISIBLE);
				 
			}else{
				t1.setVisibility(View.VISIBLE);
				 t2.setVisibility(View.VISIBLE);
				 t3.setVisibility(View.VISIBLE);
				 et1.setVisibility(View.VISIBLE);
				 et2.setVisibility(View.VISIBLE);
				 ib1.setVisibility(View.VISIBLE);
				 btn1.setVisibility(View.VISIBLE);
				 t4.setVisibility(View.INVISIBLE);
			}
			break;
			
		}
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in1,R.anim.slide_down1);
	}
	
	

	}
