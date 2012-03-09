package org.tsinghua.omedia.dao;

import java.util.List;

import org.tsinghua.omedia.exception.DbException;
import org.tsinghua.omedia.model.CcnFile;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface CcnDao {
    public List<CcnFile> listAllCcnFiles() throws DbException;
    public List<CcnFile> listPrivateCcnFiles(long accountId) throws DbException;
    public List<CcnFile> listAllCcnFiles(long accountId) throws DbException;
    public List<CcnFile> listCcnFiles(int type) throws DbException;
    public void saveCcnFile(CcnFile ccnFile) throws DbException;
    public void deleteCcnFile(long accountId, String ccnName) throws DbException;
}
