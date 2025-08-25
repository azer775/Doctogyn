package org.example.analyse.Services;

import feign.Headers;
import feign.RequestLine;
import org.example.analyse.Configurations.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "fastapi-service", url = "http://localhost:8000", configuration = FeignConfig.class)
public interface FastApi {
    @PostMapping(value = "/pdf/extract-blood-data-with-embedding", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Object uploadFile(@RequestPart("files") List<MultipartFile> file);
}
