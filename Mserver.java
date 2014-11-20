import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;
import java.text.*;
public class Mserver implements Runnable{
    String str;
    InetAddress ip;
    Thread t1,t2,t3;
     int load=0;
    PriorityQueue<MyClass> que=new PriorityQueue<MyClass>();
     int port;
	public Mserver(int port){
		t1=new Thread(this);
		t2=new Thread(this);
		t3=new Thread(this);
                this.port=port;
 	}
    public void run(){
		DataInputStream br=new DataInputStream(System.in);
		while(true){
			try{
				Thread.sleep(700);
			}catch(Exception ex){
				System.out.println(ex);			
			}
			if(Thread.currentThread()==t1){
				receiveData(port);
			}else if(Thread.currentThread()==t2){
				sendData();
			}else{
				try{
					br.readLine();//waits for any key -enter
					
				}catch(IOException ex){
					System.out.println(ex);
				}
                        }
                    }
               } 
          int[][] a,b;
    int m,n,p,q;
   
    public void receiveData(int port){
        try {
                 DatagramSocket ds=new DatagramSocket(port);
            byte data[]=new byte[255];
                DatagramPacket dp=new DatagramPacket(new byte[255], 255);
                ds.receive(dp);
                data=dp.getData();
		System.out.println(new String(data));
                String sts=new String(data);
		sts=sts.trim();
		
                System.out.println("Received from client : '"+sts+"'");
                String st[]=sts.split(" ");
                m=Integer.parseInt(st[0]);
                n=Integer.parseInt(st[1]);
                System.out.println("m="+m+" n="+n);
                a=new int[m][n];
		int k=2;
                for(int i=0;i<m;i++){
                    for(int j=0;j<n;j++){
                        a[i][j]=Integer.parseInt(st[k++]);
                         System.out.println(""+a[i][j]);
                    }
                }
		k++;
                p=Integer.parseInt(st[m*n+2]);
                q=Integer.parseInt(st[m*n+3]);
                System.out.println("p="+p+" q="+q);
                b=new int[p][q];
                for(int i=0;i<p;i++){
                    for(int j=0;j<q;j++){
                        b[i][j]=Integer.parseInt(st[++k]);
                        System.out.println(""+b[i][j]);
                    }
                }
                ip=dp.getAddress();//st[m*n+p*q+4];
		int ports=Integer.parseInt(st[m*n+p*q+4]);
		//System.out.println("port"+ports);
		//System.out.println(ip.toString());
                que.add(new MyClass(m, n, p, q, a, b,ip,ports));
                 ds.close();
        } catch (SocketException ex) {
           System.out.println(ex.getMessage());
        } catch (IOException ex) {
             System.out.println(ex.getMessage());
        }
    }
  public int[][] multiply(int[][] m1, int[][] m2){
      int m1rows = m1.length;
      int m1cols = m1[0].length;
      int m2rows = m2.length;
      int m2cols = m2[0].length;
      if (m1cols != m2rows){
        throw new IllegalArgumentException("Matrix multiplication not possible");		
      }
    else{ 
         int[][] result = new int[m1rows][m2cols];
         for (int i=0; i< m1rows; i++){
            for (int j=0; j< m2cols; j++){
                 result[i][j]=0;
               for (int k=0; k< m1cols; k++){
                  result[i][j] += m1[i][k] * m2[k][j];
                 
               }
            }
         }
  
          return result;
      }	       
   }
    public void mprint(int[][] a){
	int rows = a.length;
	int cols = a[0].length;
	for (int i=0; i< rows; i++){
	 	for (int j=0; j< cols; j++)
	    		{System.out.print(" " + a[i][j] + ",");}
	 System.out.println("\n");
	}
	System.out.println("\n");
     }   
            
 public void sendData(){
        try {
		str="";
	   if(que.size()>=1){
		//System.out.println("Load on Server :"+que.size());
		MyClass mc=que.poll(); //pop()
            	DatagramSocket ds=new DatagramSocket();
            	DataInputStream dis=new DataInputStream(System.in);
            	byte data[]=new byte[255];
		int port=0;
               try{
                InetAddress ip=mc.qip;
			port=mc.qport;
		        int[][]c=multiply(a,b);
                         load++;
			int rows = c.length;
		        int cols = c[0].length;
                         System.out.println("\n\nMatrix after multiplication");
                         mprint(c);
                          System.out.println("\nLoad on Server :"+load);
                         System.out.println("\nSending to client..."+port);
		         str=rows+" "+cols;
		         for(int i=0;i<rows;i++){
		             for(int j=0;j<cols;j++)
		               str+=" "+c[i][j];
		          }

            }catch(IllegalArgumentException e){
              
                   // System.out.println(e);
	            str=e.getMessage();
                 }
               System.out.println(str);
                  
                data=str.getBytes();
                DatagramPacket dp=new DatagramPacket(data,data.length,ip,port);
                ds.send(dp);
	    }

        }  catch (SocketException ex) {
             System.out.println(ex.getMessage());
        } catch (IOException ex) {
             System.out.println(ex.getMessage());
        } 
    }


     public static void main(String[] args) {
        int port=Integer.parseInt(args[0]);  //arg[0]=server port
        Mserver ser=new Mserver(port);
 	ser.t1.start();
	ser.t2.start();
	ser.t3.start();
	 }


}

