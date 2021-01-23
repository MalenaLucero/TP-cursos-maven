package base.util;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {
	@Test
    public void getMarchString() {
		Assert.assertEquals("Marzo", DateUtil.getMonthString(3));
	}
	
	@Test
    public void getAgustString() {
		Assert.assertEquals("Agosto", DateUtil.getMonthString(8));
	}
}
