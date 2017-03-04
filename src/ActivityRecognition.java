//http://andreamconnell.blogspot.it/2012/02/working-with-esper-problems-and.html


import com.espertech.esper.client.*;
import com.espertech.esperio.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;
//import com.espertech.esper.util.ObjectInputStreamWithTCCL
import com.espertech.esperio.csv.CSVInputAdapterSpec;

import java.util.Random; 
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.ConsoleAppender; 
import org.apache.log4j.SimpleLayout; 
import org.apache.log4j.Level; 
import org.apache.log4j.Logger;





public class ActivityRecognition implements Runnable{
    private static final Log log = LogFactory.getLog(ActivityRecognition.class);

	

	
	
	//let's define a class to store the sensor entries
	
	public  static class SensorEvent
	{
//							ID				SENSOR TYPE			PLACE
											
		/*1*/ int		ph1;		//	Photocell 			Wardrobe
		/*2*/ int		ph2;		//	Photocell 			Convertible Couch (Used as bed for Resident 2)
		/*3*/ int		ir1;		//	IR 				    TV receiver
		/*4*/ int		fo1;		//	Force Sensor 		Couch
		/*5*/ int		fo2;		//	Force Sensor 	    Couch
		/*6*/ int		di3;		//	Distance  			Chair
		/*7*/ int		di4; 		//	Distance		    Chair
		/*8*/ int		ph3;		//	Photocell 			Fridge
		/*9*/ int		ph4;		//	Photocell			Kitchen Drawer
		/*10*/int		ph5;		//	Photocell			Wardrobe
		/*11*/int		ph6;		//	Photocell			Bathroom Cabinet
		/*12*/int		co1;		//	Contact Sensor 		House Door
		/*13*/int		co2;		//	Contact Sensor		Bathroom Door
		/*14*/int		co3;		//	Contact Sensor 		Shower Cabinet Door
		/*15*/int		so1;		//	Sonar Distance 		Hall
		/*16*/int		so2;		//	Sonar Distance 		Kitchen
		/*17*/int		di1;		//	Distance			Tap
		/*18*/int		di2;		//	Distance			Water Closet
		/*19*/int		te1;		//	Temperature  		Kitchen
		/*20*/int		fo3;		//	Force Sensor 		Bed
			  double 	activity1;
			  double    activity2;
			  
			  
		//NO CONSTRUCTOR IS NEEDED??
		public SensorEvent(){
			
		};
		
		
		public SensorEvent(int ph1, int ph2, int ir1, int fo1,int fo2,
							int di3,int di4,  int ph3,  int ph4, int ph5, int ph6, 
							int co1, int co2, int co3, int so1, int so2, int di1, 
							int	di2, int te1, int fo3, double activity1, double activity2 ){    
			 
               this.ph1=ph1;		
               this.ph2=ph2;		
               this.ir1=ir1;		
               this.fo1=fo1;		
               this.fo2=fo2;		
               this.di3=di3;		
               this.di4=di4;		
               this.ph3=ph3;		
               this.ph4=ph4;		
               this.ph5=ph5;		
               this.ph6=ph6;		
               this.co1=co1;		
               this.co2=co2;		
               this.co3=co3;		
               this.so1=so1;		
               this.so2=so2;
               this.di1=di1;
               this.di2=di2;		
               this.te1=te1;		
               this.fo3=fo3;
               this.activity1=activity1;
               this.activity2=activity2;
			   }
		          
			  //GETTERS
		     public int getPh1() { return ph1;}
		     public int getPh2() { return ph2;}
		     public int getIr1() { return ir1;}
		     public int getFo1() { return fo1;}
		     public int getFo2() { return fo2;}
		     public int getDi3() { return di3;}
		     public int getDi4() { return di4;}
		     public int getPh3() { return ph3;}
		     public int getPh4() { return ph4;}
		     public int getPh5() { return ph5;}
		     public int getPh6() { return ph6;}
		     public int getCo1() { return co1;}
		     public int getCo2() { return co2;}
		     public int getCo3() { return co3;}
		     public int getSo1() { return so1;}
		     public int getSo2() { return so2;}
		     public int getDi1() { return di1;}
		     public int getDi2() { return di2;}
		     public int getTe1() { return te1;}
		     public int getFo3() { return fo3;}
		     public double getActivity1() {return activity1;}
		     public double getActivity2() {return activity2;}
		     
		     
		     public void setPh1(int ph1) { this.ph1=ph1;}
		     public void setPh2(int ph2) { this.ph2=ph2;}
		     public void setIr1(int ir1) { this.ir1=ir1;}
		     public void setFo1(int fo1) { this.fo1=fo1;}
		     public void setFo2(int fo2) { this.fo2=fo2;}
		     public void setDi3(int di3) { this.di3=di3;}
		     public void setDi4(int di4) { this.di4=di4;}
		     public void setPh3(int ph3) { this.ph3=ph3;}
		     public void setPh4(int ph4) { this.ph4=ph4;}
		     public void setPh5(int ph5) { this.ph5=ph5;}
		     public void setPh6(int ph6) { this.ph6=ph6;}
		     public void setCo1(int co1) { this.co1=co1;}
		     public void setCo2(int co2) { this.co2=co2;}
		     public void setCo3(int co3) { this.co3=co3;}
		     public void setSo1(int so1) { this.so1=so1;}
		     public void setSo2(int so2) { this.so2=so2;}
		     public void setDi1(int di1) { this.di1=di1;}
		     public void setDi2(int di2) { this.di2=di2;}
		     public void setTe1(int te1) { this.te1=te1;}
		     public void setFo3(int fo3) { this.fo3=fo3;}
		     public void setActivity1(double activity1) {this.activity1=activity1;}
		     public void setActivity2(double activity2) {this.activity2=activity2;}
		     
		     //SETTERS
		
		//     @Override public String toString(){
		  //  	 return "Time_instant:" + Time_instant.toString(); //non funge, boh
		   //  }
		}//SensorEvent
		     
			//CAPIRE BENE STO PEZZO
	
	
	public static void main(String[] args)
	{
		SimpleLayout layout= new SimpleLayout();
		ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
		Logger.getRootLogger().addAppender(appender);
		Logger.getRootLogger().setLevel((Level) Level.WARN);
		
		new ActivityRecognition().run();
	}
	
	
	
	
	public ActivityRecognition(){		
	}
	
	public void run(){
		Configuration configuration = new Configuration();
		configuration.addEventType(SensorEvent.class);
		
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
		epService.initialize();
		
		AdapterInputSource source= new AdapterInputSource("DAY_1.csv");
		
		EPAdministrator epAdmin= epService.getEPAdministrator();
		/*EPStatement cepPattern=epAdmin.createPattern("every (a=SensorEvent(ir1=1)"
				+ "->"
				+ "b=SensorEvent(so1=1)"
				+ "where timer:within(1000 ))");
				*/
		
		EPStatement cepStatement1=epAdmin.createEPL("select c.activity1,c.activity2 from pattern ["
				+ "every(a=SensorEvent(ir1=1)->c=SensorEvent(fo1=1 or fo2=1 or so1=1)"
				+ "where timer:within(1 sec))]" );
				
				
				
	//	EPStatement cepStatement1=epAdmin.createEPL("select ir1,so1 from SensorEvent(activity1=12 or activity2=12).win:time(1 sec)" );
		
		
		/*
		EPStatement cepStatement1=epAdmin.createEPL("select activity1,activity2 from "
				+ "SensorEvent(so1=1).win:length(8000)"
				+ "output snapshot every 8000 events");
		*/
		
		cepStatement1.addListener(new UpdateListener(){
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				EventBean event = newEvents[0];
				
				System.out.println("Found Match:"+event.getUnderlying() );
			}
		});
		
		//CSVInputAdapter input1= new CSVInputAdapter(epService, source,"SensorEvent");
		//input1.start();
		
		
		
		// /*
		//3600 sec/1 sec
		CSVInputAdapterSpec spec= new CSVInputAdapterSpec(new AdapterInputSource("DAY_1.csv"),"SensorEvent");
		spec.setEventsPerSec(1000);
		//spec.setTimestampColumn("timestamp");
		//spec.setUsingEngineThread(true);
		
		CSVInputAdapter inputAdapter= new CSVInputAdapter(epService,spec);
		inputAdapter.start();
		//Thread.Sleep(2000);
		
//*/
	}
}
	