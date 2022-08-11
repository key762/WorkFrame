package io.github.key762;

import cn.hutool.core.thread.ThreadUtil;
import io.github.key762.cache.CacheUtil;
import io.github.key762.cache.impl.TimeCallbackCache;
import io.github.key762.map.MapUtil;
import org.junit.Test;

import java.util.HashMap;

/**
 * {@code @author:} gcl
 * {@code @create:} 2022/8/10 11:11
 */
public class WorkFrameTests {

	@Test
	public void contextLoads1() {
		HashMap<String, String> map = new HashMap<>();
		map.put("k1", "v1");
		map.put("k2", "v1");
		System.out.println(MapUtil.getKeysByValue(map,"v1"));
		System.out.println(MapUtil.getKeysByValue(map,"v2"));
	}

	@Test
	public void contextLoads2() {
		TimeCallbackCache s = CacheUtil.newTimeCallbackCache( 5000 ,5);
		s.put( '1', '2', 2000L, System.out::println);
		s.remove('1');
		ThreadUtil.sleep(4000);
		s.put( '3', '4', System.out::println);
		ThreadUtil.sleep(8000);
	}

}
