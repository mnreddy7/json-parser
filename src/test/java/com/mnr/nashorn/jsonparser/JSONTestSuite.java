package com.mnr.nashorn.jsonparser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.mnr.nashorn.bean.Data;

public class JSONTestSuite {
	/**
	 * 
	 * @author Naveen Reddy
	 * 
	 *  1. Do not use any 3rd party tools/libraries for this program, and use your own logic
		2. Use Java/Python for development
		3. The program should take two JSON files as inputs
		4. The JSONs can have nested structures (as per standard JSON format)
		5. Program should iterate over every {key, value} pair of first file and compare the same with the second file
		6. Print the differences as
		a. Missing keys
		b. Different values in keys
		c. New keys
		7. The program should have a provision to exclude certain keys from comparison
		a. This is useful when there is a timestamp or index field that can often change when a JSON is created with same fields
		b. What keys to be excluded can be inputted via a config file or at command line
		c. Example config file:
		“exclude_keys “ : [ “request.uuid” ]
		“mask_keys” : { 
			“request. originatorType” : “testxxxx”
		 }
		8. There should be a way to mask certain part of the value of a given key
		a. Example, “95xxx” should match 95034
	 *
	 */
	
	public interface Header{
		String uuid();
		String ts();
		String workspace();
		String originatorType();
	}
	
	public interface Request{
		Header header(); 
		 
	}
	
	public interface Data1 {
		String workspace();
        Request request();
    }
	
	@Test
    public void parseTest_data1() throws IOException {
		String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/data1.json")));
		System.out.println(json);
		Data1 data1 = Json.parse(json, Data1.class);
		
		System.out.println("workspace = "+data1.workspace());
		System.out.println("uuid = "+data1.request().header().uuid());
		System.out.println("ts = "+data1.request().header().ts());
		System.out.println("workspace = "+data1.request().header().workspace());
		System.out.println("originatorType = "+data1.request().header().originatorType());
        assertEquals("abc", data1.workspace());
    }
	
	@Test
    public void parseTest_data2() throws IOException {
		String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/data2.json")));
		System.out.println(json);
		Data1 data1 = Json.parse(json, Data1.class);
		
		System.out.println("workspace = "+data1.workspace());
		System.out.println("uuid = "+data1.request().header().uuid());
		System.out.println("ts = "+data1.request().header().ts());
		System.out.println("workspace = "+data1.request().header().workspace());
		System.out.println("originatorType = "+data1.request().header().originatorType());
        assertEquals("a123", data1.workspace());
    }
	
	@Test(expected=UnsupportedOperationException.class )
    public void parseTest_data3() throws IOException {
		String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/data3.json")));
		System.out.println(json);
		Data1 data1 = Json.parse(json, Data1.class);
		
		System.out.println("uuid = "+data1.request().header().uuid());
		System.out.println("ts = "+data1.request().header().ts());
		System.out.println("originatorType = "+data1.request().header().originatorType());
		System.out.println("workspace = "+data1.request().header().workspace());
    }
	
	@Test
    public void parseTest_data2WithBean() throws IOException {
		String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/data2.json")));
		System.out.println(json);
		Data data = Json.parse(json, Data.class);
		
		System.out.println("workspace = "+data.getWorkspace());
		System.out.println("uuid = "+data.getRequest().getHeader().getUuid());
		System.out.println("ts = "+data.getRequest().getHeader().getTs());
		System.out.println("workspace = "+data.getRequest().getHeader().getWorkspace());
		System.out.println("originatorType = "+data.getRequest().getHeader().getOriginatoryType());
        assertEquals("a123", data.getWorkspace());
    }

}
