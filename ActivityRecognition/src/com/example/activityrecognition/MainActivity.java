package com.example.activityrecognition;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import com.practice.cos.R;
public class MainActivity extends Activity {
	private  SensorManager sm;
	
	
	private float X_lateral;
	private float Y_longitudinal;
	private float Z_vertical;
	private float x[],y[],z[];
	private float features[];
	private Timer timer = new Timer();
    int i=0;
    Button stop;
    Button start;
    TextView textview;
    private static final String tag="wang";
    TimerTask task = new TimerTask( ) {

	public void run () {

	Message message = new Message( );

	message.what = 1;

	handler.sendMessage(message);
    //System.out.println("afs");
	}
	
	};

	final Handler handler = new Handler() {

		public void handleMessage(Message msg) {

		switch (msg.what) {

		case 1:
		write();
		break;

		}
		super.handleMessage(msg);
		}
		
		};
		
		private void write()
		{
			if(i<200)
			{
				x[i]=this.X_lateral;
				y[i]=this.Y_longitudinal;
				z[i]=this.Z_vertical;
				i++;
			}
			else
			{
				Log.v("wang","finish");
			}
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x=new float[200];
		y=new float[200];
		z=new float[200];
		features=new float[39];
		 setContentView(R.layout.activity_main);
		  stop=(Button)findViewById(R.id.finishButton);
	      start=(Button)findViewById(R.id.startButton);
	     textview=(TextView)findViewById(R.id.textView);
	     start.setOnClickListener(  new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		
	        			        		        	     		
	        			 sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	        		     int sensorType = Sensor.TYPE_ACCELEROMETER;        
	        		     sm.registerListener(myAccelerometerListener,sm.getDefaultSensor(sensorType),SensorManager.SENSOR_DELAY_FASTEST);
	        		     timer.schedule(task,1000,50);
	        	     	
	        	}
	        });
	        stop.setOnClickListener(  new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		
	        		sm.unregisterListener(myAccelerometerListener);
	                timer.cancel();
	                
	              try{
	            	  getfeatures();
	            	  
	                  String str= getresult();
	                                 
	                  textview.setText("识别结果"+str);
	                 }
	              catch (Exception e) {
	            	   textview.setText("error");
	            	                      }
	             }
	        }
	        );
	}; // oncreate finish
	
	void getfeatures()
	{  
		
		Getdata g=new Getdata();
		features=g.getfeatures(x, y, z);
	}
	String getresult() throws Exception
	{   Log.v(tag,"str");
		 Wekaclassifier classifier=new Wekaclassifier(getResources().openRawResource(R.raw.activityrecognition));
		 Log.v(tag,"aaaaa");
		 return classifier.getResult(features); 
	};
	    final SensorEventListener myAccelerometerListener = new SensorEventListener(){
	    	
	    	public void onSensorChanged(SensorEvent sensorEvent){
	    		if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){ 			
	    	  	    X_lateral = sensorEvent.values[0];
	    	        Y_longitudinal = sensorEvent.values[1];
	    		    Z_vertical = sensorEvent.values[2];
	    		   if(i<200) 
	    			textview.setText("X="+X_lateral);	    			//T.setText(" ");
	    		}
	    	}
	    	//澶嶅啓onAccuracyChanged鏂规硶

			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
	    	
	      };
}

