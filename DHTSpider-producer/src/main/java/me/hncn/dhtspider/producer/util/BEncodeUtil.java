package me.hncn.dhtspider.producer.util;
import net.seedboxer.bencode.BDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BEncodeUtil 加密  解密
 * Created by XMH on 2016/6/11.
 */
public class BEncodeUtil {
    //线程池
    private final static ExecutorService exec = Executors.newFixedThreadPool(1);
    private  Logger logger = LoggerFactory.getLogger(BEncodeUtil.class);

    private  BDecoder bDecoder = new BDecoder();
    public byte[] encode(Object obj){
        ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
        byteBuffer =encode(obj,byteBuffer);
        byte[] bytes = null;
        try {
            bytes=  byteBufferToStr(byteBuffer);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byteBuffer.clear();
        byteBuffer.flip();
        return  bytes;
    }
    /**
     * BEncode encode 加密
     *
     * @param obj java 对象 仅支持 Integer Long  String Map List
     * @return 返回 BEncode编码的字符串
     */
    private   ByteBuffer encode(Object obj,ByteBuffer byteBuffer) {

        if (obj instanceof Integer || obj instanceof Long) {
            Long value;
            if (obj instanceof Integer) {
                value = Long.valueOf((Integer) obj);
            } else {
                value = (Long) obj;
            }
            byteBuffer.put((byte) Constant.ASCII_i).put(String.valueOf(value).getBytes()).put((byte) Constant.ASCII_e);
        }
        if (obj instanceof byte[]) {
            byte[] value = (byte[]) obj;
            byteBuffer.put(String.valueOf(value.length).getBytes());
            byteBuffer.put((byte) Constant.ASCII_colon);
            byteBuffer.put(value);
        }
        if (obj instanceof String) {
            String value = (String) obj;
            byteBuffer.put(String.valueOf(value.length()).getBytes());
            byteBuffer.put((byte) Constant.ASCII_colon);
            byteBuffer.put(value.getBytes());
        }
        if (obj instanceof Map) {
            Map value = (Map) obj;
            Iterator<Object> keys = value.keySet().iterator();
            byteBuffer.put((byte) Constant.ASCII_d);
            while (keys.hasNext()) {
                Object key = keys.next();
                encode(key,byteBuffer);
                encode(value.get(key),byteBuffer);
            }
            byteBuffer.put((byte) Constant.ASCII_e);
        }
        if (obj instanceof List) {
            List value = (List) obj;
            byteBuffer.put((byte) Constant.ASCII_l);
            for (Object o : value) {
                encode(o,byteBuffer);
            }
            byteBuffer.put((byte) Constant.ASCII_e);
        }
        return byteBuffer;
    }
   /* public static byte[] encode(final Iterable<?> l){
        return bencode.encode(l);
    }
    public static byte[] encode(final Map<?, ?> m){
        return bencode.encode(m);
    }*/

    /**
     * BEncode  decode  解码
     *
     * @param bytes 传入的BEncode  bytes
     * @return 返回 java 对象
     */
    public  Map decode(final byte[] bytes) {
        try {
            return bDecoder.decodeByteArray(bytes);
        } catch (IOException e) {
            logger.error("bDecoder error {}",e.getMessage());
        }
        return null;
    }


   /* *//**
     * BEncode  decode  解码
     *
     * @param str 传入的BEncode  str
     * @return 返回 java 对象
     *//*
    public static Object decode(String str) {
       *//* try {

            //实现有返回值的多线程
            Callable<Object> callable = new Callable<Object>() {
                public Object call() throws Exception {
                    List<String> list = new ArrayList<>();
                    list.addToQueue(str);
                    return decode(list);
                }
            };

            //实现超时控制
            Future<Object> future = exec.submit(callable);
            return future.get(1000 * 2, TimeUnit.MILLISECONDS); //任务处理超时时间设为 2 秒
        }catch (InterruptedException | TimeoutException | ExecutionException e) {
            logger.error("解码 失败 {}",e.getMessage());
        }
       *//*
        List<String> list = new ArrayList<>();
        list.addToQueue(str);
        try {
            return decode(list);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
        // return bencode.decode(bytes,  type);
    }

    *//**
     * @param stringList 临时存储str 因为String类型为final类，不可修改
     * @return Object 返回 java 对象
     *//*
    private static Object decode(List<String> stringList) throws UnsupportedEncodingException {
        String str = stringList.get(0);
        Object obj = null;
        if (str.length() <= 1) {
            return null;
        }
        char first = str.charAt(0);

        Integer nextStartIndex = 0;
        if (first >= '0' && first <= '9') { // 数字 开头 为 str
            String[] strs = str.split(":");
            Integer length = Integer.valueOf(strs[0]);
            nextStartIndex = strs[0].length() + 1 + length;
            obj = strs[1].substring(0, length); //找到截断点
        }

        if ('i' == first) {// i 开头 为 数字
            Integer endIndex = str.indexOf("e");
            String content = str.substring(1, endIndex);
            nextStartIndex = endIndex + 1; //找到截断点
            obj = Long.valueOf(content);
        }
        if ('l' == first) {// l 开头 为 list

            String content = str.substring(1);
            List<Object> list = new ArrayList<>();
            boolean flag = true;
            stringList.set(0,content);
            while (flag) {
                if (stringList.get(0).startsWith("e")) {
                    nextStartIndex = str.indexOf(stringList.get(0)) + 1; //找到截断点
                    break;
                }
                Object object = decode(stringList);
                if (object != null) {
                    list.addToQueue(object);
                } else {
                    flag = false;
                }
            }
            obj = list;
        }
        if ('d' == first) {//d  开头 为 map
            String content = str.substring(1);
            Map<Object, Object> map = new HashMap<>();
            boolean flag = true;

            stringList.set(0,content);
            while (flag) {
                if (stringList.get(0).startsWith("e")) { //如果遇到 e 代表 结束 弹出
                    nextStartIndex = str.indexOf(stringList.get(0)) + 1; //找到截断点
                    break;
                }
                Object key = decode(stringList);
                Object value = decode(stringList); //递归调用
                if (key != null) {
                    map.put(key, value);
                } else {
                    flag = false;
                }
            }
            obj = map;
        }
        String temp = str.substring(nextStartIndex); //截短str
        stringList.set(0, temp);

        return obj;
    }*/


    /**
     * BEncode  decode  解码
     *
     * @param bytes 传入的BEncode  str
     * @return 返回 java 对象
     */
    /*public  Object decode(final byte[] bytes) {
       *//* try {
            //实现有返回值的多线程
            Callable<Object> callable = new Callable<Object>() {
                public Object call() throws Exception {
                    BEncode bEncode = new BEncode(bytes);
                    return decode(bEncode,null);
                }
            };

            //实现超时控制
            Future<Object> future = exec.submit(callable);
            return future.get(1000 * 2, TimeUnit.MILLISECONDS); //任务处理超时时间设为 2 秒
        }  catch (Exception e) {
            logger.error("解码 失败 {}",e.getMessage());
        }*//*
        BEncode bEncode = new BEncode(bytes);
        try {
            return decode(bEncode,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/


    /**
     * @param bEncode bEncode
     * @return Object 返回 java 对象
     */
    private  Object decode(BEncode bEncode, String type) throws Exception {
        byte[] bytes = bEncode.data;
        Object obj = null;
        if (bytes.length <= 1) {
            return null;
        }
        int first = new Byte(bytes[bEncode.getIndex()]).intValue();

        if (first!=Constant.ASCII_e){
            Integer nextStartIndex = bEncode.getIndex();
            if (first >= Constant.ASCII_0 && first <= Constant.ASCII_9) { // 数字 开头 为 str

                int colonIndex = -1;
                for(int i = bEncode.getIndex();i<bytes.length;i++){
                    Byte iByte = bytes[i];
                    if(iByte.intValue()==Constant.ASCII_colon){
                        colonIndex = i;
                        break;
                    }
                }

                Integer length = Integer.valueOf(new String(bytes,bEncode.getIndex(),colonIndex-bEncode.getIndex()));
                Integer offset = colonIndex+1;
                nextStartIndex = offset + length;
                if(type!=null&&"value".equals(type)){
                    byte[] str = new byte[length];
                    for(int i = 0;i<length;i++){
                        str[i]  =bytes[i+offset];
                    }
                    obj = str;
                }else {
                    obj = new String(bytes,offset,length); //找到截断点
                }
            }else if (Constant.ASCII_i == first) {// i 开头 为 数字
                int endIndex = -1;
                for(int i = bEncode.getIndex();i<bytes.length;i++){
                    Byte iByte = bytes[i];
                    if(iByte.intValue()==Constant.ASCII_e){
                        endIndex = i;
                    }
                }
                Integer length = endIndex-bEncode.getIndex()-1;
                Integer offset = bEncode.getIndex()+1;
                nextStartIndex = offset + length ;
                obj = Long.valueOf(new String(bytes,bEncode.getIndex()+1,endIndex-bEncode.getIndex()-1)); //找到截断点

            }else if (Constant.ASCII_l == first) {// l 开头 为 list
                List<Object> list= new ArrayList<>();
                boolean flag = true;
                bEncode.setIndex(bEncode.getIndex()+1);
                while (flag) {
                    first = new Byte(bytes[bEncode.getIndex()]).intValue();
                    if (first ==Constant.ASCII_e) {
                        nextStartIndex = bEncode.getIndex() + 1; //找到截断点
                        break;
                    }
                    Object object = decode(bEncode,null);
                    if (object != null) {
                        list.add(object);
                    } else {
                        flag = false;
                    }
                }
                obj = list;
            }else if (Constant.ASCII_d== first) {//d  开头 为 map

                Map<Object, Object> map = new HashMap<>();
                boolean flag = true;
                bEncode.setIndex(bEncode.getIndex()+1);
                while (flag) {
                    first = new Byte(bytes[bEncode.getIndex()]).intValue();

                    if (first==Constant.ASCII_e) { //如果遇到 e 代表 结束 弹出
                        nextStartIndex = bEncode.getIndex() + 1; //找到截断点
                        break;
                    }
                    Object key = decode(bEncode,null);
                    Object value = decode(bEncode,"value"); //递归调用
                    if (key != null) {
                        map.put(key, value);
                    } else {
                        flag = false;
                    }
                }
                obj = map;
            } else {
                throw new Exception("传入BEncode格式错误!");
            }

            bEncode.setIndex(nextStartIndex);

        }
        return obj;
    }

    private class  BEncode{
        private Integer index;
        private byte[] data;
        public BEncode(byte[] data){
            this.data = data;
            this.index = 0;
        }
        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public byte[] getData() {
            return data;
        }
    }

    /**
     * 有更优算法 ，现在的做法效率低 for循环 花费约 4ms
     * @param byteBuffer ByteBuffer
     * @return String
     * @throws UnsupportedEncodingException
     */
    private byte[] byteBufferToStr(ByteBuffer byteBuffer) throws UnsupportedEncodingException {
        int length = 0;
        for(int i=byteBuffer.array().length-1;i<byteBuffer.array().length;i--){
            byte b = byteBuffer.array()[i];
            if(b!=0){
                length= i+1;
                break;
            }
        }
        byte[] bytes = new byte[length];
        for(int i = 0;i<length;i++){
            bytes[i] =  byteBuffer.array()[i];
        }
        return bytes;
    }

}
