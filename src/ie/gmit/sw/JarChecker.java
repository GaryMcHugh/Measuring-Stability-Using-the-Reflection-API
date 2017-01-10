package ie.gmit.sw;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.Iterator;
import java.util.jar.*;

public class JarChecker {
	//constructor that uses the file path
    public JarChecker(String path){
    	//call to get details
    	prepare(path);
    }
    
    public void prepare(String path){
    	//has to be in a try/catch block
    	try{
    		//create a file
	        File myFile  = new File(path);
	        
	        //convert file toURI
	        URL myUrl = myFile.toURI().toURL();
	        URL[] urls = new URL[]{myUrl};
	        
	        //load the classes in the array using a class loader
	        ClassLoader cl = new URLClassLoader(urls);
	    	
	        //code provided on moodle
	    	JarInputStream in = new JarInputStream(new FileInputStream(new File("mylib.jar")));
	    	JarEntry next = in.getNextJarEntry();
	    	
	    	while (next != null) {
	    	 if (next.getName().endsWith(".class")) {
		    	 String name = next.getName().replaceAll("/", "\\.");
		    	 name = name.replaceAll(".class", "");
		    	 if (!name.contains("$")) name.substring(0, name.length() - ".class".length());
		    	 	System.out.println(name);
	    	 }
	    	 next = in.getNextJarEntry();
	    	}
    	 } catch (FileNotFoundException e){

             e.printStackTrace();

         } catch (Exception e){

             e.printStackTrace();
         }
    }
    
    //this method uses all methods supplied on moodle
    public void getDetails(Class cls){
    	//use the methods provided on moodle to get packages, constructors, interfaces etc
    	
    	//package
    	 Package pack = cls.getPackage();
         System.out.println("Package: " + pack.getName());
         
         //isInterface
         boolean iface = cls.isInterface();
         System.out.println("isInterface: " + iface);
         
         //interfaces
         Class[] interfaces = cls.getInterfaces();
         //have to loop as there can be multiple interfaces (enhanced loop)
         //get each class implementing the interface
         //for(int i=0; i < interfaces.length; i++){ //could have done it this way but : as for each is better
         //Initially had for loops like above (from code sample) but changed them all to enhanced loops
         for(Class i : interfaces){
        	 //System.out.println(interfaces[i]);
             System.out.println("Classes implementing the interface: " + i.getName());
         }
         
         //Constructor & Constructor Parameters
         Constructor[] cons = cls.getConstructors();
         Class[] params;
         //loop as there can be multiple constructors
         //print out the constructors and their parameters
       //for(int i=0; i < cons.length; i++)
         for(Constructor c : cons){
        	//System.out.println(cons[i]);
             System.out.println("Contructor: " + c.getName());
             params = c.getParameterTypes();
           //for(int i=0; i < params.length; i++)
             for(Class param : params){
            	//System.out.println(params[i]);
                 System.out.println("Constructors Parameters: " + param.getName());
             }
         }
         
         //Fields
         Field[] fields = cls.getFields();
         //loop as there can be multiple fields
         for(Field f : fields){
             System.out.println("Fields: " + f.getName());
         }
         
         //Methods, Method Parameters & Return Types
         Method[] methods = cls.getMethods();
         Class[] methodParams;

         //loop for each method
         //print out methods and their return type
         for(Method m : methods){
             System.out.println("Methods: " + m.getName());
             Class methodReturnType = m.getReturnType();
             System.out.println("Method Return Types: " + methodReturnType.getName());
             
             methodParams = m.getParameterTypes();
             //get method parameters
             for(Class mp : methodParams){
                 System.out.println("Method Parameters: " + mp.getName());
             }
         }
    }

}
