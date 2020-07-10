package com.hanium.healthband;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectTcp {

    private String TAG = "tcp";

    public ConnectTcp() {
        Thread socket = new Thread() {
            public void run(){
                try { //클라이언트 소켓 생성

                    Socket socket = new Socket("13.125.206.125", 8888);
                    Log.e(TAG, "success");
                }  catch (UnknownHostException uhe) {
                    // 소켓 생성 시 전달되는 호스트(www.unknown-host.com)의 IP를 식별할 수 없음.
                    Log.e(TAG," 생성 Error : 호스트의 IP 주소를 식별할 수 없음.(잘못된 주소 값 또는 호스트이름 사용)");
                } catch (IOException ioe) {
                    // 소켓 생성 과정에서 I/O 에러 발생. 주로 네트워크 응답 없음.
                    Log.e(TAG," 생성 Error : 네트워크 응답 없음");
                } catch (SecurityException se) {
                    // security manager에서 허용되지 않은 기능 수행.
                    Log.e(TAG," 생성 Error : 보안(Security) 위반에 대해 보안 관리자(Security Manager)에 의해 발생. (프록시(proxy) 접속 거부, 허용되지 않은 함수 호출)");
                } catch (IllegalArgumentException le) {
                    // 소켓 생성 시 전달되는 포트 번호(65536)이 허용 범위(0~65535)를 벗어남.
                    Log.e(TAG," 생성 Error : 메서드에 잘못된 파라미터가 전달되는 경우 발생. (0~65535 범위 밖의 포트 번호 사용, null 프록시(proxy) 전달)");
                }
            }
        };

        socket.start();
    }
}
