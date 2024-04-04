package com.ssafy.kkoma.global.util.complete;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.core.io.ClassPathResource;

import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoCompleteUtils {

	private static AutoCompleteUtils instance;
	private static Trie<String, List<String>> trie;

	private AutoCompleteUtils() {
		getCategory();
	}

	public static AutoCompleteUtils getInstance() {
		if (instance == null) {
			instance = new AutoCompleteUtils();
		}
		return instance;
	}

	private static void getCategory() {
		try {
			InputStreamReader reader = new InputStreamReader(new ClassPathResource("/data/category.txt").getInputStream(),"UTF-8");
			BufferedReader br = new BufferedReader(reader);

			trie = new PatriciaTrie<>();
			String tmp = "";

			while((tmp = br.readLine()) != null) {
				for (int i = 0; i < tmp.length(); i++) {
					if(trie.containsKey(tmp.substring(i))) {
						List<String> value = trie.get(tmp.substring(i));
						if(!value.contains(tmp)) {
							value.add(tmp);
						}
					} else {
						List<String> value = new ArrayList<>();
						value.add(tmp);
						trie.put(tmp.substring(i), value);
					}
				}
			}

		} catch (IOException e) {
			log.debug(e.getMessage());
			throw new BusinessException(ErrorCode.AUTO_COMPLETE_FAIL);
		}
	}

	public List<String> getPrefixMap(String keyword) {
		if(keyword.isEmpty()) return new ArrayList<>();

		SortedMap<String, List<String>> map = trie.prefixMap(keyword);
		return map.values().stream()
			.collect(ArrayList::new, List::addAll, List::addAll);
	}

}
