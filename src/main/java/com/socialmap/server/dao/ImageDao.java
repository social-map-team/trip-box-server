package com.socialmap.server.dao;

import com.socialmap.server.model.Image;

/**
 * Created by yy on 2/25/15.
 */
public interface ImageDao {
    public Image findImageByName(String name);
    public Image findImageById(long id);
}
