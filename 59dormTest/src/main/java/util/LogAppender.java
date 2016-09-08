package util;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * 重写isAsSevereAsThreshold方法，只输出对应级别的日志
 * @author wangk
 *
 */
public class LogAppender extends DailyRollingFileAppender {
	
	@Override
	public boolean isAsSevereAsThreshold(Priority priority){
		//只判断是否相等，不判断优先级
		return this.getThreshold().equals(priority);
	}
	
}
