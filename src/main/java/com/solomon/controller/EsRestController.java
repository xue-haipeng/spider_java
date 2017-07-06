package com.solomon.controller;

import com.solomon.repository.elasticsearch.FormDataRepo;
import com.solomon.vo.FormData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by xuehaipeng on 2017/7/6.
 */
@RestController
@RequestMapping("/es")
public class EsRestController {

    @Autowired
    FormDataRepo formDataRepo;

    @GetMapping("/insertFormData")
    public String insertFormData(@Valid FormData formData) {
        FormData result = formDataRepo.save(formData);
        return result != null && !StringUtils.isEmpty(result.getUrl()) ? "OK" : "Failed";
    }

    @GetMapping("/deleteFormData")
    public void deleteFormData(@RequestParam String id) {
        formDataRepo.delete(id);
    }

    @GetMapping("/queryFormData")
    public List<FormData> queryFormData(@RequestParam String url) {
        return formDataRepo.findByUrl(url);
    }

}
