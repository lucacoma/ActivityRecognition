
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.espertech.esper.client.*;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esperio.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;
import com.espertech.esperio.csv.CSVInputAdapterSpec;
import com.opencsv.CSVReader;


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
		//initialization done

		new ActivityRecognition().run();
	}
	
	
	
	
	public ActivityRecognition(){	
		
	}
	
	
	
	//this returns  the number of events labeled with the number of the chosen activity during the chosen day:
	
	public static int countOccurrences(String day,int activity) throws IOException{ 
		
		int count=0; //here we count the activity of our choice
		String [] nextLine;
		
		CSVReader reader=new  CSVReader(new FileReader("src/"+day)); //day must be contained in the src directory, if u change directory u must also change the argument passed to filereader
		
		nextLine= reader.readNext(); //we need to skip the first line since it contains the header
		
		//each line of the csv file is parsed and the labels are counted
		while((nextLine= reader.readNext())!=null){
			//we need to be sure that the activity strings contain only 2 chars i.e. the number of the activity:
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

		reader.close();
		return count;
	}
	
	
	/*\*************************************************************************************** *
	 *  In order to run the activity detection u need to set the following in the run method:   *
	 *  -) day=day in which to perform detection                                                *
	 *  -)activitynumber= number of the label of the activity to detect                         *
	 *  -)house=number of the house where to perform the detection                              *
	 *  -)query= the query we want to perform                                                   *
	 ***************************************************************************************** */
	
	public void run(){
		
		
		String day="/HouseB_CSV/DAY_2.csv";  //this is the day where we want to check 
		int activitynumber=4; //need to set the activity that we want to check	  
		String house="B"; //CAN BE A or B depending on which house we want to consider
        String query;    //this contains the query that we want to perform  

		
		/*ACTIVITIES*******************
		* ID	ACTIVITY              *
        * 1	Other                     *
        * 2	Going Out                 *
        * 3	Preparing Breakfast       *
        * 4	Having Breakfast          *
        * 5	Preparing Lunch           *
        * 6	Having Lunch              *
        * 7	Preparing Dinner          *
        * 8	Having Dinner             *
        * 9	Washing Dishes            *
        * 10	Having Snack          *
        * 11	Sleeping              *
        * 12	Watching TV           *
        * 13	Studying              *
        * 14	Having Shower         *
        * 15	Toileting             *
        * 16	Napping               *
        * 17	Using Internet        *
        * 18	Reading Book          *
        * 19	Laundry               *
        * 20	Shaving               *
        * 21	Brushing Teeth        *
        * 22	Talking on the Phone  *
        * 23	Listening to Music    *
        * 24	Cleaning              *
        * 25	Having Conversation   *
        * 26	Having Guest          *
        * 27	Changing Clothes      *
		*******************************/
		
//QUERIES HOUSE A:
		String queryhavingshowerA="select a.activity1,a.activity2 from pattern[every(a=SensorEventHouseA(co2=1))->b=SensorEventHouseA(co3=1)]"; //DETECTS WHEN ONE OF THE RESIDENTS IS HAVING A SHOWER activity:/**/
	
		//bisogna scegliere la migliore per watching tv, forse è la 2
		String querywatchingtvA1= "select c.activity1,c.activity2 from pattern ["                                                                                                                                    /**/
			+ "every(a=SensorEventHouseA(ir1=1)->c=SensorEventHouseA(fo1=1 or fo2=1 or so1=1)"                                                                                                                 /**/
			+ "where timer:within(1 sec))]" ;                                                                                                   /**/

		String querywatchingtvA2="select b.activity1,b.activity2 "                                                                                                                                                  /**/
				+ "from pattern["                                                                                                                                                              /**/
				+ "every(b=SensorEventHouseA(ir1=1)->c=SensorEventHouseA(fo1=1 or fo2=1))]";   
		/**/
	                    //ultima aggiunta:                                                                                                                                                                                       /**/
		String querywatchingtvA3 ="select b.activity1,b.activity2 "
				+ "from pattern["
				+ "every(a=SensorEventHouseA(ir1=1)) -> b=SensorEventHouseA(fo1=1 or fo2=1 or so1=1) and c=SensorEventHouseA(ir1=1)"
				+ "and " 
				+ "every(d=SensorEventHouseA(fo1=1 or fo2=1 or so1=1) "
				+ "and e=SensorEventHouseA(ir1=1))"
				+ "-> f=SensorEventHouseA(fo1=1 or fo2=1 or so1=1)"
				+ "]";                                                                                                                                                                                                         /**/
	                                                                                                                                                                                                           /**/
	                                                                                                           /**/
	                                                                                                                                                                                                           /**/
		String querypreparingbreakfastA="select b.activity1,b.activity2 "                                                                                                                                           /**/
				+ "from pattern["                                                                                                                                                                                  /**/
				+ "a=SensorEventHouseA(fo3=1)"                                                                                                                                                                     /**/
				+ "-> every(b=SensorEventHouseA(ph3=1 and timestamp < 43000))"                                                                                                                                     /**/
				+ "where timer:within(1)]";                                                                                                                                                                        /**/
	                                                                                                                                                                                                           /**/
		String queryhavingbreakfastA="select c.activity1,c.activity2 "                                                                                                                                              /**/
				+ "from pattern["                                                                                                                                                                                  /**/
				+ "a=SensorEventHouseA(fo3=1)"                                                                                                                                                                     /**/
				+ "-> every(b=SensorEventHouseA(ph3=1 and timestamp < 43000))"                                                                                                                                     /**/
				+ "where timer:within(1)->every(c=SensorEventHouseA((di3=1 or di4=1) and timestamp < 43000))]";                                                                                                    /**/
			                                                                                                                                                                                                   /**/
				                                                                                                                                                  /**/
	                                                                                                                                                                                                           /**/
		String querypreparinglunchA="select b.activity1,b.activity2 "                                                                                                                                               /**/
				+ "from pattern["                                                                                                                                                                          /**/
				+ "a=SensorEventHouseA(ph3=1 and timestamp > 43000)"                                                                                                                                       /**/
				+ "-> every(b=SensorEventHouseA(te1=1 and timestamp between 43000 and 60000))]";                                                                                                           /**/
					                                                                                                                                                                                           /**/
	                                                                                                                                                                                                           /**/
	                                                                                                                                                                                                           /**/
		String querypreparingdinnerA="select b.activity1,b.activity2 "                                                                                                                                              /**/
				+ "from pattern["                                                                                                                                                                                  /**/
				+ "a=SensorEventHouseA(ph3=1 and timestamp > 67000)"                                                                                                                                               /**/
				+ "-> every(b=SensorEventHouseA(te1=1 and timestamp between 67000 and 83000))]";     
	
		String querytoiletingA="select a.activity1,a.activity2 "
				+ "from pattern["
				+ "every(a=SensorEventHouseA(co2=1))->b=SensorEventHouseA(di2=1)"
				+ "where timer:within(1)"
				+ "]";
	



//QUERIES HOUSE2:

        String queryhavingshowerB="select b.activity1,b.activity2 from pattern["
        		+ "every(a=SensorEventHouseB(so1=1))"
        		+ "->"
        		+ "every(b=SensorEventHouseB(co6=1))]";                                                                                          
                                                                                              
                                                                                           
        String querywatchingtvB="select activity1,activity2 "                                                                                                                                              /**/
				+ "from SensorEventHouseB(pr1=1 or pr2=1)";                                                         
                                                                                              
                 
     	String queryHavingBreakfastB="select b.activity1,b.activity2 "                                                                                                                                             /**/
                + "from pattern["																	                                                                                   /**/
                + "every(b=SensorEventHouseB(timestamp<43000 and (ph1=1 or so2=1 or fo1=1 or fo2=1)))"                                                                                                             /**/
                + "->"                                                                                                                                                                                             /**/
                + "c=SensorEventHouseB((fo1=1 or fo2=1)and timestamp<43000)]";
		
			                                                                                                             /**/   
                 

                                                                                              
	
     	query=queryHavingBreakfastB;//THIS IS THE QUERY WE WANT TO PERFORM 
		
		
		//esper configuration:
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
		
		
		//esper listener:
		cepStatement2.addListener(new UpdateListener(){
			int count=0; //correctly detected activities
			int counttotal=0; //total detected activities
			//the listener writes an output.csv file each time an event is received, at the end of the processing this file
			// will contain the total number of correct activities detected and the total number of activities detected
			
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				EventBean event = newEvents[0];
				try {
					FileWriter fw = new FileWriter("./output.csv");
				
				String output= EventBeanUtility.printEvent(event); //->Format the event and return a string representation.
				StringBuilder outputparsed = new StringBuilder();
				counttotal+=1; //each time we receive an event we have to update counttotal->number of total
				char prevchar='b'; //important to not initialize it as digit or = so 'b' does the job
				int i=0;
				String activitytodetect=Integer.toString(activitynumber);
				boolean firstactivityread=false; //flag needed to separate the reading of the 2 activities
				
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

		
		//here we read the csv file and generate events
		CSVInputAdapterSpec spec= new CSVInputAdapterSpec(new AdapterInputSource(day),"SensorEventHouse"+house);

		spec.setEventsPerSec(1000);//here the number of events generated each second is selected
		
		CSVInputAdapter inputAdapter= new CSVInputAdapter(epService,spec);
		inputAdapter.start();
		
		
		//NOW PRECISION AND RECALL AND EVERYTHING
		//N.B.->this is done when all the events have been detected
		String [] nextLine;
		CSVReader reader2;
		int correction=100; //the result is decimal, but we want a percentage for easier understanding
		
		try {
			reader2 = new  CSVReader(new FileReader("output.csv")); //read the file where we put the count results
			nextLine= reader2.readNext(); //we need to skip the first line since it contains the header
			reader2.close();
			// the csv file has the form:  last_activity1_read,last_activity1_read,total_correct_activities_detected,total_activities_detected
			 
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
		
	
		
		
	 }//run method ends

	
}//ActivityRecognition class ends
	