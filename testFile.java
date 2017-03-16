package Week6;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class nima{
	public int a = 0;
	public int b = 120;
}
public class testFile {
	public static void printModTime(File f){
		long modTime = f.lastModified();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    //Calendar cal = Calendar.getInstance();
	    ///cal.setTimeInMillis(modTime);
		//System.currentTimeMillis()
	    System.out.println(formatter.format(modTime));
	    if (formatter.format(modTime) instanceof String) System.out.println("nimabi");
	}//this method can print the most recent time that this file was modified.
	public static ArrayList countDescModTime(String filepath)  {
    	File file = new File(filepath);
    	ArrayList table = new ArrayList();
        if (file.isFile()) {
        	table.add(file.lastModified());
        } 
        else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++){
	            File readfile = new File(filepath + "\\" + filelist[i]);
	            if (readfile.isFile() || readfile.isDirectory()) {
	            	table.add(readfile.lastModified());
	            } else if (readfile.isDirectory()) {
	            	table.addAll(countDescModTime(filepath + "\\" + filelist[i]));
	            }
            }
        }
        return table;
    }
	public static ArrayList<File> gatherAllFile(File file)  {
    	ArrayList<File> table = new ArrayList<File>();
        if (file.isFile()) {
        	table.add(file);
        } 
        else if (file.isDirectory()) {
            File [] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++){
	            File readfile = filelist[i];
	            if (readfile.isFile()) {
	            	table.add(readfile);
	            } else if (readfile.isDirectory()) {
	            	table.addAll(gatherAllFile(readfile));
	            }
            }
        }
        return table;
    }
	public static long NumOfFiDi(File f){
		long n = 0;
		File []list = f.listFiles();
		for (int i = 0; i < list.length; i++){
			if (list[i].isFile())
				n += 1;
			else if (list[i].isDirectory()){
				n += 1;
				n += NumOfFiDi(list[i]);
			}
		}
		return n;
	}
	public static ArrayList countDescModTime1(File f) {  
		ArrayList table = new ArrayList(); 
	    File flist[] = f.listFiles();  
	    for  ( int  i =  0 ; i < flist.length; i++){  
	    	if (flist[i].isDirectory()){
	    		table.add(flist[i].lastModified());
	    		table.addAll(countDescModTime1(flist[i])); 
	    	} else{
	    		table.add(flist[i].lastModified());
	        }  
	    }  
	    return  table;  
	}
	
	public static ArrayList<String> FindFile(File head, long modTime, String fileName, long size) {
		ArrayList<String> a = new ArrayList<String>();
	    File flist[] = head.listFiles();
	    for  ( int  i =  0 ; i < flist.length; i++){  
	    	if  (flist[i].isDirectory()){  
	    		a.addAll(FindFile(flist[i], modTime, fileName, size));  
	    	} 
	    	else if (flist[i].isFile() && 
	    			 flist[i].lastModified() == modTime &&
	    			 flist[i].getName().equals(fileName) &&
	    			 flist[i].length() == size){  
	    		a.add(flist[i].getAbsolutePath()); 
	        }  
	    } 
	    return a;
	}

	public static String PathValidate (String path){
		String []part = path.split("\\\\");
		String a = "";
		for (int i = 0; i < part.length; i++){
			a += part[i];
			if (i != part.length - 1){
				a += "\\\\";
			}
		}
		return a;
	}
	public static long getFileSize(File f) {  
		long  size =  0 ;  
	    File flist[] = f.listFiles();  
	    for  ( int  i =  0 ; i < flist.length; i++){  
	    	if  (flist[i].isDirectory()){  
	    		size = size + getFileSize(flist[i]);  
	    	} else{  
	    		size = size + flist[i].length();  
	        }  
	    }  
	    return  size;  
	}
	public static boolean isValidString(String str){//^IF \\|(.*)\\| (renamed|modified|path-changed|size-changed) THEN (record-summary|record-detail|recover)$
		final String reqFormat = "^(IF \\|(.*)\\| (renamed|modified|path-changed|size-changed) THEN (record-summary|record-detail|recover))$";
		Pattern neg_x = Pattern.compile(reqFormat.trim());
    	Matcher neg_xn = neg_x.matcher(str.trim());
    	Pattern neg_1 = Pattern.compile("\\|(.*)\\|");
    	Matcher neg_1n = neg_1.matcher(str.trim());
		Pattern neg_2 = Pattern.compile("(renamed|modified|path-changed|size-changed)");
    	Matcher neg_2n = neg_2.matcher(str.trim());
		Pattern neg_3 = Pattern.compile("(record-summary|record-detail|recover)");
    	Matcher neg_3n = neg_3.matcher(str.trim());
    	File temp = null;
    	String k = null;
    	if (!neg_xn.find()) {
    		//System.out.println("neg_xn");
    		return false; 		
    	}
    	else{
    		if (neg_1n.find() &&
    	    	neg_2n.find() && 
    	    	neg_3n.find()){
    	    	k = neg_1n.group().substring(1, neg_1n.group().length() - 1);
    	    	temp = new File(k.trim());
    	    	if (!temp.exists()) {
    	    		//System.out.println("!exist");
    	    		return false;
    	    	}
    	    	else{
    	    		if (temp.isDirectory() && (neg_2n.group().equals("renamed") || neg_2n.group().equals("path-changed"))) {
    	    			//System.out.println("illegal");
    	    			return false;
    	    		}
    	    		else return true;
    	    	} 	    		 
    	    }
    	    else 
    	    	return false;
    	}    	
	}
	public static void main(String[] args) {
	   String str=new Scanner(System.in).nextLine();
	   nima n = new nima();
	    //final String str = "IF (K:\\Users\\Hunter Hugh\\Desktop\\Newfolder\\New folder1\\New folder) path-changed THEN record-summary";
	    System.out.println(isValidString(str));
	    String [][]a = new String[100][3];
	    String [][]b = new String[100][3];
	    a = b.clone();
	    System.out.println(n.b);
	    
	    
	}
}

		/*String fileName = "  g:\\school files noooooooootice\\object Oriented\\"
	    		+ "All_Works\\src\\week6\\input.txt";
	    File f= new File(fileName.trim());	
	    File head = new File("K:\\Users\\Hunter Hugh\\Desktop\\Newfolder\\");
	    System.out.println(NumOfFiDi(head));
	    File head1 = head;
	    if (head.exists())
	    	head.delete();
	    System.out.println(head.getName());
	    System.out.println(head.exists());
	    System.out.println(head.getAbsolutePath());
	    System.out.println(head.length() + "\n" + head1.lastModified());
	    File f= new File(fileName.trim());	
	    File dest = new File("K:\\Users\\Hunter Hugh\\Desktop\\nimabi.txt");
	    f.renameTo(dest);
	    System.out.println("ahsdf");
	    System.out.print(f.getName());
	    *
	    */
/*Pattern neg_x = Pattern.compile(reqFormat.trim());
Matcher neg_xn = neg_x.matcher(str.trim());

Pattern neg_1 = Pattern.compile("|(.*)|");
Matcher neg_1n = neg_1.matcher(str.trim());

Pattern neg_2 = Pattern.compile("(renamed|modified|path-changed|size-changed)");
Matcher neg_2n = neg_2.matcher(str.trim());

Pattern neg_3 = Pattern.compile("(record-summary|record-detail|recover)");
Matcher neg_3n = neg_3.matcher(str.trim());*/

/*for (int i = 0; i < nima.size(); i++){
	System.out.println(nima.get(i));
}
S
//String fileName = "G:\\School Files Noooooooootice\\Object Oriented\\All_Works\\src";
//ArrayList nima = gatherAllFile(new File(fileName));
//System.out.println('\n' + nima.size());

//final String reqFormat = "^IF |(.*)| (renamed|modified|path-changed|size-changed) THEN (record-summary|record-detail|recover)$";
/*
    	if (neg_xn.find() || neg_1n.find()){
    		k = neg_1n.group().substring(1, neg_1n.group().length() - 1);
    		temp = new File(k.trim());
		    if (!temp.exists()){
		    	System.out.println("no");
		    	return false;	
		    }
	    }
	    if (neg_xn.find() && neg_1n.find() && neg_2n.find()){
	    	if (temp.isDirectory() && (neg_2n.group().equals("renamed") || neg_2n.group().equals("path-changed"))) {
	    		System.out.println("illegal");
	    		return false;
	    	}	    		
	    }*/

/*final String reqFormat = "^(IF \\|(.*)\\| (renamed|modified|path-changed|size-changed) THEN (record-summary|record-detail|recover))$";
Pattern neg_x = Pattern.compile(reqFormat.trim());
Matcher neg_xn = neg_x.matcher(str.trim());
Pattern neg_1 = Pattern.compile("\\|(.*)\\|");
Matcher neg_1n = neg_1.matcher(str.trim());
Pattern neg_2 = Pattern.compile("(renamed|modified|path-changed|size-changed)");
Matcher neg_2n = neg_2.matcher(str.trim());
Pattern neg_3 = Pattern.compile("(record-summary|record-detail|recover)");
Matcher neg_3n = neg_3.matcher(str.trim());
File temp = null;
String k = null;
if (!neg_xn.find()) {
	System.out.println("neg_xn"); 		
}
else{
	System.out.println("zong ti ge shi dui");
}
if (neg_1n.find() &&
	neg_2n.find() && 
	neg_3n.find()){
	 System.out.println("bu rong yi a");
}*/
/*
String str1 = "abcdefghijklmn";

Pattern neg_1 = Pattern.compile("(\\|(.*)\\|)");
Matcher neg_1n = neg_1.matcher(str2.trim());
System.out.println(neg_1n.find());
System.out.println(neg_1n.group());*/