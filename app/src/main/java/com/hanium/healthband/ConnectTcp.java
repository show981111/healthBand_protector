package com.hanium.healthband;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectTcp {

    private String TAG = "tcp";
    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket socket;
    private Thread socketThread;

    private OutputStream out;
    private InputStream in;
    Timer timer;

    public ConnectTcp() {

    }//connectTcp

    void sendMessage(final String message){
         socketThread = new Thread() {
             public void run(){
                 int isSuccess = 0;
                try {
                    if (Thread.interrupted()) { throw new InterruptedException();}
                    while(!Thread.currentThread().isInterrupted()) {
                        // ...
                        try { //클라이언트 소켓 생성

                            socket = new Socket("13.125.245.251", 5656);
                            Log.e(TAG, "success");
                            isSuccess = 1;

                        }  catch (UnknownHostException uhe) {
                            // 소켓 생성 시 전달되는 호스트(www.unknown-host.com)의 IP를 식별할 수 없음.
                            Log.e(TAG," 생성 Error : 호스트의 IP 주소를 식별할 수 없음.(잘못된 주소 값 또는 호스트이름 사용)");
                        } catch (IOException ioe) {
                            // 소켓 생성 과정에서 I/O 에러 발생. 주로 네트워크 응답 없음.
                            Log.e(TAG," 생성 Error : 네트워크 응답 없음");
                            return;
                        } catch (SecurityException se) {
                            // security manager에서 허용되지 않은 기능 수행.
                            Log.e(TAG," 생성 Error : 보안(Security) 위반에 대해 보안 관리자(Security Manager)에 의해 발생. (프록시(proxy) 접속 거부, 허용되지 않은 함수 호출)");
                        } catch (IllegalArgumentException le) {
                            // 소켓 생성 시 전달되는 포트 번호(65536)이 허용 범위(0~65535)를 벗어남.
                            Log.e(TAG," 생성 Error : 메서드에 잘못된 파라미터가 전달되는 경우 발생. (0~65535 범위 밖의 포트 번호 사용, null 프록시(proxy) 전달)");
                        }
                        if(isSuccess == 0) return;
                        try {
                            String sending = message;
                            byte[] byteArr = sending.getBytes("UTF-8");
                            out = socket.getOutputStream();
                            out.write(byteArr);
                            out.flush();
//                            dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                            //dis = new DataInputStream(socket.getInputStream());     // input에 받을꺼 넣어짐
//                            dos.writeUTF("CONNECT TO SERVER : "+ message);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.w("버퍼", "버퍼생성 잘못됨");
                            this.interrupt();
                        }

                        Log.w("버퍼","버퍼생성 잘됨");

                            // 서버에서 받아옴
                        try {

                            Log.w("------서버에서 받아온 값 ","시작");
                            while(true)
                            {
//                                line = reader.readLine();
//                                Log.w("------서버에서 받아온 값 ", "" + line);
                                byte[] byteArr = new byte[100];
                                InputStream is = socket.getInputStream();
                                int readByteCount = is.read(byteArr);
                                //Log.w("------서버에서 받아온 값 ", "" + byteArr);
                                String data = new String(byteArr, 0, readByteCount, "UTF-8");
                                Log.w("------서버에서 받아온 값 ", "" + data);
                            }
                        } catch (Exception e) {
                            Log.w("------서버EE ","EE" );
                        }

                    }
                } catch (InterruptedException consumed){
                /* Allow thread to exit */
                }



            }//run
        };//Thread

        socketThread.start();
    }

    void sendAdditionalMessage(final String data){

        Thread sendMessageThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    out = socket.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        sendMessageThread.start();

    }

    void sendDataEverySecond(){
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            int i = 0;
            @Override
            public void run()
            {
                String data = "send data every second" + i ;
                try {
                    out = socket.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);

    }


    void closeSocket() throws IOException {
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
        if(timer != null)
        {
            timer.cancel();
        }
        socketThread.interrupt();
    }


}//class
