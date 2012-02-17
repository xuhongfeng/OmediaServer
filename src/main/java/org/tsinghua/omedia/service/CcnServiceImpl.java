package org.tsinghua.omedia.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
<<<<<<< HEAD
import org.tsinghua.omedia.dao.AccountDao;
import org.tsinghua.omedia.dao.CcnDao;
=======
import org.tsinghua.omedia.controller.CcnController;
import org.tsinghua.omedia.dao.CcnDAO;
>>>>>>> MOD : ccnFileVersion放在内存
import org.tsinghua.omedia.model.CcnFile;

@Component("ccnService")
public class CcnServiceImpl extends BaseService implements CcnService {
    @Autowired
<<<<<<< HEAD
    private CcnDao ccnDao;
    @Autowired
    private AccountDao accountDao;
=======
    private CcnDAO ccnDao;
>>>>>>> MOD : ccnFileVersion放在内存

    @Override
    public void saveCcnFile(CcnFile ccnFile)
            throws IOException {
        ccnDao.saveCcnFile(ccnFile);
        CcnController.ccnFileVersion = System.currentTimeMillis();
        if(ccnFile.getType() == CcnFile.TYPE_PUBLIC) {
            CcnController.ccnFileVersion = System.currentTimeMillis();
        }
    }

    @Override
    public List<CcnFile> listCcnFiles(long accountId) throws IOException {
        return ccnDao.listAllCcnFiles(accountId);
    }

    @Override
    public void dumpCcnFileToRepo() throws IOException {
        List<CcnFile> ccnFiles = ccnDao.listAllCcnFiles();
        for(CcnFile e:ccnFiles) {
            ccnUtils.ccnPutFile(e);
        }
    }
}
