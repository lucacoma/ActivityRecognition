
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
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.ConsoleAppender; 
import org.apache.log4j.SimpleLayout; 
import org.apache.log4j.Level; 
import org.apache.log4j.Logger;

import java.io.InputStream;

                  
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
	
	
	/***********************************************************************
	* Method: countOccurrences				  					  		   *
	* Description: this returns  the number of events labeled 			   *
	* with the number of the chosen activity during the chosen day         *
	*             	  												       * 
	*		   														       *
	* Parameters: String day, int activity						 					       *
	* Returns:	int count						  						   *
	***********************************************************************/
	
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
	
	
	// the menus	
	
	public static String insertDay(){
		Scanner in = new Scanner(System.in);
		String day = null;
		// Display the menu
		System.out.println("AVAILABLE DAYS FOR HOUSE A");
		System.out.println("d1 -> DAY_1 on House A");
        System.out.println("d3 -> DAY_3 on House A");
        System.out.println("d5 -> DAY_5 on House A");
        System.out.println("d13 -> DAY_13 on House A");
        System.out.println("\nAVAILABLE DAYS FOR HOUSE B");
        System.out.println("d2 -> DAY_2 on House B");
        System.out.println("d4 -> DAY_4 on House B");
        System.out.println("d6 -> DAY_6 on House B");
        System.out.println("d8 -> DAY_8 on House B");
        System.out.println("d10 -> DAY_10 on House B");
  

        System.out.println("\nSTEP TWO)  Please enter the day you want to test for the prevoiusly selected house:");
        
        //Get user's choice
        String choice=in.nextLine();
         
        //Display the title of the chosen module
        switch (choice) {
            case "d1": 
            	day = "/HouseA_CSV/DAY_1.csv"; 
            	break;
            case "d3": 
            	day = "/HouseA_CSV/DAY_3.csv"; 
            	break;
            case "d5": 
            	day = "/HouseA_CSV/DAY_5.csv"; 
            	break;
            case "d13": 
            	day = "/HouseA_CSV/DAY_13.csv"; 
            	break;
            case "d2": 
            	day = "/HouseB_CSV/DAY_2.csv"; 
            	break;
            case "d4": 
            	day = "/HouseB_CSV/DAY_4.csv"; 
            	break;
            case "d6": 
            	day = "/HouseB_CSV/DAY_6.csv"; 
            	break;
            case "d8": 
            	day = "/HouseB_CSV/DAY_8.csv"; 
            	break;
            case "d10": 
            	day = "/HouseB_CSV/DAY_10.csv"; 
            	break;
            default: System.out.println("Invalid choice");
        }//end of switch
        System.out.println("You selected the file: " + day);
        System.out.println("\n");
		return day;
		}
	
	
	public static int insertActivity(){
		Scanner in = new Scanner(System.in);
		int activity = 0;
		
		System.out.println("3\t Preparing Breakfast");
		System.out.println("4\t Having Breakfast ");
		System.out.println("5\t Preparing Lunch (for house A only)");
		System.out.println("7\t Preparing Dinner (for house A only)");
		System.out.println("12\t Watching TV");
		System.out.println("15\t Toileting");
		System.out.println("14\t Having Shower");
		System.out.println("20\t Shaving (for house A only)");

        System.out.println("\nSTEP THREE) Please select the label's activity (Ground Truth) you want to evaluate:");
        
        int choice=in.nextInt();
             
        switch (choice) {
            case 3: 
            	activity = 3; 
            	break;
            case 4: 
            	activity = 4; 
            	break;
            case 5: 
            	activity = 5; 
            	break;
            case 7: 
            	activity = 7; 
            	break;
            case 12: 
            	activity = 12; 
            	break;
            case 14: 
            	activity = 14; 
            	break;
            case 21: 
            	activity = 21; 
            	break;
            	
            default: System.out.println("Invalid choice");
        }//end of switch
        System.out.println("You selected the activity: " + activity);
        System.out.println("\n");
		return activity;
		}
	
	
	
	public static String insertHouse(){
		Scanner in = new Scanner(System.in);
		String house = null;
		// Display the menu
		System.out.println("a -> HOUSE A");
		System.out.println("b -> HOUSE B");

        System.out.println("\nSTEP ONE) Please enter tha HOUSE you want to test ('a' or 'b')");
        
        String choice=in.nextLine();
         
        switch (choice) {
            case "a": 
            	house = "A"; 
            	break;
            case "b": 
            	house = "B"; 
            	break;
            default: System.out.println("Invalid choice");
        }//end of switch
        System.out.println("You selected HOUSE: " + house);
        System.out.println("\n");
		return house;
		}
	        
	        
	
	public static String selectQuery(){
		Scanner in = new Scanner(System.in);
		String query = null;
		// Display the menu
		System.out.println("HOUSE A available queries");
		System.out.println("q1\t HouseA -> Preparing Breakfast (label: 3)");
		System.out.println("q2\t HouseA -> Having Breakfast (label: 4)");
		System.out.println("q3\t HouseA -> Preparing Lunch (label: 5)");
		System.out.println("q4\t HouseA -> Preparing Dinner (label: 7)");
		System.out.println("q5\t HouseA -> Haning Shower (label: 14)");
		System.out.println("q6\t HouseA -> Watching TV (label: 12)");
		System.out.println("q7\t HouseA -> Toileting (label: 15)");
		System.out.println("q8\t HouseA -> Shaving (label: 20)");
		System.out.println("\nHOUSE B available queries");
		System.out.println("q9\t HouseB -> Having Breakfast (label: 4)");
		System.out.println("q10\t HouseB -> Watching TV (label: 12)");
		System.out.println("q11\t HouseB -> Having Shower (label: 14)");
        
        System.out.println("\nSTEP FOUR) Please select a query:");
        
        //Get user's choice
        String choice=in.nextLine();
         
        //Display the title of the chosen module
        switch (choice) {
            case "q1": 
            	query = "select b.activity1,b.activity2 "                                                                                                                                           
        				+ "from pattern["                                                                                                                                                                                  
        				+ "a=SensorEventHouseA(fo3=1)"                                                                                                                                                                     
        				+ "-> every(b=SensorEventHouseA(ph3=1 and timestamp < 43000))"                                                                                                                                     
        				+ "where timer:within(1)]"; 
            	break;
            case "q2": 
            	query = "select c.activity1,c.activity2 "                                                                                                                                              
        				+ "from pattern["                                                                                                                                                                                  
        				+ "a=SensorEventHouseA(fo3=1)"                                                                                                                                                                     
        				+ "-> every(b=SensorEventHouseA(ph3=1 and timestamp < 43000))"                                                                                                                                     
        				+ "where timer:within(1)"
        				+ "->every(c=SensorEventHouseA((di3=1 or di4=1) and timestamp < 43000))]"; 
            	break;
            case "q3": 
            	query = "select b.activity1,b.activity2 "                                                                                                                                               
        				+ "from pattern["                                                                                                                                                                          
        				+ "a=SensorEventHouseA(ph3=1 and timestamp > 43000)"                                                                                                                                       
        				+ "-> every(b=SensorEventHouseA(te1=1 and timestamp between 43000 and 60000))]" ; 
            	break;
            case "q4": 
            	query = "select b.activity1,b.activity2 "                                                                                                                                              
        				+ "from pattern["                                                                                                                                                                                  
        				+ "a=SensorEventHouseA(ph3=1 and timestamp > 67000)"                                                                                                                                               
        				+ "-> every(b=SensorEventHouseA(te1=1 and timestamp between 67000 and 83000))]"; 
            	break;
            case "q5": 
            	query = "select a.activity1,a.activity2 from pattern[every(a=SensorEventHouseA(co2=1))->b=SensorEventHouseA(co3=1)]"; 
            	break;
            case "q6": 
            	query = "select c.activity1,c.activity2 from pattern ["                                                                                                                                    
            			+ "every(a=SensorEventHouseA(ir1=1)->c=SensorEventHouseA(fo1=1 or fo2=1 or so1=1)"                                                                                                                 
            			+ "where timer:within(1 sec))]"; 
            	break;
            case "q7": 
            	query = "select a.activity1,a.activity2 "
        				+ "from pattern["
        				+ "every(a=SensorEventHouseA(co2=1))->b=SensorEventHouseA(di2=1)"
        				+ "where timer:within(1)"
        				+ "]"; 
            	break;
            case "q8": 
            	query = "select a.activity1,a.activity2 "
        				+ "from pattern["
        				+ "every(a=SensorEventHouseA(di1=1))->every(b=SensorEventHouseA(ph6=1))"
        				+ "where timer:within(1)"
        				+ "]"; 
            	break;
            case "q9": 
            	query ="select b.activity1,b.activity2 "                                                                                                                                             
                        + "from pattern["																	                                                                                   
                        + "every(b=SensorEventHouseB(timestamp<43000 and (ph1=1 or so2=1 or fo1=1 or fo2=1)))"                                                                                                             
                        + "->"                                                                                                                                                                                             
                        + "c=SensorEventHouseB((fo1=1 or fo2=1)and timestamp<43000)]" ; 
            	break;
            case "q10": 
            	query = "select activity1,activity2 "                                                                                                                                              
        				+ "from SensorEventHouseB(pr1=1 or pr2=1)"; 
            	break;
            case "q11": 
            	query = "select b.activity1,b.activity2 from pattern["
                		+ "every(a=SensorEventHouseB(so1=1))"
                		+ "->"
                		+ "every(b=SensorEventHouseB(co6=1))]"; 
            	break;
      
            default: System.out.println("Invalid choice");
        }//end of switch
        System.out.println("Please wait while the system elaborates your request...");
		return query;
		}
	
	
	public void run(){
	
		String house= ActivityRecognition.insertHouse();	
		String day= ActivityRecognition.insertDay();  	
		int activitynumber= ActivityRecognition.insertActivity(); 						  								
		
		String query;     
		query= ActivityRecognition.selectQuery();//THIS IS THE QUERY WE WANT TO PERFORM 
		

     	
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
	