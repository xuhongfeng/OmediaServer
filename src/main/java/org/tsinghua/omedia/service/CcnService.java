package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.List;

import org.tsinghua.omedia.model.CcnFile;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface CcnService {
    public void saveCcnFile(CcnFile ccnFile) throws IOException;
    
    public List<CcnFile> listCcnFiles(long accountId) throws IOException;
}
