import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
public class Mclient {
    DatagramPacket dp;
    DataInputStream dis;
    String str="";
    String serverip;
    public Mclient(String ip){
        serverip=ip;
    }
    public void sendData(int port){
        try {
            DatagramSocket ds=new DatagramSocket();
          
            byte a[]=new byte[255];
         	
                a=str.getBytes();
                System.out.println("\nData Sending to Server ");
		System.out.println(new String(a));
                dp=new DatagramPacket(a,a.length,InetAddress.getByName(serverip),port);
                ds.send(dp);
         
                
        } catch (SocketException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            
            System.out.println(ex);
        } 
    }

   public void receiveData(int port){
String str="";
        try {
            DatagramSocket ds=new DatagramSocket(port);
            byte data[]=new byte[255];
            
         
                DatagramPacket dp=new DatagramPacket(new byte[255], 255);
                ds.receive(dp);
                
                data=dp.getData();
                str=new String(data);
                
		String st[]=str.trim().split(" ");
		if(st.length>1){
			int m=Integer.parseInt(st[0]);
		        int n=Integer.parseInt(st[1]);
		        int a[][]=new int[m][n];
			int k=2;
		        for(int i=0;i<m;i++){
		            for(int j=0;j<n;j++){
		                a[i][j]=Integer.parseInt(st[k++]);
                                // System.out.println(st.length);
		            }
		        }
                       System.out.println("\n\nResult :\n");
			mprint(a);
		}else{
			System.out.println(" "+str.trim()+" ");
		        
            	}
                ds.close();
        } catch (NumberFormatException ex) {
            System.out.println(" "+str.trim()+" ");
        }catch (SocketException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
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

   private void readMatrix() {
       	str="";
         try{
            dis=new DataInputStream(System.in);
            System.out.println("Enter number of rows of first matrix");
            int m=Integer.parseInt(dis.readLine());
            str+=m+" ";
            System.out.println("Enter number of columns of first matrix");
            int n=Integer.parseInt(dis.readLine());
            str+=n+" ";
            System.out.println("Enter the elements of first matrix");
            for(int i=0;i<m;i++)
                for(int j=0;j<n;j++)
                   str+=Integer.parseInt(dis.readLine())+" ";              
            
            System.out.println("Enter number of rows of 2nd matrix");
            int p=Integer.parseInt(dis.readLine());
            str+=p+" ";
            System.out.println("Enter number of columns of 2nd matrix"); 
            int q=Integer.parseInt(dis.readLine());
            str+=q+" ";
            System.out.println("Enter the elements of 2nd matrix");
            for(int i=0;i<p;i++)
                for(int j=0;j<q;j++)
                 str+=Integer.parseInt(dis.readLine())+" ";
       } catch (IOException ex) {
		    System.out.println(ex);
		}
    }

  public static void main(String[] args) {
        
        Mclient cli=new Mclient(args[0]); //arg[0]=server ip
        while(true){

	    Random r = new Random();
	    int fourDigit = 1000 + r.nextInt(100000);
            cli.readMatrix();
	    cli.str+=args[1]; //attach client port ,arg[1]
            System.out.println("\nRandom number : "+fourDigit);
            cli.sendData(Integer.parseInt(args[2+fourDigit%2])); //agr[2]=server1 ,arg[3]=server2
            cli.receiveData(Integer.parseInt(args[1]));
        }
    }
    
}
