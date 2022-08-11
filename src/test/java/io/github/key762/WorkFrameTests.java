package io.github.key762;

import io.github.key762.untils.MapUtil;
import org.junit.Test;

import java.util.HashMap;

/**
 * {@code @author:} gcl
 * {@code @create:} 2022/8/10 11:11
 */
public class WorkFrameTests {

	@Test
	public void contextLoads() {
		HashMap<String, String> map = new HashMap<>();
		map.put("k1", "v");
		map.put("k2", "v");
		System.out.println(MapUtil.getKeyByValue(map,"v"));
	}

}
