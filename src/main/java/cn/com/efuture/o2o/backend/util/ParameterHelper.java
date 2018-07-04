package cn.com.efuture.o2o.backend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//入参处理
public class ParameterHelper {
    private static final int DEFAULT_PAGE_SIZE = 10;

    public static long getCount(Map<String, Object> map,PageCount pageCount){
        if (map.containsKey("page") && map.get("page") != null && Integer.parseInt(map.get("page").toString())<=1){
           return pageCount.getCount(map);
        }
        return -1;
    }


    public static void cookPageInfo(Map<String, Object> map) {
        if (map.containsKey("pageSize") && map.get("pageSize") != null) {
            map.put("pageSize", Integer.parseInt(map.get("pageSize").toString()));
        } else {
            map.put("pageSize", DEFAULT_PAGE_SIZE);
        }

        if (map.containsKey("page") && map.get("page") != null) {
            map.put("page", Integer.parseInt(map.get("page").toString()));
        } else {
            map.put("page", 1);
        }
    }

    //处理参数的数组:shopId
    public static void cookCityInfo(Map<String, Object> map) {
        if (map.containsKey("shopId")) {
            Object values = map.get("shopId");
            if (values instanceof List) {
                // 如果shopId为空数组，则为查询全部门店
                if (((List) values).isEmpty()) {
                    map.remove("shopId");
                }
            }
        }
        if (map.containsKey("city")) {
            if ("".equals(map.get("city").toString()) || "全部".equals(map.get("city").toString())) {
                map.remove("city");
            }else if(map.get("city") instanceof List){
                // 如果shopId为空数组，则为查询全部门店
                if (((List) map.get("city")).isEmpty() || "全部".equals(((List) map.get("city")).get(0))) {
                    map.remove("city");
                }
            }
        }
    }



    public static List signToSignArray(int sign) {
        List<Object> arrayList = new ArrayList<>();
        for (int j = 1; j <= sign; j *= 2) {
            if ((sign | j) == sign) {
                arrayList.add(j);
            }
        }
        return arrayList;
    }

    public static Integer signArrayToSign(List signArray) {
        Integer sign = 0;
        for (Object i : signArray) {
            sign += (Integer) i;
        }
        return sign;
    }
}
