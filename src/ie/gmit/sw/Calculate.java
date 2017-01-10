package ie.gmit.sw;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

public class Calculate {
	//variables
	 private HashMap<String, Metric> classMap = new HashMap<>();
	 private String jarName;
	
	 public Calculate(String jar){
		 //set jar
		 this.jarName = jar;
		
		 //call add
		 addClass();
		
		 try {
			 //call calculate
			calculateMetric();
		 } catch (ClassNotFoundException e) {
			e.printStackTrace();
		 } catch (IOException e) {
			e.printStackTrace();
		 }
        //enhanced loop that steps through the map
		 //http://stackoverflow.com/questions/27867598/java-hashmap-put-in-an-enhanced-for-loop-just-like-an-arraylist
		 for(Metric m : classMap.values()){
			 //print out values to console
             System.out.println("Outdegree: " + m.getOutDegree() + ". Class: " + m.getName());
             System.out.println("Indegree: " + m.getInDegree() + ". Class" + m.getName());
             System.out.println("Stability: " + m.getStability());
             System.out.println();
		 }
	}
	  
	public void addClass(){
		int count = 0;

        try {
            //get jar file
            File myFile  = new File(jarName);

            //create an inout stream to read jar
            JarInputStream in = new JarInputStream(new FileInputStream(myFile));

            //get the next JarEnrty
            JarEntry next = in.getNextJarEntry();
            
            //code from moodle and JarChecker.java
            while (next != null) {

                //check that its a class
                if (next.getName().endsWith(".class")) {

                    //tidy it up
                    String name = next.getName().replaceAll("/", "\\.");
                    name = name.replaceAll(".class", "");
                    if (!name.contains("$")) name.substring(0, name.length() - ".class".length());
                    
                    //add the class to the map with a new metric object
                    classMap.put(name, new Metric());

                    //set the class name
                    classMap.get(name).setName(name);
                    
                    //increment the count
                    count++;

                } // if

                //get next jar entry
                next = in.getNextJarEntry();

            } // while

        } catch (Exception e){

            e.printStackTrace();
        }
	}
	
	public void calculateMetric() throws ClassNotFoundException, IOException{
		try{
			//get jar file
	        File myFile = new File(jarName);
	
	        //create a url to the file
	        URL myUrl = myFile.toURI().toURL();
	        URL[] urls = new URL[]{myUrl};
	
	        //get a class loader to get classes from jar
	        ClassLoader cl = new URLClassLoader(urls);
	
	        //for each loop on the map
	        //http://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
	        for (String className : classMap.keySet()) {
	            // using the class loader
	        	//Class cls = Class.forName(“ie.gmit.sw.Stuff”);
	            Class cls = Class.forName(className, false, cl);
	
	            //get in and out degree
	            getClassItems(cls);
	        }
		 } catch (Exception e){

            e.printStackTrace();
        }
	}
	
	public void getClassItems(Class cls){
		int outdegree = 0;

		//use the previous code but increment the degrees this time
        boolean iface = cls.isInterface();
        Class[] interfaces = cls.getInterfaces();
        for(Class i : interfaces){

            if(classMap.containsKey(i.getName())) {

                // increment outdegree
                outdegree++;

                // increment indegree for interface
                Metric m = classMap.get(i.getName());
                m.setInDegree(m.getInDegree() + 1);
            }
        }

        Constructor[] cons = cls.getConstructors();
        Class[] constructorParams;

        for(Constructor c : cons){
            constructorParams = c.getParameterTypes();
            for(Class param : constructorParams){
                if(classMap.containsKey(param.getName())){
                    // increment outdegree
                    outdegree++;

                    // increment indegree for other class
                    Metric m = classMap.get(param.getName());
                    m.setInDegree(m.getInDegree() + 1);
                }
            }
        }

    	Field[] fields = cls.getFields();
        for(Field f : fields){
            if(classMap.containsKey(f.getName())){
                // increment outdegree
                outdegree++;

                // increment indegree for interface
                Metric m = classMap.get(f.getName());
                m.setInDegree(m.getInDegree() + 1);

            }
        }
        
        Method[] methods = cls.getMethods();
        Class[] methodParams;
        for(Method m : methods){
            Class methodReturnType = m.getReturnType();
            if(classMap.containsKey(methodReturnType.getName())){
                // increment outdegree
                outdegree++;

                // increment indegree for interface
                Metric bm = classMap.get(methodReturnType.getName());
                bm.setInDegree(bm.getInDegree() + 1);
            }

            methodParams = m.getParameterTypes();
            for(Class mp : methodParams){
                if(classMap.containsKey(mp.getName())){
                    // increment outdegree
                    outdegree++;

                    // increment indegree for interface
                    Metric bm = classMap.get(mp.getName());
                    bm.setInDegree(bm.getInDegree() + 1);
                } 
            }
        }
        classMap.get(cls.getName()).setOutDegree(outdegree);
	}
	
	//used to put data in the table
	public Object[][] getMetricData() {
		//use count to keep track of table position
		int count = 0;
		//using a 2d array as it is a table structure
		 Object[][] data = new Object[classMap.size()][4];
		 
		 //http://www.sergiy.ca/how-to-iterate-over-a-map-in-java/
		 //http://stackoverflow.com/questions/27867598/java-hashmap-put-in-an-enhanced-for-loop-just-like-an-arraylist
		 for(Metric m : classMap.values()){
            //set the data in the table
            data[count][0] = m.getName();
            data[count][1] = m.getOutDegree();
            data[count][2] = m.getInDegree();
            data[count][3] = m.getStability();

            count++;
		 }
		 return data;
	}
	  
	  
}
