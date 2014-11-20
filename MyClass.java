import java.util.*;
import java.text.*;
import java.net.*;
public class MyClass {
   int m,n,p,q,x[][],y[][],qport;
    InetAddress qip;
 
    MyClass(int r1,int r2,int c1,int c2,int a[][],int b[][],InetAddress ip,int port)
    {
      m=r1;
      n=r2;
      p=c1;
      q=c2;
     // x=new int[m][n];
      x=a;
      //y=new int[p][q];
      y=b;
      qip=ip;
      qport=port;
    }
    
}

    

