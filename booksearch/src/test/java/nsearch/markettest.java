package nsearch;

import org.junit.Before;
import org.junit.Test;

public class markettest {
	private ncrawler  ncrawler;
	
	@Before
	public void setUp() throws Exception {
		this.ncrawler = new ncrawler();
	}
	
	@Test
	public void markettest() throws Exception {
		String result = this.ncrawler.market();
		//assertNotNull(result);
		System.out.println(result);
	}
	
}
