package org.vegetto.demo;

import java.util.ArrayList;
import java.util.List;

public class Test2 { 
	
	public final static int ERA   = 0;
	public final static int YEAR = 1;
	public final static int ERA_MASK		= (1 << ERA);
	public final static int YEAR_MASK	= (1 << YEAR);
	public transient final static String sa="10";
	
//	public transient final static int sa=10;         
//	public transient final static int sa=10;
    
	
    public static void main(String[] args) {
//		System.out.println(ERA_MASK);
//		System.out.println(YEAR_MASK);
//		System.out.println(sa);
		List<String> list=new ArrayList<String>();
		String s = "1234567890";
//		list.add(sa+"");
		list.add(sa);
		list.add(s);
		System.out.println(list);
	}
	
}
