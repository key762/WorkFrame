package com.key762.workframe.untils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code @author:} gcl
 * {@code @create:} 2022/8/10 11:21
 */
public class MapUtil {

    /**
     * 使用value从集合中获取key集合
     * @param map HashMap集合
     * @param value value值
     * @return List<Object> 获取的Key值集合
     */
    public static <K, V> List<Object> getKeyByValue(HashMap<K, V> map, String value){
        if ( map.isEmpty() ) {
            return null;
        }
        return map.keySet().stream().filter( x -> map.get(x).equals(value) )
                  .collect(Collectors.toCollection(() -> new ArrayList<>(map.size())));
    }

}
