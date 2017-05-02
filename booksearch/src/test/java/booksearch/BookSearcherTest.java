package booksearch;

import org.junit.Before;
import org.junit.Test;

public class BookSearcherTest {
	
		private BookSearcher bookSearcher;
		
		@Before
		public void setUp() throws Exception {
			this.bookSearcher = new BookSearcher();
		}

		@Test
		public void testSearchBooks() throws Exception {
			String result = this.bookSearcher.searchBooks("nosql");
			//assertNotNull(result);
			System.out.println(result);
		}
		
		@Test
		public void testSaveBooks() throws Exception {
			String result = this.bookSearcher.searchBooks("nosql");
			this.bookSearcher.saveBooks(result);
		}
}
