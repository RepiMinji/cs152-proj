package RBTree;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Scanner;

public class DictionaryTest {
	
	String s1 = "zebra";
	String s2 = "ancylus";
	String s3 = "flattops";
	String s4 = "presubscribe";
	String s5 = "aavasaksa";
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	@Test
	public void lookup() throws Exception
	{
		RedBlackTree rbt = new RedBlackTree();
		long start = System.currentTimeMillis();
		Path path = Paths.get("./src/dictionary.txt");
		Scanner in = new Scanner(path);
		while(in.hasNextLine())
		{
			String s = in.nextLine();
			rbt.insert(s);
		}
		in.close();
		System.out.println("Time to create: " + (System.currentTimeMillis() - start));
		long totalLookup = System.currentTimeMillis();
		
		start = System.currentTimeMillis();
		assertEquals(s1, rbt.lookup("zebra").key);
		System.out.println("Lookup time: " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		assertEquals(s2, rbt.lookup("ancylus").key);
		System.out.println("Lookup time: " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		assertEquals(s3, rbt.lookup("flattops").key);
		System.out.println("Lookup time: " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		assertEquals(s4, rbt.lookup("presubscribe").key);
		System.out.println("Lookup time: " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		assertEquals(s5, rbt.lookup("aavasaksa").key);
		System.out.println("Lookup time: " + (System.currentTimeMillis() - start));
		System.out.println("Total lookup time: " + (System.currentTimeMillis() - totalLookup));
	}
	
	

}
