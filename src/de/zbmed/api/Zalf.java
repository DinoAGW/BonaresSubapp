package de.zbmed.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Zalf {
//	public static String zalfApiBaseUrl = "https://longtermarchiving-e.dataservice.zalf.de";
	public static String zalfApiBaseUrl = "http://142.132.163.56";

	public static void main(String[] args) throws Exception {
//		JsonNode uploads = getToAcknowledge();
//		System.out.println(uploads.toPrettyString());
//		JsonNode obj = uploads.get(0);
//		for (Iterator<String> iterator = obj.fieldNames(); iterator.hasNext();) {
//			String fieldName = iterator.next();
//			System.out.println(fieldName + " -> " + obj.get(fieldName));
//		}
		ResponseEntity<String> response = acknowledge("ed185616-7d18-4e45-81e7-26e6dcf679d9");
		System.out.println(response.toString());
		System.out.println("RestApi Ende");
	}

	public static ResponseEntity<String> acknowledge(String uuid) {
		RestTemplate restTemplate = new RestTemplate();
		String acknowledgesUrl = zalfApiBaseUrl + "/api/lza/acknowledge/" + uuid;
		ResponseEntity<String> response = restTemplate.postForEntity(acknowledgesUrl, null, String.class);
		return response;
	}

	public static JsonNode getUploads() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uploadsUrl = zalfApiBaseUrl + "/api/lza/uploads/";
		ResponseEntity<String> response = restTemplate.getForEntity(uploadsUrl, String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(response.getBody());
		return json;
	}

	public static JsonNode getToAcknowledge() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String uploadsUrl = zalfApiBaseUrl + "/api/lza/to_acknowledge/";
		ResponseEntity<String> response = null;
		int versuche = 20;
		while (true) {
			--versuche;
			try {
				response = restTemplate.getForEntity(uploadsUrl, String.class);
				break;
			} catch (Exception e) {
			}
			if (versuche == 0) {
				throw new Exception("Zalf API antwortet nicht");
			} else {
				System.err.println("getToAcknowledge hat nicht geklappt");
			}
			Thread.sleep(5000);
		}
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(response.getBody());
		return json;
	}
}
