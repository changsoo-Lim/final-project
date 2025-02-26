package kr.or.ddit.json;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class JsonService {
    public String parseXmlToJson(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        // XML 데이터를 JSON으로 변환
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(file);

        // JSON 데이터를 Map으로 변환
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(jsonNode);
    }

}
