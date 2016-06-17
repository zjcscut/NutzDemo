package net.wendal.nutzbook.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjinci
 * @version 2016/6/15 16:41
 */
public abstract class BaseAction extends ExportExcelAction{


    public Map<String, String> getJsonResult(String statusCode,
                                             String message, String navTabId) {
        Map<String, String> mp = new HashMap<String, String>();
        mp.put("statusCode", statusCode);
        mp.put("message", message);
        mp.put("navTabId", navTabId);
//        mp.put("rel", rel);
//        mp.put("callbackType", callbackType);
//        mp.put("forwardUrl", forwardUrl);
        return mp;
    }

    public Map<String, String> getJsonResult(String statusCode,
                                             String message, String navTabId, String rel, String callbackType,
                                             String forwardUrl) {
        Map<String, String> mp = new HashMap<String, String>();
        mp.put("statusCode", statusCode);
        mp.put("message", message);
        mp.put("navTabId", navTabId);
        mp.put("rel", rel);
        mp.put("callbackType", callbackType);
        mp.put("forwardUrl", forwardUrl);
        return mp;
    }

}
