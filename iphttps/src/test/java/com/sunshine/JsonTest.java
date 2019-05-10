package com.sunshine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiangzi
 * @ClassName JsonTest
 * @Description :
 * @Date 2019/5/9 20:06
 * @Version 1.0
 **/
public class JsonTest {

    public static void main(String[] args) {
        String json = "{\"aa\":\"bb\",\"cc\":2,\"dd\":{\"dd1\":\"value\",\"dd2\":3,\"dd3\":[{\"ddd1\":\"arry1\",\"ddd2\":3}," +
                "{\"ddd2\":\"arry22\",\"ddd3\":\"33\"}]},\"ee\":[{\"ee1\":\"arryee\",\"ee2\":2}]}";
        JsonNode jsonNode = parseJson(json, new JsonNode());
        System.out.println("toString  "+jsonNode.toString());
    }


    private static JsonNode parseJson(String josn, JsonNode node){
        if (josn.charAt(0)=='{'){
            System.err.println("解析{ ：  "+josn);
            node.setType(1);
            josn = josn.substring(1,josn.length()-1);
            System.out.println(josn);
            String[] strs = josn.split(",");
            int index = 0;
            int lastIndex = 0;
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<strs.length;i++) {
                if((strs[i].indexOf("{")>=0&&strs[i].indexOf("[")<0)||strs[i].indexOf("{")<strs[i].indexOf("[")){
                   index = strs[i].indexOf(":",1);
                    for(int j=i;j<strs.length;j++){
                        sb.append(strs[j]).append(",");
                    }
                    lastIndex = sb.lastIndexOf("},");
                    if (lastIndex==-1){
                        lastIndex = sb.lastIndexOf("}");
                    }
                    break;
                }else if (strs[i].indexOf("[")>=0){
                    index = strs[i].indexOf(":",1);
                    for(int j=i;j<strs.length;j++){
                        sb.append(strs[j]).append(",");
                    }
                    lastIndex = sb.lastIndexOf("],");
                    if (lastIndex==-1){
                        lastIndex = sb.lastIndexOf("]");
                    }
                    break;
                } else {
                    String[] split1 = strs[i].split(":");
                    if(split1[1].charAt(0)!='{'||split1[1].charAt(0)!='['){
                        node.getMap().put(split1[0],split1[1]);
                        node.getList().add(node.getMap());
                        node.getMap().clear();
                    }else{
                        node.getMap().put(split1[0],parseJson(split1[1],new JsonNode()));
                    }
                }
            }
            if (sb.length()>0){
                System.err.println("切割后：  "+sb.subSequence(index+1,lastIndex+1).toString());
                node.getMap().put(sb.subSequence(0,index),
                        parseJson(sb.subSequence(index+1,lastIndex+1).toString(),new JsonNode()));
                node.getList().add(node.getMap());
                node.getMap().clear();
                System.out.println(sb.charAt(sb.length()-2));
                if (lastIndex + 2< sb.length()&&sb.charAt(0)!='}'&&sb.charAt(0)!=']'){
                    parseJson(sb.subSequence(lastIndex + 2, sb.length()-1).toString(),node);
                }
            }
        }else if (josn.charAt(0)=='['){
            System.err.println("解析[ ：  "+josn);
            node.setType(2);
            josn = josn.substring(1,josn.length()-1);
            String[] split = josn.split("},");
            for (int j=0;j<split.length;j++) {
                if(j==split.length-1){
                    parseJson(split[j], new JsonNode());
                }else {
                    parseJson(split[j]+"}",new JsonNode());
                }

            }
        }else {
            System.err.println("解析 ：  "+josn);
            String[] split = josn.split(":");
            if(split.length>2){
                StringBuffer sb = new StringBuffer();
                for (int i=1;i<split.length;i++){
                    sb.append(split[i]).append(":");
                }
                node.getMap().put(split[0],parseJson(sb.subSequence(0,sb.length()-1).toString(),node));
            }else {
                node.getMap().put(split[0],parseJson(split[1],node));
            }
        }
        return node;
    }
}


class JsonNode{
    private JsonNode node;
    private Map map = new HashMap<String,Object>();
    private List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
    private int type;
    public JsonNode getNode() {
        return node;
    }

    public void setNode(JsonNode node) {
        this.node = node;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        StringBuffer sr = new StringBuffer();
        if (type==1){
            sb.append("{\n");
            for (HashMap<String, Object> map1 : list) {
                sb.append(map1.toString());
            }
           sb.append("}");
        }
        return sb.toString();
    }
}