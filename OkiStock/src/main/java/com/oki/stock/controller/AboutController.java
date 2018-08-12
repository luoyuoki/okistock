package com.oki.stock.controller;

import com.oki.stock.dto.QuestionDto;
import com.oki.stock.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/about")
public class AboutController {

    @Value("classpath:static/question.json")
    private Resource questionRes;

    @GetMapping("/main")
    public Map<String, Object> getAboutInfo() {
        Map<String, Object> modelMap = new HashMap<>();

        try {
            String json = new String(IOUtils.readFully(questionRes.getInputStream(), -1, true), StandardCharsets.UTF_8);
            List<QuestionDto> questionList = Utils.json2List(json, QuestionDto.class);
            if (questionList != null) {
                modelMap.put("success", true);
                modelMap.put("questionList", questionList);
            } else {
                modelMap.put("success", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
        }
        return modelMap;

    }

    @PostMapping(value = "/advice")
    public Map<String, Object> newAdvice(@RequestBody String content) {
        Map<String, Object> modelMap = new HashMap<>();

        RandomAccessFile randomFile = null;
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File adviceFile = new File(path.getAbsolutePath(), "static/advice.txt");

            if (!adviceFile.exists()) {
                if (!adviceFile.createNewFile()){
                    modelMap.put("success", false);
                    return modelMap;
                }
            }
            randomFile = new RandomAccessFile(adviceFile.getAbsolutePath(), "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(content + "\n\n");

            modelMap.put("success", true);
        } catch (IOException e) {
            e.printStackTrace();
            modelMap.put("success", false);
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return modelMap;
    }
}
