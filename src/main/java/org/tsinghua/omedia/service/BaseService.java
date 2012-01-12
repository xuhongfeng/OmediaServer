package org.tsinghua.omedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.tsinghua.omedia.utils.AccountUtils;
import org.tsinghua.omedia.utils.CcnUtils;
import org.tsinghua.omedia.utils.MD5Utils;

public class BaseService {
    @Autowired
    protected CcnUtils ccnUtils;
    @Autowired
    protected MD5Utils md5Utils;
    @Autowired
    protected AccountUtils accountUtils;
}
