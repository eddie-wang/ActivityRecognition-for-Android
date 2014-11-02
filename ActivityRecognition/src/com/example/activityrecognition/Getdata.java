package com.example.activityrecognition;


import java.io.PrintWriter;

import android.util.Log;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wallace
 */
public class Getdata
{  
    float [] x=new float [200];
    float [] y=new float [200];
    float [] z=new float [200];
    float [] average=new float[3];
    float [] standardDeviation=new float [3];
    float [] averageAbsoluteDifference=new float[3] ;
    float averageResultantAcc =0;
    float [] binnedDistribution=new float[30];
	float [] features= new float[39];
	//double max,min;

    /**
     *
     * @param args
     * @throws Exception
     */
	float [] getfeatures(float x[], float y[],float z[])
	{
			
		readDateArray(x,y,z);
		
		this.getAverage();
		this.getAverageAbsoluteDifference();
		this.getBinnedDistribution();
		this.getStandardDeviation();
		
		features[0]=average[0];
        features[1]=average[1];
        features[2]=average[2];
        features[3]=standardDeviation[0];
        features[4]=standardDeviation[1];
        features[5]=standardDeviation[2];
        features[6]=averageAbsoluteDifference[0];
        features[7]=averageAbsoluteDifference[1];
        features[8]=averageAbsoluteDifference[2];
    
        int i;
        for(i=0;i<30;i++)
        {
        	features[9+i]=binnedDistribution[i];
        }
        
        return features;
	}
	
	void readDateArray (float x[], float y[],float z[])
	{
		 this.x=x;
		 this.y=y;
		 this.z=z;
	}
	void getAverage()
	{
       int count=0;
       float averagex=0,averagey=0,averagez=0;
		while(count<200)
		{
			averagex+=x[count];
			averagey+=y[count];
			averagez+=z[count];
			count++;
		}
          average[0]=averagex/200;
          average[1]=averagey/200;
          average[2]=averagez/200;
    }
    void getStandardDeviation()
    {
    	int count=0;
    	float tx=0,ty=0,tz=0;
    	while(count<200)
    	{
            tx+=Math.pow((x[count]-average[0]),2);
            ty+=Math.pow((y[count]-average[1]),2);
            tz+=Math.pow((z[count]-average[2]),2);           
    		count++;
    	}
         standardDeviation[0]=(float)Math.sqrt(tx/200);   
         standardDeviation[1]=(float)Math.sqrt(ty/200);  
         standardDeviation[2]=(float)Math.sqrt(tz/200);   	
    }
    void getAverageAbsoluteDifference()
    {
            int count=0;
            float tx=0,ty=0,tz=0;
            while(count<200)
            {
                tx+=Math.abs(x[count]-average[0]);
                ty+=Math.abs(y[count]-average[1]);
                tz+=Math.abs(z[count]-average[2]);   
            	count++;
            }
           averageAbsoluteDifference[0]=tx/200;
           averageAbsoluteDifference[1]=ty/200;
           averageAbsoluteDifference[2]=tz/200;
    }
     void getAverageResultantAcc()
     {

     	int count=0;
     	float t=0;
     	while(count<200)
     	{
     		t+=Math.sqrt(Math.pow(x[count],2)+Math.pow(y[count],2)+Math.pow(z[count],2));
     		count++;
     	}
     	averageResultantAcc=t/200;
        
     }
     void  getBinnedDistribution()
     {
        int i;
        float [] max=new float[]{-100,-100,-100};
        float [] min=new float[]{100,100,100};
        //double tmax=0,tmin=10000;
        for(i=0;i<200;i++)
        {
              if(x[i]>max[0])
              	max[0]=x[i];
              else 
              	if(x[i]<min[0])
              		min[0]=x[i];
              
              if(y[i]>max[1])
              	max[1]=y[i];
              else 
              	if(y[i]<min[1])
              		min[1]=y[i];
              
              if(z[i]>max[2])
              	max[2]=z[i];
              else 
              	if(z[i]<min[2])
              		min[2]=z[i];			       
        }
        double tx=(max[0]-min[0])/10;
        double ty=(max[1]-min[1])/10;
        double tz=(max[2]-min[2])/10;
       
         for(i=0;i<200;i++)
         {
                binnedDistribution[(int)((x[i]-min[0])/tx)]++;
         	binnedDistribution[(int)(10+(y[i]-min[1])/ty)]++;
         	try{
         	binnedDistribution[(int)(20+(z[i]-min[2])/tz)]++;
         	}
         	catch(Exception e)
         	{
         		binnedDistribution[29]++;
         	}
         	
         }
           
     }
     void printResult(PrintWriter out)
     {
  
          out.print(average[0]);
          out.print(",");
          out.print(average[1]);
          out.print(",");
          out.print(average[2]);
          out.print(",");
          out.print(standardDeviation[0]);
          out.print(",");
           out.print(standardDeviation[1]);
          out.print(",");
           out.print(standardDeviation[2]);
          out.print(",");
           out.print(averageAbsoluteDifference[0]);
          out.print(",");
           out.print(averageAbsoluteDifference[1]);
          out.print(",");
           out.print(averageAbsoluteDifference[2]);
          out.print(",");
          int i;
          for(i=0;i<30;i++)
          {
           out.print(binnedDistribution[i]);
           out.print(",");
          }
          out.print("stand");
          out.print("\n");
     }
              
}