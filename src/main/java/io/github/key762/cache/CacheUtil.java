package io.github.key762.cache;

import io.github.key762.cache.impl.TimeCallbackCache;

/**
 * {@code @author:} gcl
 * {@code @create:} 2022/8/10 11:21
 */
public class CacheUtil extends cn.hutool.cache.CacheUtil {

    /**
     * 创建定时回调缓存(默认过期时长 5000 毫秒,过期检查间隔时间 5 毫秒)
     * @return {@link TimeCallbackCache}
     */
    public static TimeCallbackCache newTimeCallbackCache(){
        return new TimeCallbackCache();
    }

    /**
     * 创建定时回调缓存
     * @param timeout 过期时长，单位：毫秒
     * @param delay 间隔时长，单位毫秒
     * @return {@link TimeCallbackCache}
     */
    public static TimeCallbackCache newTimeCallbackCache(long timeout, long delay){
        return new TimeCallbackCache(timeout, delay);
    }

}
