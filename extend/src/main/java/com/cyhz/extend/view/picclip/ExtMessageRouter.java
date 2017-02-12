package com.cyhz.extend.view.picclip;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxiaolong on 16/12/17.
 */
public class ExtMessageRouter {

    private static ExtMessageRouter router = new ExtMessageRouter();
    private Map<String,Object> mesQ = new HashMap<>();
    private ExtMessageRouter(){

    }

    public void register(String tag,Object mes){
        mesQ.put(tag,mes);
    }

    public Object fetch(String tag){
        return mesQ.get(tag);
    }

    public static ExtMessageRouter invokeRouter(){
        return router;
    }
}
