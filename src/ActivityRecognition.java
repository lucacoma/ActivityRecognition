import com.espertech.esper.client.*; 
import java.util.Random; 
import java.util.Date; 
import org.apache.log4j.ConsoleAppender; 
import org.apache.log4j.SimpleLayout; 
import org.apache.log4j.Level; 
import org.apache.log4j.Logger;


public class ActivityRecognition {

	
	


	
	
	//let's define a class to store the sensor entries
	
	public static class SensorOutput
	{
//							ID				SENSOR TYPE			PLACE
											
		/*1*/ boolean		Ph1;		//	Photocell 			Wardrobe
		/*2*/ boolean		Ph2;		//	Photocell 			Convertible Couch (Used as bed for Resident 2)
		/*3*/ boolean		Ir1;		//	IR 				    TV receiver
		/*4*/ boolean		Fo1;		//	Force Sensor 		Couch
		/*5*/ boolean		Fo2;		//	Force Sensor 	    Couch
		/*6*/ boolean		Di3;		//	Distance  			Chair
		/*7*/ boolean		Di4; 		//	Distance		    Chair
		/*8*/ boolean		Ph3;		//	Photocell 			Fridge
		/*9*/ boolean		Ph4;		//	Photocell			Kitchen Drawer
		/*10*/boolean		Ph5;		//	Photocell			Wardrobe
		/*11*/boolean		Ph6;		//	Photocell			Bathroom Cabinet
		/*12*/boolean		Co1;		//	Contact Sensor 		House Door
		/*13*/boolean		Co2;		//	Contact Sensor		Bathroom Door
		/*14*/boolean		Co3;		//	Contact Sensor 		Shower Cabinet Door
		/*15*/boolean		So1;		//	Sonar Distance 		Hall
		/*16*/boolean		So2;		//	Sonar Distance 		Kitchen
		/*17*/boolean		Di1;		//	Distance			Tap
		/*18*/boolean		Di2;		//	Distance			Water Closet
		/*19*/boolean		Te1;		//	Temperature  		Kitchen
		/*20*/boolean		Fo3;		//	Force Sensor 		Bed
			  double 		Time_instant;
			  String        Day;
			  
			  
		
		public SensorOutput(boolean sensor1, boolean sensor2, boolean sensor3, boolean sensor4,boolean sensor5,
							boolean sensor6,boolean sensor7,  boolean sensor8,  boolean sensor9, boolean sensor10, boolean sensor11, 
							boolean sensor12, boolean sensor13, boolean sensor14, boolean sensor15, boolean sensor16, boolean sensor17, 
							boolean	sensor18, boolean sensor19, boolean sensor20, double sec, String d ){    
			 
               Ph1=sensor1;		
               Ph2=sensor2;		
               Ir1=sensor3;		
               Fo1=sensor4;		
               Fo2=sensor5;		
               Di3=sensor6;		
               Di4=sensor7; 		
               Ph3=sensor8;		
               Ph4=sensor9;		
               Ph5=sensor10;		
               Ph6=sensor11;		
               Co1=sensor12;		
               Co2=sensor13;		
               Co3=sensor14;		
               So1=sensor15;		
               So2=sensor16;	
               Di1=sensor17;
               Di2=sensor18;		
               Te1=sensor19;		
               Fo3=sensor20;
               Time_instant=sec;
               Day=d;
			   }
		                                        
		     public boolean getPh1() { return Ph1;}
		     public boolean getPh2() { return Ph2;}
		     public boolean getIr1() { return Ir1;}
		     public boolean getFo1() { return Fo1;}
		     public boolean getFo2() { return Fo2;}
		     public boolean getDi3() { return Di3;}
		     public boolean getDi4() { return Di4;}
		     public boolean getPh3() { return Ph3;}
		     public boolean getPh4() { return Ph4;}
		     public boolean getPh5() { return Ph5;}
		     public boolean getPh6() { return Ph6;}
		     public boolean getCo1() { return Co1;}
		     public boolean getCo2() { return Co2;}
		     public boolean getCo3() { return Co3;}
		     public boolean getSo1() { return So1;}
		     public boolean getSo2() { return So2;}
		     public boolean getDi1() { return Di1;}
		     public boolean getDi2() { return Di2;}
		     public boolean getTe1() { return Te1;}
		     public boolean getFo3() { return Fo3;}
		     public double getTime_instant() {return Time_instant;}
		     public String getDay() { return Day;}
		
		//     @Override public String toString(){
		  //  	 return "Time_instant:" + Time_instant.toString(); //non funge, boh
		   //  }
		}//SensorOutput
		     
			//CAPIRE BENE STO PEZZO
			public static class ActivityListener implements UpdateListener{
				public void update(EventBean[] newData, EventBean[] oldData){
					System.out.println("Event received:" +newData[0].getUnderlying());
				}
			}
			
		//	public static void GenerateSensorEvent(EPRuntime cepRT)
			
			////////////////////////////////////////
			
		//	public static void main(String[] args){
				
				
		//	}
			
			
	}//ActivityRecognition		     
                         