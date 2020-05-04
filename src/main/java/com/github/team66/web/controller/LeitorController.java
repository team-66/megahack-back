package com.github.team66.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.team66.service.CloudVisionService;
import com.github.team66.service.TradutorService;

@Controller
public class LeitorController {
	
	@Autowired
	private CloudVisionService cloudVisionService;
	
	@Autowired
	private TradutorService tradutorService;
	
	@GetMapping("/scan")
	public String scan() {
		return "scan";
	}
	
	@PostMapping("/processarFoto")
	@ResponseBody
	public List<String> processarFoto(String content) { 
	
		if (StringUtils.isEmpty(content) || !content.contains(","))
			return new ArrayList<>();
		
		// Obter somente o conteudo da foto
		String conteudo = content.split(",")[1];
		
		// Converte de DataURI para array de byte
		byte[] data = DatatypeConverter.parseBase64Binary(conteudo);
		Resource recurso = new ByteArrayResource(data);
		try {
			System.out.println("Tamanho: " + recurso.contentLength());
		} catch (IOException e) {
		}
		
		List<String> saida = new ArrayList<>();
		System.out.println("Enviando - ExtractLabels");
		Map<String, Float> imageLabels = cloudVisionService.extractLabels(recurso);
		imageLabels.forEach((a, b) -> {
			System.out.println(a + " " + b);
			if (saida.size() < 5)
				saida.add(tradutorService.traduzir(a).replace(" ", "-"));
		});
		
		if (saida.size() < 5) {
			String[] outros = {"dia-das-maes", "ofertas", "mais-vendidos", "receba-hoje", "covid-19"};
			int i = 0;
			while (saida.size() < 5) {
				saida.add(outros[i]);
				i++;
			}
		}

		return saida;
	}
	
}
