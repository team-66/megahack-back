package com.github.team66.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class TradutorService {

	public String traduzir(String original) { 
		
		original = original.toLowerCase();
		
		String url = "https://dictionary.cambridge.org/pt/dicionario/ingles-portugues/" + original;
		try {
			Document doc = Jsoup.connect(url).get();
			String traducao = doc.body().select(".trans.dtrans.dtrans-se").text();
			traducao = traducao.trim();
			if (traducao.contains(",")) {
				traducao = traducao.split(",")[0].trim();
			}
			if (traducao.isEmpty())
				return original;
			return traducao;
		} catch (Exception ex) {
			return original;
		}
	}
	
	
}
