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

public class AutoCompleteUtils {

	private static AutoCompleteUtils instance;
	private static Trie<String, String> trie;

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
			ClassPathResource resource = new ClassPathResource("data/category.txt");
			FileInputStream input = new FileInputStream(resource.getFile());
			InputStreamReader reader = new InputStreamReader(input,"UTF-8");
			BufferedReader br = new BufferedReader(reader);

			trie = new PatriciaTrie<>();
			String tmp = "";

			while((tmp = br.readLine()) != null) {
				trie.put(tmp, tmp);
			}

		} catch (IOException e) {
			throw new BusinessException(ErrorCode.AUTO_COMPLETE_FAIL);
		}
	}

	public List<String> getPrefixMap(String keyword) {
		if(keyword.isEmpty()) return new ArrayList<>();

		SortedMap<String, String> map = trie.prefixMap(keyword);
		return map.values().stream().toList();
	}

}
