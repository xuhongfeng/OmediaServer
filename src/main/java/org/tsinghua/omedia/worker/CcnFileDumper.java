package org.tsinghua.omedia.worker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tsinghua.omedia.utils.CcnUtils;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CcnFileDumper {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("web-context.xml");
        CcnUtils ccnUtils = context.getBean("ccnUtils", CcnUtils.class);
        ccnUtils.dumpFromDb();
    }
}
