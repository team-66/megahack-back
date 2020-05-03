package com.github.team66.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.team66.service.CloudVisionService;

@Controller
public class LeitorController {
	
	@Autowired
	private CloudVisionService cloudVisionService;

	@GetMapping("/foto")
	public String camera() {
		return "camera";
	}
	
	@PostMapping("/processarFoto")
	@ResponseBody
	public String processarFoto(String content) { System.out.println("Chegou aqui");
		// Obter somente o conteudo da foto
		String conteudo = content.split(",")[1];
		// Converte de DataURI para array de byte
		byte[] data = DatatypeConverter.parseBase64Binary(conteudo);
		Resource recurso = new ByteArrayResource(data);
		System.out.println("Enviando - ExtractPages");
		Map<String, String> mapa = cloudVisionService.extractPages(recurso);

		List<String> saida = new ArrayList<>();

		mapa.forEach((a, b) -> {
			System.out.println(a + " " + b);
			//if (a.contains("americanas.com") || b.contains("americanas.com"))
				saida.add(a + " " + b);
		});

		if (saida.isEmpty()) {
			System.out.println("Enviando - ExtractLabels");
			Map<String, Float> imageLabels = cloudVisionService.extractLabels(recurso);
			imageLabels.forEach((a, b) -> {
				System.out.println(a + " " + b);
				saida.add(a);
			});
		}

		String retorno = "";
		for (int i = 0; i < saida.size(); i++) {
			retorno += saida.get(i) + "<br>";
		}

		return retorno;
	}
	
}
