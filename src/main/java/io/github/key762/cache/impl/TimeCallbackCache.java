package io.github.key762.cache.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import com.google.common.util.concurrent.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * {@code @author:} gcl
 * {@code @create:} 2022/8/10 11:21
 */
public class TimeCallbackCache {

    /** 线程池 */
    private static ExecutorService executorService;

    /** 数据集 */
    private static TimedCache<Object, Object> timeCallbackCache;

    /** 线程池回调 */
    private static ListeningExecutorService listeningExecutorService;

    /** 回调映射 */
    private static ConcurrentHashMap<Object, Consumer<Object>> callbackMap;

    /**
     * 构造(默认过期时长 5000 毫秒,过期检查间隔时间 5 毫秒)
     */
    public TimeCallbackCache() {
        timeCallbackCache = CacheUtil.newTimedCache(5000);
        timeCallbackCache.schedulePrune(5);
        callbackMap = new ConcurrentHashMap<>();
        executorService = ExecutorBuilder.create().build();
        listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
    }

    /**
     * 构造
     *
     * @param timeout 超时（过期）时长，单位毫秒
     * @param delay 过期检查间隔时长，单位毫秒
     */
    public TimeCallbackCache(long timeout, long delay) {
        timeCallbackCache = CacheUtil.newTimedCache(timeout);
        timeCallbackCache.schedulePrune(delay);
        callbackMap = new ConcurrentHashMap<>();
        executorService = ExecutorBuilder.create().build();
        listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
    }

    /**
     *
     * @param key 键
     * @param value 值
     * @param consumer 消费回调
     * @param <K> 泛型Key
     * @param <V> 泛型Value
     */
    public <K, V> void put(K key, V value, Consumer<Object> consumer) {
        timeCallbackCache.put(key, value);
        callbackMap.put(key, consumer);
        addListen(key, consumer);
    }

    /**
     *
     * @param key 键
     * @param value 值
     * @param timeout 过期时长
     * @param consumer 消费回调
     * @param <K> 泛型Key
     * @param <V> 泛型Value
     */
    public <K, V> void put(K key, V value, Long timeout, Consumer<Object> consumer) {
        timeCallbackCache.put(key, value, timeout);
        callbackMap.put(key, consumer);
        addListen(key, consumer);
    }

    /**
     * 获取缓存值
     * @param key Key值
     * @return Value值
     */
    public Object get(Object key) {
        return timeCallbackCache.get(key);
    }

    /**
     * 删除缓存和回调映射
     * @param key Key值
     */
    public void remove(Object key) {
        callbackMap.remove(key);
        timeCallbackCache.remove(key);
    }

    /**
     * 添加监听器
     * @param key Key值
     * @param consumer 回调方法
     */
    public static void addListen(Object key, Consumer<Object> consumer) {
        ListenableFuture<Object> listenableFuture =
                listeningExecutorService.submit(
                        () -> {
                            while (timeCallbackCache.containsKey(key)) {
                                ThreadUtil.sleep(500);
                            }
                            return key;
                        });
        Futures.addCallback(
                listenableFuture,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        consumer.accept(o);
                    }
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                },
                listeningExecutorService);
    }

}
