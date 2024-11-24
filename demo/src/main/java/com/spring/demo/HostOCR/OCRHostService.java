package com.spring.demo.HostOCR;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.demo.entity.HostInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OCRHostService {

    private static final String API_URL = "https://okn4itil0a.apigw.ntruss.com/custom/v1/36203/777f3fe5442448cde79ed04c677bc0f9f985186c220e54d73947b93f66ce5fcc/general";
    private static final String SECRET = "amNwd0p2YXh6Y1R1S1lCbEFkUU5yaHBIdVhnV0dHbUc=";

    private final HostRepository hostRepository;

    @Autowired
    public OCRHostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }
//
//    public OcrResultDto processImage(String filePath) {
//        try {
//            String responseBody = callOcrApi(filePath);
//            OcrResultDto resultDto = parseResponse(responseBody);
//            saveToDatabase(resultDto);
//            return resultDto;
//        } catch (Exception e) {
//            throw new RuntimeException("OCR 처리 중 오류 발생: " + e.getMessage(), e);
//        }
//    }
public String processImage(String filePath) {
    try {
        String responseBody = callOcrApi(filePath);

        return responseBody;
    } catch (Exception e) {
        throw new RuntimeException( e.getMessage(), e);//오류 낫으면
    }
}

    private String callOcrApi(String filePath) {
        try {
            HttpHeaders headers = createHeaders();
            MultiValueMap<String, Object> body = createRequestBody(filePath);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, requestEntity, String.class);


            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("API 응답 오류: " + response.getStatusCode());
            }

            return response.getBody();

        } catch (Exception e) {
            System.err.println( e.getMessage());
            throw e;
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-OCR-SECRET", SECRET);
        return headers;
    }

    private MultiValueMap<String, Object> createRequestBody(String filePath) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다: " + filePath);
        }


        JSONObject json = new JSONObject();
        json.put("version", "V2");
        json.put("requestId", UUID.randomUUID().toString());
        json.put("timestamp", System.currentTimeMillis());

        JSONArray images = new JSONArray();
        JSONObject image = new JSONObject();
        image.put("format", "jpg");
        image.put("name", "demo");
        images.add(image);

        json.put("images", images);
        body.add("message", json.toString());


        body.add("file", new FileSystemResource(file));

        return body;
    }
    private OcrResultDto parseResponse(String responseBody) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode resultNode = rootNode.path("images").get(0).path("bizLicense").path("result");

            OcrResultDto resultDto = new OcrResultDto();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            resultNode.fields().forEachRemaining(entry -> {
                String name = entry.getKey();
                String value = entry.getValue().path("text").asText();

                switch (name) {
                    case "registerNumber":
                        resultDto.setHostRegistNum(value);
                        break;
                    case "corpName":
                        resultDto.setHostCorpName(value);
                        break;
                    case "bisAddress":
                        resultDto.setHostBisAddress(value);
                        break;
                    case "openDate":
                        try {
                            Date openDate = dateFormat.parse(value);
                            resultDto.setHostOpenDate(openDate);
                        } catch (ParseException e) {
                            System.err.println("Invalid date format for openDate: " + value);
                        }
                        break;
                    case "bizType":
                        resultDto.setHostBisType(value);
                        break;
                    case "taxType":
                        resultDto.setHostTaxType(value);
                        break;
                    default:
                        System.out.println("Unrecognized field: " + name);
                        break;
                }
            });

            return resultDto;

        } catch (Exception e) {
            System.err.println("parseResponse 실패: " + e.getMessage());
            throw new RuntimeException("JSON 파싱 중 오류 발생: " + e.getMessage(), e);
        }
    }


    private void saveToDatabase(OcrResultDto resultDto) {
        HostInfo hostInfo = new HostInfo();

        hostInfo.setHostRegistNum(resultDto.getHostRegistNum());
        hostInfo.setHostCorpName(resultDto.getHostCorpName());
        hostInfo.setHostBisAddress(resultDto.getHostBisAddress());
        hostInfo.setHostBisType(resultDto.getHostBisType());
        hostInfo.setHostOpenDate(resultDto.getHostOpenDate());
        hostInfo.setHostTaxType(resultDto.getHostTaxType());

        hostRepository.save(hostInfo);
    }
}
