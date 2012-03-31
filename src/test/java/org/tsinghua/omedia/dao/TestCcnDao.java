package org.tsinghua.omedia.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.CcnFile;

@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@ContextConfiguration(locations = { "classpath:web-context.xml" })
public class TestCcnDao extends AbstractTransactionalTestNGSpringContextTests {
    
    @Autowired
    private CcnDao ccnDao;
    
    private CcnFile f1 = new CcnFile(1L, new Date(1000000L), "name", "path", CcnFile.TYPE_PRIVATE);
    private CcnFile f2 = new CcnFile(2L, new Date(2000000L), "name", "path", CcnFile.TYPE_PUBLIC);
    private CcnFile f3 = new CcnFile(3L, new Date(3000000L), "name", "path", CcnFile.TYPE_PRIVATE);

    @SuppressWarnings("unused")
    @DataProvider( name="ccnFiles" )
    private Object[][] createCcnFiles() {
        return new Object[][] {
                { new CcnFile[]{f1, f2, f3} }
        };
    }

    @Test(dataProvider="ccnFiles")
    public void listCcnFiles(CcnFile[] ccnFiles) throws DbException {
//        for(CcnFile f:ccnFiles) {
//            ccnDao.saveCcnFile(f);
//        }
//        List<CcnFile> realResult = ccnDao.listAllCcnFiles(1L);
//        Assert.assertEquals(2, realResult.size());
//        Assert.assertEquals(f2, realResult.get(0));
//        Assert.assertEquals(f1, realResult.get(1));
    }
}
