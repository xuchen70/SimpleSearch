package com.mark.ss;

/**
 * Created by Administrator on 2017/1/6.
 */

public class API {
    public static String getSearchUrl(String type,String number){
        //http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号
        StringBuffer sb = new StringBuffer("http://www.kuaidi100.com/query");
        sb.append("?type=").append(type == null?"":type);
        sb.append("&postid=").append(number==null?"":number);
        return sb.toString();

    }
}
