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
	
	
	public static void main(String[] args)
	{
		SimpleLayout layout= new SimpleLayout();
		ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
		Logger.getRootLogger().addAppender(appender);
		Logger.getRootLogger().setLevel((Level) Level.WARN);
		
		
		//we need to initialize the output.csv file to 0,0,0,0 otherwise if no events are detected, the file would not be 
		//accessed and the results contained would be relative to the previous detection
		try{
			StringBuilder csvline= new StringBuilder();
			FileWriter fw = new FileWriter("./output.csv");
			csvline.append("0");
			csvline.append(",");
			csvline.append("0");
			csvline.append(",");
			csvline.append("0");
			csvline.append(",");
			csvline.append("0");
			csvline.append("\n");
		
			fw.write(csvline.toString());
			fw.close();
		
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		//////////////////////////////////////////////////////////////////////////////////////////////////

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
		
		/*
		 In order to run the activity detection u need to set:
		 -) day=day in which to perform detection
		 -)activitynumber= number of the label of the activity to detect
		 -)house=number of the house where to perform the detection
		 -)query= the query we want to perform
		 */
		
		//			/House(A/B)_CSV/DAY_numbert.csv
		String day="/HouseA_CSV/DAY_13t.csv";  //this is the day where we want to check 
		int activitynumber=12; //need to set the activity that we want to check	  
		String house="A"; //CAN BE A or B
		
		/*ACTIVITIED
		 ID	ACTIVITY
         1	Other
         2	Going Out
         3	Preparing Breakfast
         4	Having Breakfast
         5	Preparing Lunch
         6	Having Lunch
         7	Preparing Dinner
         8	Having Dinner
         9	Washing Dishes
         10	Having Snack
         11	Sleeping
         12	Watching TV
         13	Studying
         14	Having Shower
         15	Toileting
         16	Napping
         17	Using Internet
         18	Reading Book
         19	Laundry
         20	Shaving
         21	Brushing Teeth
         22	Talking on the Phone
         23	Listening to Music
         24	Cleaning
         25	Having Conversation
         26	Having Guest
         27	Changing Clothes
		 */
		
///////////QUERIES HOUSE1////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**/		String queryhavingshower="select a.activity1,a.activity2 from pattern[every(a=SensorEventHouseA(co2=1))->b=SensorEventHouseA(co3=1)]"; //DETECTS WHEN ONE OF THE RESIDENTS IS HAVING A SHOWER activity:/**/
/**/	String querywatchingtv= "select c.activity1,c.activity2 from pattern ["                                                                                                                                    /**/
/**/			+ "every(a=SensorEventHouseA(ir1=1)->c=SensorEventHouseA(fo1=1 or fo2=1 or so1=1)"                                                                                                                 /**/
/**/			+ "where timer:within(1 sec))]" ; //DETECTS WHEN ONE OF THE RESIDENTS IS WATCHING TV activity:12                                                                                                   /**/
/**/	                                                                                                                                                                                                           /**/
/**/	String querywatchingtv2="select b.activity1,b.activity2 "                                                                                                                                                  /**/
/**/								+ "from pattern["                                                                                                                                                              /**/
/**/								+ "every(b=SensorEventHouseA(ir1=1)->c=SensorEventHouseA(fo1=1 or fo2=1))]";                                                                                                   /**/
/**/	                    //ultima aggiunta:                                                                                                                                                                                       /**/
/**/	 String querywatchingtv3 ="select b.activity1,b.activity2 "
		+ "from pattern["
		+ "every(a=SensorEventHouseA(ir1=1)) -> b=SensorEventHouseA(fo1=1 or fo2=1 or so1=1) and c=SensorEventHouseA(ir1=1)"
		+ "and " 
		+ "every(d=SensorEventHouseA(fo1=1 or fo2=1 or so1=1) "
		+ "and e=SensorEventHouseA(ir1=1))"
		+ "-> f=SensorEventHouseA(fo1=1 or fo2=1 or so1=1)"
		+ "]";                                                                                                                                                                                                         /**/
/**/	                                                                                                                                                                                                           /**/
/**/	String aqueryhavingbreakfast="select b.activity1,b.activity2 "                                                                                                                                             /**/
/**/			+ "from pattern["																	//temperature of the kitchen                                                                                   /**/
/**/			+ "a=SensorEventHouseA(ph3=1 and timestamp<43000)"                                                                                                                                                 /**/
/**/			+ "->"                                                                                                                                                                                             /**/
/**/			+ "every(b=SensorEventHouseA(timestamp<43000 and (te1=1 or so2=1 or di3=1 or di4=1)))"                                                                                                             /**/
/**/			+ "->"                                                                                                                                                                                             /**/
/**/			+ "c=SensorEventHouseA((di3=1 or di4=1)and timestamp<43000)]"; //where timer:within(1);                                                                                                            /**/
/**/	                                                                                                                                                                                                           /**/
/**/	String querypreparingbreakfast="select b.activity1,b.activity2 "                                                                                                                                           /**/
/**/			+ "from pattern["                                                                                                                                                                                  /**/
/**/			+ "a=SensorEventHouseA(fo3=1)"                                                                                                                                                                     /**/
/**/			+ "-> every(b=SensorEventHouseA(ph3=1 and timestamp < 43000))"                                                                                                                                     /**/
/**/			+ "where timer:within(1)]";                                                                                                                                                                        /**/
/**/	                                                                                                                                                                                                           /**/
/**/	String queryhavingbreakfast="select c.activity1,c.activity2 "                                                                                                                                              /**/
/**/			+ "from pattern["                                                                                                                                                                                  /**/
/**/			+ "a=SensorEventHouseA(fo3=1)"                                                                                                                                                                     /**/
/**/			+ "-> every(b=SensorEventHouseA(ph3=1 and timestamp < 43000))"                                                                                                                                     /**/
/**/			+ "where timer:within(1)->every(c=SensorEventHouseA((di3=1 or di4=1) and timestamp < 43000))]";                                                                                                    /**/
/**/			                                                                                                                                                                                                   /**/
/**/				//->d=SensorEventHouseA(timestamp<43000)                                                                                                                                                       /**/
/**/	                                                                                                                                                                                                           /**/
/**/	String querypreparinglunch="select b.activity1,b.activity2 "                                                                                                                                               /**/
/**/					+ "from pattern["                                                                                                                                                                          /**/
/**/					+ "a=SensorEventHouseA(ph3=1 and timestamp > 43000)"                                                                                                                                       /**/
/**/					+ "-> every(b=SensorEventHouseA(te1=1 and timestamp between 43000 and 60000))]";                                                                                                           /**/
/**/					                                                                                                                                                                                           /**/
/**/	                                                                                                                                                                                                           /**/
/**/	                                                                                                                                                                                                           /**/
/**/	String querypreparingdinner="select b.activity1,b.activity2 "                                                                                                                                              /**/
/**/			+ "from pattern["                                                                                                                                                                                  /**/
/**/			+ "a=SensorEventHouseA(ph3=1 and timestamp > 67000)"                                                                                                                                               /**/
/**/			+ "-> every(b=SensorEventHouseA(te1=1 and timestamp between 67000 and 83000))]";     
/**/	
		String querytoileting="select a.activity1,a.activity2 "
				+ "from pattern["
				+ "every(a=SensorEventHouseA(co2=1))->b=SensorEventHouseA(di2=1)"
				+ "where timer:within(1)"
				+ "]";
/**///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	



///////////QUERIES HOUSE2////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**//**/
/**/    String queryhavingshower2="select b.activity1,b.activity2 from pattern[every(a=SensorEventHouseB(so1=1))->every(b=SensorEventHouseB(co6=1))]";                                                                                          
/**/                                                                                               
/**/                                                                                            
/**/      				String queryhavingbreakfast2="select c.activity1,c.activity2 "                                                                                                                                              /**/
		/**/			+ "from pattern["                                                                                                                                                                                  /**/
		/**/			+ "a=SensorEventHouseA(fo3=1)"                                                                                                                                                                     /**/
		/**/			+ "-> every(b=SensorEventHouseA(ph3=1 and timestamp < 43000))"                                                                                                                                     /**/
/**/			+ "where timer:within(1)->every(c=SensorEventHouseA((di3=1 or di4=1) and timestamp < 43000))]";                                                                                           
/**/                                                                                               
/**/                                                                                               
/**/                  
/**/                  
/**/                  
/**/                  
/**/                  
/**/                  
/**/                  
/**/                  
/**/                  
/**/                                                                                               
/**/	                                                                                           
/**/	                                                                                           
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**/	                                                                                           
/**/	                                                                                           
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**/	                                                                                           
/**/	                                                                                           
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**/	                                                                                           
/**/	                                                                                           
/**/	                                                                                           
/**/                                                                                               
/**/                                                                                               
/**/                                                                                               
/**///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		String query=querywatchingtv3;//THIS IS THE QUERY WE WANT TO PERFORM
		
		
		
		Configuration configuration = new Configuration();
		
		if(Objects.equals(house,"A")){
		configuration.addEventType(SensorEventHouseA.class);
		}
		else if(Objects.equals(house,"B")){
		configuration.addEventType(SensorEventHouseB.class);	
		}
		
		
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
		epService.initialize();
		
		
		EPAdministrator epAdmin= epService.getEPAdministrator();
				
		EPStatement cepStatement2=epAdmin.createEPL(query);
		
		
		cepStatement2.addListener(new UpdateListener(){
			int count=0; //correctly detected activities
			int counttotal=0; //total detected activities

			
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

		
		CSVInputAdapterSpec spec= new CSVInputAdapterSpec(new AdapterInputSource(day),"SensorEventHouse"+house);

		spec.setEventsPerSec(1000);
		//spec.setTimestampColumn("timestamp");
		//spec.setUsingEngineThread(true);
		
		CSVInputAdapter inputAdapter= new CSVInputAdapter(epService,spec);
		inputAdapter.start();
		//Thread.Sleep(2000);
		
		
		
		///////////NOW PRECISION AND RECALL AND EVERYTHING
		String [] nextLine;
		CSVReader reader2;
		int correction=100;
		
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
			System.out.println("precision="+  correction*((double)totalcorrectactivities/(int)totalactivitiesdetected) +"%" );
			System.out.println("recall="+ correction*((double)totalcorrectactivities/(int)totalactivitiesday) +"%" );

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
	}
	
	
	
	
	
	
	
	
}
	