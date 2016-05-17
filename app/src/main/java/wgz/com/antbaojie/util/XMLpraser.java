package wgz.com.antbaojie.util;

import android.util.Log;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * dom4j解析XML
 * Created by qwerr on 2016/5/9.
 */
public class XMLpraser {
    private Map<String , Object> map ;
    private List<Map<String ,Object>> data;
    private boolean isFirstin= true;

    public List<Map<String ,Object>> praser(InputStream inputStream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        //获取根元素
        Element root = document.getRootElement();
        Log.i("XMLpraser", "Root: " + root.getName());
        data = new ArrayList<>();
        //遍历
        listNodes(root);
        data.add(map);
        return data;


    }

    private void listNodes(Element node) {
        if (node.getName().equals("Table")){
            if (isFirstin){
                map = new HashMap<>();
                isFirstin = false;

            }else {
                data.add(map);

            }
            //如果节点不为空，则输出
            if (!(node.getTextTrim().equals(""))){
                map.put(node.getName(),node.getText());


            }if (node.getTextTrim()==null){
                map.put(node.getName(),"---");

            }
            //同时迭代当前节点下面的所有子节点
            //使用递归
            Iterator<Element> iterator = node.elementIterator();
            while(iterator.hasNext()){
                Element e = iterator.next();
                listNodes(e);
            }

        }


    }

}
