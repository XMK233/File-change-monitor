package Week6;//actually it is week 7 now.

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Heheda extends Thread{
	int name = 0;
	int num = 0;
	public Heheda(int n){
		this.name = n;
	}
	public void run(){
		while (this.num < 10){
			System.out.println("this thread named " + this.name + "is " + this.num + " now");
			this.num++;
		}
	}
}
public class test implements Runnable {
	private int total;
    private int count;
    public test() {
       total = 100;
       count = 0;
    }
    public void run() {
       while (total > 0) {
    	   if(this.total > 0) {
    		   try {
    			   Thread.sleep(new Random().nextInt(1));
               } catch (InterruptedException e) {
            	   e.printStackTrace();
               }
               synchronized (this){//2
            	   if (total > 0){//3
            		   count++;
            		   total--;
            		   System.out.println(Thread.currentThread().getName() + "\t当前票号：" + count);
                   }
               } // using synchronized key word to lock the codes
           }
       }
    }
    public static void main(String[] args) {
    	/*test st = new test();
    	for(int i=1; i<=5; i++) {
    		new Thread(st, "售票点").start();
    	}*/
    	int i = 0;
    	Heheda [] nimabi = new Heheda[5];
    	String str=new Scanner(System.in).nextLine();
    	//String format = "/^[a-zA-Z]:[\\]((?! )(?![^\\/]*\s+[\\/])[\w -]+[\\/])*(?! )(?![^.]*\s+\.)[\w -]+$/";
    	final String reqFormat = "^(IF \\((.*)\\) (renamed|modified|path-changed|size-changed) "
				+ "THEN (record-summary|record-detail|recover))$";
    	Pattern neg_2 = Pattern.compile("\\((.*)\\)");
    	Matcher neg_nx = neg_2.matcher(str.trim());
    	if (neg_nx.find()){
    		System.out.println("positive");
    	}
    	else {
    		System.out.println("nagetive");
    	}
    }
}
/* the way to  implement a runnable: implement a runnable, than new a runnable, after that 
   new some threads with the construct way:
   Thread(Runnable target, String name)
*/