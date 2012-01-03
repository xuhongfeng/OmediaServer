package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsinghua.omedia.dao.CcnDao;
import org.tsinghua.omedia.model.CcnFile;

@Component("ccnService")
public class CcnServiceImpl implements CcnService {
    @Autowired
    private CcnDao ccnDao;

    @Override
    public void saveCcnFile(CcnFile ccnFile)
            throws IOException {
        ccnDao.saveCcnFile(ccnFile);
    }

    @Override
    public List<CcnFile> listCcnFiles(long accountId) throws IOException {
        return ccnDao.listAllCcnFiles(accountId);
    }
}
