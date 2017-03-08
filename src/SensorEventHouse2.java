
public  class SensorEventHouse2 {

	
	            
	/*1	*/int   co1;		//Contact Sensor		Kitchen cupboard
	/*2	*/int   co2;		//Contact Sensor		Kitchen cupboard
	/*3	*/int   co3;		//Contact Sensor		House Door
	/*4	*/int   co4;		//Contact Sensor		Wardrobe Door
	/*5	*/int   co5;		//Contact Sensor		Wardrobe Door
	/*6	*/int   co6;		//Contact Sensor		Shower Cabinet Door
	/*7	*/int   di2;		//Distance			Tap
	/*8	*/int   fo1;		//Force Sensor		Chair
	/*9	*/int   fo2;		//Force Sensor		Chair
	/*10*/int   fo3;		//Force Sensor		Chair
	/*11*/int   ph1;		//Photocell			Fridge
	/*12*/int   ph2;		//Photocell			Kitchen Drawer
	/*13*/int   pr1;		//Pressure Mat		Couch
	/*14*/int   pr2;		//Pressure Mat		Couch
	/*15*/int   pr3;		//Pressure Mat		Bed
	/*16*/int   pr4;		//Pressure Mat		Bed
	/*17*/int   pr5;		//Pressure Mat		Armchair
	/*18*/int   so1;		//Sonar Distance		Bathroom Door
	/*19*/int   so2;		//Sonar Distance		Kitchen
	/*20*/int   so3;		//Sonar Distance		Closet
		  int activity1;
		  int activity2;
		  int timestamp;
		  
		  
		  //empty constructor:
		  public SensorEventHouse2(){
			  
		  }

		  
		  public SensorEventHouse2(int  co1,int  co2,int  co3,int  co4,int  co5,
				  					int co6, int  di2,int  fo1,int  fo2,int  fo3,
				  					int  ph1,int  ph2,int  pr1,int  pr2,int  pr3,
				  					int  pr4,int  pr5,int  so1,int  so2,int  so3,
				  					int activity1,int activity2,int timestamp){
			  
			  this.co1=co1;		
              this.co2=co2;		
              this.co3=co3;		
              this.co4=co4;		
              this.co5=co5;		
              this.co6=co6;		
              this.di2=di2;		
              this.fo1=fo1;		
              this.fo2=fo2;		
              this.fo3=fo3;		
              this.ph1=ph1;		
              this.ph2=ph2;		
              this.pr1=pr1;		
              this.pr2=pr2;		
              this.pr3=pr3;		
              this.pr4=pr4;
              this.pr5=pr5;
              this.so1=so1;		
              this.so2=so2;		
              this.so3=so3;
              this.activity1=activity1;
              this.activity2=activity2;
              this.timestamp=timestamp;  
		  }
        
	      
				 //GETTERS
			     public int getCo1() { return co1;}
			     public int getCo2() { return co2;}
			     public int getCo3() { return co3;}
			     public int getCo4() { return co4;}
			     public int getCo5() { return co5;}
			     public int getCo6() { return co6;}
			     public int getDi2() { return di2;}
			     public int getFo1() { return fo1;}
			     public int getFo2() { return fo2;}
			     public int getFo3() { return fo3;}
			     public int getPh1() { return ph1;}
			     public int getPh2() { return ph2;}
			     public int getPr1() { return pr1;}
			     public int getPr2() { return pr2;}
			     public int getPr3() { return pr3;}
			     public int getPr4() { return pr4;}
			     public int getPr5() { return pr5;}
			     public int getSo1() { return so1;}
			     public int getSo2() { return so2;}
			     public int getSo3() { return so3;}
			     public int getActivity1() {return activity1;}
			     public int getActivity2() {return activity2;}
			     public int getTimestamp() {return timestamp;}

			     
			     //Setters
			     public void setCo1(int co1) { this.co1=co1;}
			     public void setCo2(int co2) { this.co2=co2;}
			     public void setCo3(int co3) { this.co3=co3;}
			     public void setCo4(int co4) { this.co4=co4;}
			     public void setCo5(int co5) { this.co5=co5;}
			     public void setCo6(int co6) { this.co6=co6;}
			     public void setDi2(int di2) { this.di2=di2;}
			     public void setFo1(int fo1) { this.fo1=fo1;}
			     public void setFo2(int fo2) { this.fo2=fo2;}
			     public void setFo3(int fo3) { this.fo3=fo3;}
			     public void setPh1(int ph1) { this.ph1=ph1;}
			     public void setPh2(int ph2) { this.ph2=ph2;}
			     public void setPr1(int pr1) { this.pr1=pr1;}
			     public void setPr2(int pr2) { this.pr2=pr2;}
			     public void setPr3(int pr3) { this.pr3=pr3;}
			     public void setPr4(int pr4) { this.pr4=pr4;}
			     public void setPr5(int pr5) { this.pr5=pr5;}
			     public void setSo1(int so1) { this.so1=so1;}
			     public void setSo2(int so2) { this.so2=so2;}
			     public void setSo3(int so3) { this.so3=so3;}
			     public void setActivity1(int activity1) {this.activity1=activity1;}
			     public void setActivity2(int activity2) {this.activity2=activity2;}
			     public void setTimestamp(int timestamp) {this.timestamp=timestamp;}

		         
		      
              
}             
              
              
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  