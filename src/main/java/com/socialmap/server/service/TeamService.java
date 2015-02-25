package com.socialmap.server.service;

import java.util.List;
import java.util.Map;

/**
 * Created by yy on 2/28/15.
 */
public interface TeamService {
    public Map<String, String> info(long id);
    public List search(String filter);
    public List members(String filter);
}
