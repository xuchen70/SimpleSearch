package com.mark.ss;

/**
 * Created by Administrator on 2017/1/6.
 */

public class API {
    public static String getSearchUrl(String type,String number){
        //https://www.kuaidi100.com/query?type=huitongkuaidi&postid=557004121149743&temp=0.18686221772990652&phone=
        StringBuffer sb = new StringBuffer("http://www.kuaidi100.com/query");
        sb.append("?type=").append(type == null?"":type);
        sb.append("&postid=").append(number==null?"":number);
        sb.append("&temp=").append(Math.random());//&temp=0.18686221772990652&phone=
        sb.append("&phone=");
        return sb.toString();

    }

    public static String autoDiscernNO(String number){
        //https://www.kuaidi100.com/autonumber/autoComNum?text=123456
        StringBuffer sb = new StringBuffer("https://www.kuaidi100.com/autonumber/autoComNum");
        sb.append("?resultv2=1");
        sb.append("&text=").append(number==null?"":number);
        return sb.toString();

    }
}
