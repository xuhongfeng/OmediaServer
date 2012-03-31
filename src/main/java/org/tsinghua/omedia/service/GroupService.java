package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.List;

import org.tsinghua.omedia.model.Group;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface GroupService {
    public void createGroup(long accountId, String name) throws IOException;
    public List<Group> getGroup(long accountId) throws IOException;
}
