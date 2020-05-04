package com.github.team66.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature.Type;

@Service
public class CloudVisionService {

	@Autowired
	private CloudVisionTemplate cloudVisionTemplate;
	
	@Autowired
	private ApiLogService apiLogService;

	public Map<String, Float> extractLabels(Resource recurso) {
		
		if (!apiLogService.podeAcessar())
			throw new RuntimeException("Limite de acesso API");
		
		apiLogService.inserir("labels");
		
		AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(recurso, Type.LABEL_DETECTION);

		Map<String, Float> imageLabels = response.getLabelAnnotationsList().stream()
				.collect(Collectors.toMap(EntityAnnotation::getDescription, EntityAnnotation::getScore, (u, v) -> {
					throw new IllegalStateException(String.format("Duplicate key %s", u));
				}, LinkedHashMap::new));

		return imageLabels;
	}

}
