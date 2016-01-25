package com.example.gdataswitcher;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.adsonik.autosync.R;


public class AnimationTextClass extends ActionBarActivity implements OnClickListener
{

	private LovelyView1 myView1;
	ColoredButton btnRed;
    ColoredButton btnGreen;
    Button btnSwitch;
    Animation an1,an2,an3,an4,an5;
    TextView ttt;

    boolean switched = false;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Typeface font4 = Typeface.createFromAsset(getApplicationContext().getAssets(), "othercontent.TTF");
	
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		
		 getSupportActionBar().setLogo(R.drawable.ic_launcher1);
		 getSupportActionBar().setTitle("Automatic Notification Synchronization");
		 //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		 getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(248, 174, 16)));
		//getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		 an1=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
		 an2=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
		 an3=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
		
		setContentView(R.layout.animationtextclass);
		int padding = (int)(16 * getResources().getDisplayMetrics().density);
		myView1=(LovelyView1)findViewById(R.id.custView1);
		myView1.setPadding(padding, padding, padding, padding);
		myView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28.0f);
		myView1.setTextColor(Color.BLACK);
		myView1.setTypeface(font4);
		 myView1.setAnimation(an1);
		myView1.setText("How would you like to enable the internet connection?");
		ttt=(TextView)findViewById(R.id.tvhelp);
		


	 //  myView1.setTextSize(20);
		initButtons();
		
		btnRed.setTextSize(25);
		btnGreen.setTextSize(25);
		btnRed.setOnClickListener(this);
		btnGreen.setOnClickListener(this);
		ttt.setOnClickListener(this);
		an1.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				 btnGreen.startAnimation(an2);
				
			}
		});
		
an2.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				 btnRed.startAnimation(an3);
				
			}
		});

		//myView1.setAnimation(an1);
		
	}
	
	private void initButtons() {
        btnRed = (ColoredButton) findViewById(R.id.btnRed);
        btnGreen = (ColoredButton) findViewById(R.id.btnGreen);
       
     

  
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		 case R.id.action_settings:
			 Intent id=new Intent("com.example.gdataswitcher.HELP");
				startActivity(id);
				break;
		 }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
    switch(arg0.getId()){
    case R.id.btnRed:
    
				Intent i=new Intent("com.example.gdataswitcher.MAINACTIVITY");
				startActivity(i);
				overridePendingTransition(R.anim.slide_down,R.anim.slide_in);

		
		
    	break;
    case R.id.btnGreen:
    	
   				Intent i1=new Intent("com.example.gdataswitcher.MAINACTIVITY1");
   				startActivity(i1);
   				overridePendingTransition(R.anim.slide_down,R.anim.slide_in);

    	break;
    case R.id.tvhelp:
    			
    			Intent i2=new Intent("com.example.gdataswitcher.HELP");
    			startActivity(i2);
	
	}

}

	@Override
	public void onBackPressed() {
		AnimationTextClass.this.finish();
	}
	
	
}
