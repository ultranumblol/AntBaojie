package wgz.com.antbaojie.util;

/**
 * Created by qwerr on 2016/5/18.
 */
public class PathMaker {
    public PathMaker() {
    }

    public String getPath(Object...params){
       String path = "http://192.168.7.34:8080/sms/queryAll.do";

       return path;
   }
    public String getQueryFinishedPath(Object...params){
        String path = "http://192.168.7.34:8080/sms/queryFinished.do";

        return path;
    }
    public String getQueryIngPath(Object...params){
        String path = "http://192.168.7.34:8080/sms/queryIng.do";

        return path;
    }

}
