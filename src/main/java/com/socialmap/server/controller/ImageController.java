package com.socialmap.server.controller;

import com.socialmap.server.dao.ImageDao;
import com.socialmap.server.model.Image;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.socialmap.server.utils.ApiUrls.Image.FETCH;
import static com.socialmap.server.utils.ApiUrls.Image.ROOT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by yy on 2/25/15.
 */
@Controller
@RequestMapping(value = ROOT)
public class ImageController {

    @Autowired
    ImageDao imageDao;

    @RequestMapping(value = FETCH, method = GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] fetch(@PathVariable Long id) throws IOException{
        Image image = imageDao.findImageById(id);
        if(image == null) return null;
        return image.getData();
    }
}
