//http://andreamconnell.blogspot.it/2012/02/working-with-esper-problems-and.html


import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.espertech.esper.client.*;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esperio.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;
import com.espertech.esperio.csv.CSVInputAdapterSpec;
import com.opencsv.CSVReader;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

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
			  int 	activity1;
			  int    activity2;
			  int 	timestamp;
			  
			  
		//NO CONSTRUCTOR IS NEEDED??
		public SensorEvent(){
			
		};
		
		
		public SensorEvent(int ph1, int ph2, int ir1, int fo1,int fo2,
							int di3,int di4,  int ph3,  int ph4, int ph5, int ph6, 
							int co1, int co2, int co3, int so1, int so2, int di1, 
							int	di2, int te1, int fo3, int activity1, int activity2 ,int timestamp){    
			 
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
               this.timestamp=timestamp;
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
		     public int getActivity1() {return activity1;}
		     public int getActivity2() {return activity2;}
		     public int getTimestamp() {return timestamp;}

		     
		     
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
		     public void setActivity1(int activity1) {this.activity1=activity1;}
		     public void setActivity2(int activity2) {this.activity2=activity2;}
		     public void setTimestamp(int timestamp) {this.timestamp=timestamp;}

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
	
	
	
	//this function
	public static int countOccurrences(String day,int activity) throws IOException{ 
		int count=0; //here we count the activity of our choice
		 //the activity we want to detect
		String [] nextLine;
		CSVReader reader=new  CSVReader(new FileReader("src/"+day));
		
		nextLine= reader.readNext(); //we need to skip the first line since it contains the header
		
		while((nextLine= reader.readNext())!=null){
			//we need to be sure that the activity strings contain only 2 chars i.e. the number of the activity
				if (nextLine[20].length()>2){
					nextLine[20]=nextLine[20].substring(0, 1);
				}
				
				if (nextLine[21].length()>2){
					nextLine[21]=nextLine[21].substring(0, 1);
				}
					//here we do the count
				if(Integer.parseInt(nextLine[20])==activity || Integer.parseInt(nextLine[21])==activity){
					count +=1;
				}//if ends
		}//while ends
		//System.out.println("occurrences of activity"+activity +"are=" +count);
		reader.close();
		return count;
	}
	
	
	
	public void run(){
		
		String day="DAY_3tmstp.csv";  //this is the day where we want to check  ////////////////////////////////////////////////////////////////////////////////////
		//watching_tv->12 having_shower->14 preparing_breakfast->4
		int activitynumber=12; //need to set the activity that we want to check	  /////////////////////////////////////////////////////////////////////////////////////
		
		String queryhavingshower="select a.activity1,a.activity2 from pattern[every(a=SensorEvent(co2=1))->b=SensorEvent(co3=1)]"; //DETECTS WHEN ONE OF THE RESIDENTS IS HAVING A SHOWER activity:14
		String querywatchingtv= "select c.activity1,c.activity2 from pattern ["
				+ "every(a=SensorEvent(ir1=1)->c=SensorEvent(fo1=1 or fo2=1 or so1=1)"
				+ "where timer:within(1 sec))]" ; //DETECTS WHEN ONE OF THE RESIDENTS IS WATCHING TV activity:12
		
		String querywatchingtv2="select b.activity1,b.activity2 "
									+ "from pattern["
									+ "every(b=SensorEvent(ir1=1)->c=SensorEvent(fo1=1 or fo2=1))]";
		
		
		
		String aqueryhavingbreakfast="select b.activity1,b.activity2 "
				+ "from pattern["																	//temperature of the kitchen
				+ "a=SensorEvent(ph3=1 and timestamp<43000)"
				+ "->"
				+ "every(b=SensorEvent(timestamp<43000 and (te1=1 or so2=1 or di3=1 or di4=1)))"
				+ "->"
				+ "c=SensorEvent((di3=1 or di4=1)and timestamp<43000)]"; //where timer:within(1);
		
		String querypreparingbreakfast="select b.activity1,b.activity2 "
				+ "from pattern["
				+ "a=SensorEvent(fo3=1)"
				+ "-> every(b=SensorEvent(ph3=1 and timestamp < 43000))"
				+ "where timer:within(1)]";
		
		String queryhavingbreakfast="select c.activity1,c.activity2 "
				+ "from pattern["
				+ "a=SensorEvent(fo3=1)"
				+ "-> every(b=SensorEvent(ph3=1 and timestamp < 43000))"
				+ "where timer:within(1)->every(c=SensorEvent((di3=1 or di4=1) and timestamp < 43000))]";
				
					//->d=SensorEvent(timestamp<43000)  
		
		String querypreparinglunch="select b.activity1,b.activity2 "
						+ "from pattern["
						+ "a=SensorEvent(ph3=1 and timestamp > 43000)"
						+ "-> every(b=SensorEvent(te1=1 and timestamp between 43000 and 60000))]";
						
		
		
		String querypreparingdinner="select b.activity1,b.activity2 "
				+ "from pattern["
				+ "a=SensorEvent(ph3=1 and timestamp > 67000)"
				+ "-> every(b=SensorEvent(te1=1 and timestamp between 67000 and 83000))]";
		
	
		String query=querywatchingtv;
		
		
		
		Configuration configuration = new Configuration();
		configuration.addEventType(SensorEvent.class);
		
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
		epService.initialize();
		
	//	AdapterInputSource source= new AdapterInputSource("DAY_2345.csv");
		
		EPAdministrator epAdmin= epService.getEPAdministrator();
				
		//Co2->bathroom door Co3->Shower Cabinet door
		EPStatement cepStatement2=epAdmin.createEPL(query);
		
		
		cepStatement2.addListener(new UpdateListener(){
			int count=0; //correctly detected activities
			int counttotal=0; //total detected activities
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////////
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				EventBean event = newEvents[0];
				try {
					FileWriter fw = new FileWriter("./output.csv");
				
				//EventBeanUtility.printEvent(event)->	Format the event and return a string representation.
				String output= EventBeanUtility.printEvent(event);
				StringBuilder outputparsed = new StringBuilder();
				counttotal+=1; //each time we receive an event we have to update counttotal->number of total
				char prevchar='b'; //important to not initialize it as digit or =
				int i=0;
				String activitytodetect=Integer.toString(activitynumber);
				boolean firstactivityread=false;
				
				StringBuilder activity1=new StringBuilder();
				StringBuilder activity2=new StringBuilder();
				
				// it works only with strings of the form: "#0  a.activity1 = 14\n"
				//											+"#1  a.activity2 = 2";
				for (i=0;i<output.length();i++){	
					if(output.charAt(i)=='\n' && firstactivityread==false){
						outputparsed.append(','); //we do this in order to separate the two activities
						firstactivityread=true;
					}//end first if	
					if(Character.isDigit(output.charAt(i))==true && (prevchar==' ' || Character.isDigit(prevchar))){
						outputparsed.append(output.charAt(i));
						if (firstactivityread==false){ //read first activity
							activity1.append(output.charAt(i));
						}
						if (firstactivityread==true){ //read second activity
							activity2.append(output.charAt(i));
						}	
					}//end second if
					prevchar=output.charAt(i);
				} //for ends here
				if(Objects.equals(activity1.toString(),activitytodetect)|| Objects.equals(activity2.toString(),activitytodetect))
				{count +=1;}
				System.out.println("activity1= "+ activity1.toString()+ "activity2= "+ activity2.toString());
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
				StringBuilder csvline= new StringBuilder();
				
				csvline.append(activity1.toString());
				csvline.append(",");
				csvline.append(activity2.toString());
				csvline.append(",");
				csvline.append(String.valueOf(count));
				csvline.append(",");
				csvline.append(String.valueOf(counttotal));
				csvline.append("\n");
				
				fw.write(csvline.toString());
				fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
				
				
		});
		//close the opened file

		
		//3600 sec/1 sec
		CSVInputAdapterSpec spec= new CSVInputAdapterSpec(new AdapterInputSource(day),"SensorEvent");
		spec.setEventsPerSec(1000);
		//spec.setTimestampColumn("timestamp");
		//spec.setUsingEngineThread(true);
		
		CSVInputAdapter inputAdapter= new CSVInputAdapter(epService,spec);
		inputAdapter.start();
		//Thread.Sleep(2000);
		
		
		
		///////////NOW PRECISION AND RECALL AND EVERYTHING
		String [] nextLine;
		CSVReader reader2;
		
		try {
			reader2 = new  CSVReader(new FileReader("output.csv")); //read the file where we put the count results
			nextLine= reader2.readNext(); //we need to skip the first line since it contains the header
			reader2.close();
			// the csv file has the form:
			//  last_activity1_read,last_activity1_read,total_correct_activities_detected,total_activities_detected
			int totalcorrectactivities; 
			int totalactivitiesdetected;
			int totalactivitiesday;
			
			totalcorrectactivities=Integer.parseInt(nextLine[2]);
			totalactivitiesdetected=Integer.parseInt(nextLine[3]);
			totalactivitiesday=ActivityRecognition.countOccurrences(day,activitynumber);
			
			System.out.println("total detected activities="+totalactivitiesdetected);
			System.out.println("total correct detected activities="+totalcorrectactivities);
			System.out.println("total activities of the same kind in the day= "+totalactivitiesday);
			System.out.println("precision="+ ((double)totalcorrectactivities/(int)totalactivitiesdetected +"%" ));
			System.out.println("recall="+ ((double)totalcorrectactivities/(int)totalactivitiesday) +"%" );

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
	}
	
	
	
	
	
	
	
	
}
	