package me.hncn.util.net;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by XMH on 2016/7/18.
 */
public class ScanGetPort {
    public static synchronized  int  getPot(int first){
        for(int i=first;i<65535;++i){
            ServerSocket ss=null;
            try {
                ss= new ServerSocket(i);
            } catch (IOException e) {
                //e.printStackTrace();
            }finally{
                if(ss!=null){
                    if(ss.isBound()){
                        try {
                            ss.close();
                            System.out.println(i);
                            return i;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
        return -1;
    }
}
