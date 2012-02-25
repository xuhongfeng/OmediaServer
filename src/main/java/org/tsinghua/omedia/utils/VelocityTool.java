package org.tsinghua.omedia.utils;

import org.springframework.stereotype.Component;

/**
 * 
 * @author xuhongfeng
 *
 */
@Component("velocityTool")
public class VelocityTool {
	public String size(long fileSize) {
		long B = fileSize%1024;
		fileSize = fileSize/1024;
		long K = fileSize%1024;
		long M = fileSize/1024;
		if(M != 0) {
			return M+"M";
		} else if(K != 0) {
			return K+"K";
		} else {
			return B+"B";
		}
	}
}
