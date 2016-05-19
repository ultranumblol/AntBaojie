package wgz.com.antbaojie.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qwerr on 2016/4/8.
 */
public class FastJsonTools {


    /**
     * 用fastjson将jsonstring转换为实体类
     * @param jsonstr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(String jsonstr ,Class<T> cls){
        T t = null;
        t = JSON.parseObject(jsonstr,cls);
        return t;


    }

    /**
     * 用fastjson将str转换为实体类集合
     * @param jsonstr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeans(String jsonstr,Class<T> cls){
        List<T> list = new ArrayList<T>();
        list = JSON.parseArray(jsonstr, cls);
        return list;


    }

    /**
     * 用fastjson将jsonStr转换为listMap
     * @param jsonstr
     * @return
     */
    public static List<Map<String ,Object>> getlistmap(String jsonstr){
        List<Map<String ,Object>> list = new ArrayList<Map<String ,Object>>();
        //list = JSON.parseObject(jsonstr,new TypeReference<List<Map<String, Object>>>(){});
        try {
            list = JSON.parseObject(jsonstr,new TypeReference<List<Map<String, Object>>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
