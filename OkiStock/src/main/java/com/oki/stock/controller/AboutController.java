package com.oki.stock.controller;

import com.oki.stock.common.RespResult;
import com.oki.stock.dto.QuestionDTO;
import com.oki.stock.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping("/about")
@Slf4j
public class AboutController {

    @Value("classpath:static/question.json")
    private Resource questionRes;

    @GetMapping("/main")
    public RespResult getAboutInfo() {
        String jsonQuestion;
        try {
            jsonQuestion = new String(IOUtils.readFully(questionRes.getInputStream(), -1, true), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("get question json error:{}", e);
            return RespResult.byError();
        }
        List<QuestionDTO> questionList = Utils.json2List(jsonQuestion, QuestionDTO.class);
        if (questionList != null) {
            return RespResult.bySuccess(questionList);
        } else {
            return RespResult.byError();
        }

    }

    @PostMapping(value = "/advice")
    public RespResult newAdvice(@RequestBody String content) {
        RandomAccessFile randomFile = null;
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File adviceFile = new File(path.getAbsolutePath(), "static/advice.txt");

            if (!adviceFile.exists()) {
                if (!adviceFile.createNewFile()) {
                    log.warn("create advice file error");
                    return RespResult.byError();
                }
            }
            randomFile = new RandomAccessFile(adviceFile.getAbsolutePath(), "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(content.trim() + "\n\n");

            return RespResult.bySuccess();
        } catch (IOException e) {
            log.error("save advice error:{}", e);
            return RespResult.byError();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    log.error(e.toString());
                }
            }
        }
    }
}
