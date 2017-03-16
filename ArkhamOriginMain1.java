package Week6;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.ThreadFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ExceptionThread2 implements Runnable  
{   
    /* (non-Javadoc) 
     * @see java.lang.Runnable#run() 
     */  
    public void run()  
    {     
        throw new RuntimeException();  
    }  
}
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler  
{  
    public void uncaughtException(Thread thread, Throwable throwable)  
    {  
        // TODO Auto-generated method stub  
        System.out.println("caught "+throwable);  
    }  
      
}  
class HandlerThreadFactory implements ThreadFactory  
{  
    public Thread newThread(Runnable runnable)  
    {  
        // TODO Auto-generated method stub  
        Thread t = new Thread(runnable);  
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());  
        return t;  
    }  
} 
class DirectoryFile{
	public ArrayList<File> gatherAllFile(File file)  {
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
	public ArrayList<Long> gatherAllTime(File file) {
		ArrayList<Long> table = new ArrayList<Long>();
        if (file.isFile()) {
        	table.add(file.lastModified());
        } 
        else if (file.isDirectory()) {
            File [] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++){
	            File readfile = filelist[i];
	            if (readfile.isFile()) {
	            	table.add(readfile.lastModified());
	            } else if (readfile.isDirectory()) {
	            	table.addAll(gatherAllTime(readfile));
	            }
            }
        }
        return table;
	}
	public ArrayList gatherAllSize(File file) {
		ArrayList table = new ArrayList();
        if (file.isFile()) {
        	table.add(file.length());
        } 
        else if (file.isDirectory()) {
            File [] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++){
	            File readfile = filelist[i];
	            if (readfile.isFile()) {
	            	table.add(readfile.length());
	            } else if (readfile.isDirectory()) {
	            	table.addAll(gatherAllSize(readfile));
	            }
            }
        }
        return table;
	}
	//
	public File dirFile = null;
	public ArrayList allFile = new ArrayList();
	public ArrayList allModTime = new ArrayList();
	public ArrayList allLength = new ArrayList();
	
	public DirectoryFile(File file){
		this.dirFile = file;
		this.allFile = this.gatherAllFile(file);
		this.allModTime = this.gatherAllTime(file);
		this.allLength = this.gatherAllSize(file);
	}
	public DirectoryFile(){
		this.dirFile = null;
		this.allFile = new ArrayList();
		this.allModTime = new ArrayList();
		this.allLength = new ArrayList();
	}
	public DirectoryFile(File f, ArrayList a, ArrayList b, ArrayList c){
		this.dirFile = f;
		this.allFile = (ArrayList) a.clone();
		this.allLength = (ArrayList) b.clone();
		this.allModTime = (ArrayList) c.clone();
	}
	
}
class Trigger extends Thread {
	public File fileTopDirection;//top directory, all file should not beyond the extent
	public ArrayList<File> file = new ArrayList<File>();
	public ArrayList<Long> modTime = new ArrayList<Long>();
	public ArrayList<Long> size = new ArrayList<Long>();
	public ArrayList descModTime = new ArrayList();//direction exclusive, mediate num
	public ArrayList descFile = new ArrayList();//directory exclusive
	public String[] requests = new String [3];
	private int triggerTime = 0;
	//directory exclusive
	public ArrayList dirFile0 = new ArrayList();
	public ArrayList dirFile1 = new ArrayList();
	public ArrayList index = new ArrayList();
	public ArrayList<DirectoryFile> DirFile = new ArrayList<DirectoryFile>();
	//
	private File outPutFile = new File("G:\\School Files Noooooooootice\\Object Oriented\\All_Works\\src\\Week6\\output.txt");
	//attributes
	public boolean UnfindFlag = false;
	public int name = 0;
	public Summary_Detail SD_Branch;
	//flags and symbols zone
	public Trigger(String []attr, String topDir, int n, Summary_Detail sd){
		this.SD_Branch = sd;
		this.name = n;
		this.requests = attr.clone();
		this.fileTopDirection = new File(topDir);
		File tempFile = new File(attr[0]);
		this.file.add(tempFile);
		this.file.add(tempFile);
		if (!tempFile.exists()){
			System.out.println("cannot find");
			this.UnfindFlag = true;
		}// if not exists
		else if (tempFile.exists()){
			if (tempFile.isDirectory()){
				this.descModTime = countDescModTime1(tempFile);
				this.descFile = gatherAllFile(tempFile);
				//
				this.modTime.add(this.latestModTime(this.descModTime));
				this.size.add(this.getFileSize(tempFile));
				this.modTime.add(this.latestModTime(this.descModTime));
				this.size.add(this.getFileSize(tempFile));
				//
				DirectoryFile t = new DirectoryFile(tempFile);
				this.DirFile.add(t);
				this.DirFile.add(t);
				//
			}
			else if(tempFile.isFile()){
				this.modTime.add(tempFile.lastModified());
				this.size.add(this.getFileSize(tempFile));
				this.modTime.add(tempFile.lastModified());
				this.size.add(this.getFileSize(tempFile));
			}
		}//duplicate it so from the beginning the ArrayList has at least 2 elements
	}
	// construct, judge mainly whether the file or directory exists.
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
	public void addOneFile( File tempFile){
		this.file.add(tempFile);
		if (!tempFile.exists()){
			System.out.println("cannot find");
		}// if not exists
		else if (tempFile.exists()){
			if (tempFile.isDirectory()){
				this.descModTime = (ArrayList) countDescModTime1(tempFile).clone();
				this.modTime.add(this.latestModTime(this.descModTime));
				this.size.add(this.getFileSize(tempFile));
			}
			else if(tempFile.isFile()){
				this.modTime.add(tempFile.lastModified());
				this.size.add(this.getFileSize(tempFile));
			}
		}
	}
	public void delOneFile(){
		this.file.remove(0);
		this.modTime.remove(0);
		this.size.remove(0);
	}
	public long getFileSize(File f){  
		long  size =  0; 
		if (f.isFile())
			return f.length();
		else if (f.isDirectory()){
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
		return 0;
	     
	}
	public ArrayList<Long> countDescModTime1(File f) {  
		ArrayList<Long> table = new ArrayList<Long>(); 
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
	public long latestModTime(ArrayList<Long> a){
		int i = 0, index = 0;
		long temp1 = 0, max = 0;
		for (i = 0; i < a.size(); i++){
			temp1 = Long.parseLong(a.get(i).toString());
			max = Long.parseLong(a.get(index).toString());
			if (temp1 > max){
				index = i;
				max = temp1;
			}
		}
		return max;
	}
	public ArrayList<String> FindFile(File head) {
		ArrayList<String> a = new ArrayList<String>();
	    File flist[] = head.listFiles();
	    for  ( int  i =  0 ; i < flist.length; i++){  
	    	if  (flist[i].isDirectory()){  
	    		a.addAll(FindFile(flist[i]));  
	    	} 
	    	else if (flist[i].isFile() && 
	    			 flist[i].lastModified() == this.modTime.get(1) &&
	    			 flist[i].getName().equals(this.file.get(1).getName()) &&
	    			 flist[i].length() == this.size.get(1)){  
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
	//function zone
	public synchronized boolean DirRenamed(){
		this.DirFile.get(0);
		int l = this.DirFile.get(0).allFile.size();
		for (int i = 0; i < l; i++){
			
		}
		return false;
	}
	public synchronized boolean Renamed(){
		File filet = new File( this.file.get(1).getParent());//current address
		if (filet.isDirectory()){
			File [] filelist = filet.listFiles();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = filelist[i];
	            if (readfile.isFile()) {// is a file
	            	if (!this.file.get(1).exists() && //the original file should not exists.		            		readfile.lastModified() == this.modTime.get(1) &&
		           		readfile.length() == this.size.get(1) &&
		           		!readfile.getName().equals(this.file.get(1).getName())){//resemble in size and last-modified time
		           		this.addOneFile(readfile);//do some change and keep tracking
		           		this.delOneFile();
		           		return true;
		           	}//changed name
		           	else if (this.file.get(1).exists()){
		           		return false;
		           	}//didn's changed
	            } 
			}
		}
		this.UnfindFlag = true;
		return false;
	}
	public synchronized boolean Modified(){
		if (this.file.get(1).isFile()){
			File fileF = new File( this.file.get(1).getAbsolutePath());
			if (fileF.exists()){
				if (fileF.lastModified() != this.modTime.get(1) && 
					fileF.getName().equals(this.file.get(1).getName())){
					this.addOneFile(fileF);
					this.delOneFile();
					return true;
				}
			}	
		}
		else if (this.file.get(1).isDirectory()){
			File fileD = new File(this.file.get(1).getAbsolutePath());//current address
			ArrayList timeTable = this.countDescModTime1(fileD);
			if (!this.descModTime.equals(timeTable)){
				//this.descModTime = (ArrayList) timeTable.clone();///////////////////////
				this.addOneFile(fileD);
				this.delOneFile();
				return true;
			}
		}
		return false;
	}
	public synchronized boolean PathChanged(){
		File deleted = this.file.get(1);
		File head = this.fileTopDirection;
		if (!deleted.exists()){//be deleted
			ArrayList t = this.FindFile(head);
			String newLoc = (String) (t.isEmpty() ? "" : t.get(0));
			if (newLoc.equals("")) {
				this.UnfindFlag = true;
				return false;//vanished
			}
			else{
				this.addOneFile(new File(/*this.PathValidate(newLoc)*/newLoc));
				this.delOneFile();
				return true;//concrete
			}
		}
		else
			return false;
	}
	public synchronized boolean SizeChanged(){
		File f = this.file.get(1);
		File temp = new File(this.file.get(1).getAbsolutePath());
		if (f.isFile()){
			if (temp.length() != this.size.get(1)){
				this.addOneFile(temp);
				this.delOneFile();
				return true;
			}
		}
		else if (f.isDirectory()){
			if (this.getFileSize(temp) != this.size.get(1)){
				this.addOneFile(temp);
				this.delOneFile();
				return true;
			}
		}
		return false;
	}
	public synchronized boolean harmonyTrigger(int i){
		if (i == 1){
			boolean t = this.Renamed();
			if(t)
				this.triggerTime += 1;
			return t;
		}
		else if (i == 2){
			boolean t = this.Modified();
			if(t)
				this.triggerTime += 1;
			return t;
		}
		else if (i == 3){
			boolean t = this.PathChanged();
			if(t)
				this.triggerTime += 1;
			return t;
		}
		else if (i == 4){
			boolean t = this.SizeChanged();
			if(t)
				this.triggerTime += 1;
			return t;
		}
		else
			return false;
	}
	//trigger zone
	public synchronized void Summary(){
		System.out.println("trigger " + this.name +" has been provoked " + this.triggerTime + " times.");
		this.SD_Branch.tray.addResult("trigger " + this.name +" has been provoked " + this.triggerTime + " times." + "\r\n");
	}
	public synchronized void Detail(int kinds){
		if (kinds == 1){
			System.out.println("name changed: " + this.file.get(this.file.size() - 2).getName() + " to " + this.file.get(this.file.size() - 1).getName());
			this.SD_Branch.tray.addResult("thigger " + this.name + " name changed: " + this.file.get(this.file.size() - 2).getName() + " to " + this.file.get(this.file.size() - 1).getName() + "\r\n");
		}//trigger: renamed
		else if (kinds == 2){
			System.out.print("recently modified time: ");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    System.out.println("thigger " + this.name + " " + formatter.format(this.modTime.get(this.modTime.size() - 1)) + " since " 
		    				   + formatter.format(this.modTime.get(this.modTime.size() - 2)));
		    this.SD_Branch.tray.addResult("thigger " + this.name + " recently modified time: " 
		    				   + formatter.format(this.modTime.get(this.modTime.size() - 1)) + " since "
		    				   + formatter.format(this.modTime.get(this.modTime.size() - 2)) + "\r\n");
		}//trigger: modified
		else if (kinds == 3){
			System.out.println("thigger " + this.name + " the path change: (" + this.file.get(this.file.size() - 2).getAbsolutePath() + ") to (" 
								+ this.file.get(this.file.size() - 1).getAbsolutePath() + ")");
			this.SD_Branch.tray.addResult("thigger " + this.name + " the path change: (" + this.file.get(this.file.size() - 2).getAbsolutePath() + ") to (" 
								+ this.file.get(this.file.size() - 1).getAbsolutePath() + ")" + "\r\n");
		}//trigger: path changed
		else if (kinds == 4){
			System.out.println("thigger " + this.name + " the size change: " + this.size.get(this.size.size() - 2) + "b to " 
								+ this.size.get(this.size.size() - 1)  + "b.");
			this.SD_Branch.tray.addResult("thigger " + this.name + " the path change: (" + this.file.get(this.file.size() - 2).getAbsolutePath() + ") to (" 
											+ this.file.get(this.file.size() - 1).getAbsolutePath() + ")" + "\r\n");
		}//trigger: size changed
	}
	public synchronized void Recover(){
		/*File temp1 = this.file.get(this.file.size() - 1)
		File temp2 = this.file.get(this.file.size() - 2);
		this.addOneFile(temp2);
		this.delOneFile();*/
		this.file.get(1).renameTo(this.file.get(0));
		File temp1 = this.file.get(this.file.size() - 1);
		File temp2 = this.file.get(this.file.size() - 2);
		this.addOneFile(temp2);
		this.delOneFile();
		System.out.println("the recover has been done.");
	}//restore the last statistic, swap them
	public synchronized void harmonyAssignment(int i, int j){
		if (i == 1)
			this.Summary();
		else if (i == 2)
			this.Detail(j);
		else if (i == 3)
			this.Recover();
		else
			;
	}
	//assignment zone
	public void run(){
		int tgrKind = this.requests[1].equals("renamed") ? 1 :
					  this.requests[1].equals("modified")? 2 : 
					  this.requests[1].equals("path-changed") ? 3 : 
					  this.requests[1].equals("size-changed") ? 4 :
					  0;
		int asmKind = this.requests[2].equals("record-summary") ? 1 :
						 this.requests[2].equals("record-detail") ? 2 :
						 this.requests[2].equals("recover") ? 3 :
						 0;
		try {sleep(1000 * 2);} catch (InterruptedException e) {e.printStackTrace();}
		//sleep for 10 seconds, let the change becomes stable.
		while (true){
			if (this.UnfindFlag){
				System.out.println("thread " + this.name + " terminated.");
				break;
			}
			boolean trigd = false;
			/*System.out.println("thread " + this.name + " start scanning.");*/
			trigd = this.harmonyTrigger(tgrKind);
			if (trigd)
				this.harmonyAssignment(asmKind, tgrKind);
			try {sleep(1000 * 10);/*System.out.println("thread " + this.name +" has finished one turn of scanning.")*/;} catch (InterruptedException e) {e.printStackTrace();}
		}//dead loop,each loop, the thread rest for several seconds
		//in other words, we do the loop every 10 seconds
	}
}
class InputRequest{
	//we do all the inputs here, first run in the 
	private String[][] attribute= new String[10][3];
	private int reqNum = 0;
	private String topDir;
	private String resultTxt;
	public Summary_Detail sd;//////////////////////////////////////////////////////////////////////
	public String[][] getAttribute(){
		return this.attribute;
	}
	public int getReqNum(){
		return this.reqNum;
	}
	public String getTopDir(){
		return this.topDir;
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
	public void splitRequest(String []attribute, String str){
		Pattern neg_1 = Pattern.compile("\\|(.*)\\|");
    	Matcher neg_1n = neg_1.matcher(str.trim());
		Pattern neg_2 = Pattern.compile("(renamed|modified|path-changed|size-changed)");
    	Matcher neg_2n = neg_2.matcher(str.trim());
		Pattern neg_3 = Pattern.compile("(record-summary|record-detail|recover)");
    	Matcher neg_3n = neg_3.matcher(str.trim());
    	if (neg_1n.find())
    		attribute[0] = neg_1n.group().substring(1, neg_1n.group().length() - 1);
    	if (neg_2n.find())
    		attribute[1] = neg_2n.group();
    	if (neg_3n.find()) 
    		attribute[2] = neg_3n.group();
	}
	public boolean isEnd(String str){
		final String endInput = "end";
		Pattern neg_1 = Pattern.compile(endInput);
    	Matcher neg_nx = neg_1.matcher(str.trim());
    	if (neg_nx.find()) return true;
    	else return false;
		
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
	public void startInput(){
		System.out.println("ready to go: ");		
		while(true){
			System.out.println("please input the extent：");//top directory, all the file and relevant operation should not beyond this extent
			String temp = new Scanner(System.in).nextLine();
			File t = new File(temp.trim());
			if (!t.exists()){
				System.out.println("wrong directory, please re-input.");
				continue;
			}
			else if (NumOfFiDi(new File(temp.trim())) > 100){
				System.out.println("too many files/directories below, please choose another top directory");
				continue;
			}
			else if (!new File(temp.trim()).exists()){
				System.out.println("invalid input, please input another directory");
				continue;
			}
			else{
				this.topDir = temp.trim();
				break;
			}
		}
		//this.sd = new Summary_Detail(this.topDir + "\\" + "result.txt");///////////////////////////////////////////////
		System.out.println("the requests:");//new result file .txt
		while (this.reqNum < 5){ 
			@SuppressWarnings("resource")
			String str = new Scanner(System.in).nextLine();//String newsc = str.replaceAll("\\s+", "");
			if (isValidString(str)){
				splitRequest(this.attribute[this.reqNum], str);
				this.reqNum++;
			}//valid request
			else if (isEnd(str)){
				break;
			}
			else{
				System.out.println("invalid input, please input another request.");
				continue;
			}
		}
		System.out.println("input complete");
	}
}
public class ArkhamOriginMain1{
	public static void main(String [] args){
		/*we should enter the requests here
		 * and then start the new threads*/
		try{
			InputRequest IR = new InputRequest();IR.startInput();
			int num = 0; num = IR.getReqNum();	
			String attr[][] = new String[10][3]; attr = IR.getAttribute();
			String topDir; topDir = IR.getTopDir();
			//
			Summary_Detail SD = new Summary_Detail(topDir + "\\" + "Result.txt");
			ExecutorService exec1 = Executors.newCachedThreadPool(new HandlerThreadFactory());
			exec1.execute(SD);
			//
			Trigger []tri = new Trigger[5];
			ExecutorService exec2 = Executors.newCachedThreadPool(new HandlerThreadFactory());
			for (int i = 0; i < num; i ++){
				tri[i] = new Trigger(attr[i], topDir, i + 1, SD);
				exec2.execute(tri[i]);
				//tri[i].start();
			}
			/*Tester test = new Tester();
			 * ExecutorService exec3 = Executors.newCachedThreadPool(new HandlerThreadFactory());
			 *exec3.execute(SD);
			 * */
			//测试用的线程将上面注释中的SD替换掉即可
		}catch(Throwable e){
			System.out.println("you are fucking wrong");
		}
	}
}
//
class Tray{
	private ArrayList<String> ResultList = new ArrayList<String>();
	public Tray(){
		this.ResultList.clear();
	}
	public synchronized ArrayList<String> getResultList(){
		return this.ResultList;
	}
	public synchronized void addResult(String res){
		this.ResultList.add(res);
	}
	public synchronized void removeFirstResult(){
		this.ResultList.remove(0);
	}
}
class Summary_Detail extends Thread{
	public String address;
	public Tray tray;
	//
	public Summary_Detail(String addr){
		tray = new Tray();
		this.address = addr;
		File rec = new File(addr);
		try {
			rec.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//establish a file
	public void run(){
		while(true){
			synchronized (this){
				if (!this.tray.getResultList().isEmpty()){
					FileWriter fileWritter = null;
					try {fileWritter = new FileWriter(this.address,true);} catch (IOException e) {e.printStackTrace();}
		            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		            try {bufferWritter.write(this.tray.getResultList().get(0) + '\n');} catch (IOException e) {e.printStackTrace();}
		            this.tray.removeFirstResult();
		            try {bufferWritter.close();} catch (IOException e) {e.printStackTrace();}
				}
			}
		}
	}
	//running
}

class Tester extends Thread{
	public void readInformation(String str){
		;
	}
	public void renameFile(String str){
		;
	}
	public void moveFileTo(String str){
		;
	}
	public void newFile(String str){
		;
	}
	public void deleteFile(String str){
		;
	}
	public void writeFile(String str){
		;
	}
	public void run(){
			//if ?? then use the method above
		while(true){
			String str=new Scanner(System.in).nextLine();
			//...........根据实际的input来做出判断，调用上边的方法来修改文件
			try {
				sleep(1000 * 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}








/*public boolean isValidString(String str){
	final String reqFormat = "^IF \\((.*)\\) (renamed|modified|path-changed|size-changed) THEN (record-summary|record-detail|recover)$";
	Pattern neg_x = Pattern.compile(reqFormat.trim());
	Matcher neg_xn = neg_x.matcher(str.trim());
	Pattern neg_1 = Pattern.compile("\\((.*)\\)");
	Matcher neg_1n = neg_1.matcher(str.trim());
	Pattern neg_2 = Pattern.compile("(renamed|modified|path-changed|size-changed)");
	Matcher neg_2n = neg_2.matcher(str.trim());
	Pattern neg_3 = Pattern.compile("(record-summary|record-detail|recover)");
	Matcher neg_3n = neg_3.matcher(str.trim());
	 
	boolean ex = neg_xn.find();
	if (!neg_xn.find()) return false;
	else return true;
	
	if (!ex) 
		return false;
	File temp = new File(neg_1n.group());
	if (!temp.exists()) return false;
	if (temp.isDirectory() && (neg_2n.group().equals("renamed") || neg_2n.group().equals("path-changed"))) {
		return false;
	}
	return true;
}*/

/*if (this.file.get(0).exists()){
	this.file.get(0).delete();
}
if (!this.file.get(1).exists()){
	try {this.file.get(1).createNewFile();} catch (IOException e) {System.out.println("raw 291 exists bug");}
}
//
if (!temp1.getName().equals(temp2.getName())){
	this.file.get(1).renameTo(this.file.get(0));
}*/

/*class Summary_Detail{
public int triggerTimes = 0;
public String moveChange = null;
public String nameChange = null;
public String sizeChange = null;
public String timeChange = null;
public String targetTxt;
public void createRecordFile(String addr){
	File rec = new File(addr);
	try {
		rec.createNewFile();
	} catch (IOException e) {
		e.printStackTrace();
	}
	this.targetTxt = addr;
}
public Summary_Detail(String ad){
	createRecordFile(ad);
}
}*/

/*
public ArrayList<Long> gatherAllTime(File file) {
	ArrayList<Long> table = new ArrayList<Long>();
    if (file.isFile()) {
    	table.add(file.lastModified());
    } 
    else if (file.isDirectory()) {
        File [] filelist = file.listFiles();
        for (int i = 0; i < filelist.length; i++){
            File readfile = filelist[i];
            if (readfile.isFile()) {
            	table.add(readfile.lastModified());
            } else if (readfile.isDirectory()) {
            	table.addAll(gatherAllTime(readfile));
            }
        }
    }
    return table;
}
public ArrayList gatherAllSize(File file) {
	ArrayList table = new ArrayList();
    if (file.isFile()) {
    	table.add(file.length());
    } 
    else if (file.isDirectory()) {
        File [] filelist = file.listFiles();
        for (int i = 0; i < filelist.length; i++){
            File readfile = filelist[i];
            if (readfile.isFile()) {
            	table.add(readfile.length());
            } else if (readfile.isDirectory()) {
            	table.addAll(gatherAllSize(readfile));
            }
        }
    }
    return table;
}*/