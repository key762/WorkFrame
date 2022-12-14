package io.github.key762.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@code @author:} gcl
 * {@code @create:} 2022/8/10 11:21
 */
public class MapUtil extends cn.hutool.core.map.MapUtil {

    /**
     * 使用value从集合中获取key集合(空Key时集合返回arg)
     * @param map HashMap集合
     * @param value value值
     * @param defaultObject 空key时集合返回值(默认为null)
     * @param <K> 泛型Key
     * @param <V> 泛型Value
     * @return List<Object> 获取的Key值集合
     */
    public static <K, V> List<Object> getKeysByValue(HashMap<K, V> map, String value, Object defaultObject){
        List<Object> result = new ArrayList<>();
        if ( !map.isEmpty() ) {
            map.keySet().stream().filter( x -> map.get(x).equals(value) ).forEach(result::add);
            if ( !result.isEmpty() ){
                return result;
            }
        }
        result.add(defaultObject);
        return result;
    }

    /**
     * 使用value从集合中获取key集合(空Key时集合返回null)
     * @param map HashMap集合
     * @param value value值
     * @param <K> 泛型Key
     * @param <V> 泛型Value
     * @return List<Object> 获取的Key值集合
     */
    public static <K, V> List<Object> getKeysByValue(HashMap<K, V> map, String value){
        return getKeysByValue(map, value,null);
    }

}
