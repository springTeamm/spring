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

import static org.apache.commons.lang3.time.DateUtils.parseDate;

@Service
public class OCRHostService {

    private static final String API_URL = "https://yzvtptc5u6.apigw.ntruss.com/custom/v1/36317/9171ec3e66f3924c4cd2e29840133243e3cd6af3256cfa9385c1d6aab4e34876/document/biz-license";
    private static final String SECRET = "SnpvUkdiRmdiZEJ3YlpQSWdTR25qR3Rya2ZndFlPYXc=";

    private final OcrHostRepository ocrhostRepository;

    @Autowired
    public OCRHostService(OcrHostRepository ocrhostRepository) {
        this.ocrhostRepository = ocrhostRepository;
    }

    public OcrResultDto processImage(String filePath) {
        try {
            String responseBody = callOcrApi(filePath);
            OcrResultDto resultDto = parseResponse(responseBody);
            saveToDatabase(resultDto);
            return parseResponse(responseBody);
        } catch (Exception e) {
            throw new RuntimeException("OCR 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    private String callOcrApi(String filePath) {
        HttpHeaders headers = createHeaders();
        MultiValueMap<String, Object> body = createRequestBody(filePath);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("OCR API 응답 오류: " + response.getStatusCode());
        }

        return response.getBody();
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

        String jsonMessage = String.format(
                "{\"version\": \"V2\", \"requestId\": \"%s\", \"timestamp\": %d, \"images\": [{\"format\": \"jpg\", \"name\": \"demo\"}]}",
                UUID.randomUUID(),
                System.currentTimeMillis()
        );

        body.add("message", jsonMessage);
        body.add("file", new FileSystemResource(file));

        return body;
    }
    private String cleanDateString(String rawDate) {
        // "년", "월", "일" 제거
        String cleaned = rawDate.replaceAll("[년월일]", "").trim();

        // 다중 공백을 단일 공백으로 변환
        cleaned = cleaned.replaceAll("\\s+", " ");
        return cleaned;
    }


    private OcrResultDto parseResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode resultNode = rootNode.path("images").get(0).path("bizLicense").path("result");

            OcrResultDto resultDto = new OcrResultDto();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");

            resultNode.fields().forEachRemaining(entry -> {
                String name = entry.getKey();
                JsonNode fieldArray = entry.getValue(); // 배열 데이터 처리

                if (fieldArray.isArray() && fieldArray.size() > 0) {
                    String rawValue = fieldArray.get(0).path("text").asText(); // 첫 번째 텍스트 값 추출
                    String value = cleanDateString(rawValue); // 텍스트 클리닝 처리

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
                                Date openDate = dateFormat.parse(value); // 클린된 날짜 문자열을 파싱
                                resultDto.setHostOpenDate(openDate);
                            } catch (ParseException e) {
                                System.err.println("Invalid date format for openDate: " + value);
                            }
                            break;
                        case "bisType":
                            resultDto.setHostBisType(value);
                            break;
                        case "companyName":
                            resultDto.setHostCompanyName(value);
                            break;
                        case "taxType":
                            resultDto.setHostTaxType(value);
                            break;
                        case "bisItem":
                            resultDto.setHostBisItem(value);
                            break;
                        default:
                            System.out.println("Unrecognized field: " + name);
                            break;
                    }
                }
            });

            return resultDto;

        } catch (Exception e) {
            System.err.println("parseResponse 실패: " + e.getMessage());
            throw new RuntimeException("JSON 파싱 중 오류 발생: " + e.getMessage(), e);
        }    }


    private void saveToDatabase(OcrResultDto resultDto) {
        HostInfo hostInfo = new HostInfo();

        hostInfo.setHostRegistNum(resultDto.getHostRegistNum());
        hostInfo.setHostCorpName(resultDto.getHostCorpName());
        hostInfo.setHostBisAddress(resultDto.getHostBisAddress());
        hostInfo.setHostBisType(resultDto.getHostBisType());
        hostInfo.setHostOpenDate(resultDto.getHostOpenDate());
        hostInfo.setHostTaxType(resultDto.getHostTaxType());

        ocrhostRepository.save(hostInfo);
    }
}
