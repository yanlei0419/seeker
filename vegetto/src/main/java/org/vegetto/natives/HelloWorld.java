package org.vegetto.natives;



public class HelloWorld {
	public native void displayHelloWorld();// 
	public native String say(String content);
	public void sys(){}

	static {
		System.out.println(System.getProperty("java.library.path"));
//		Properties p=System.getProperties();
//		Set<Object> keySet = p.keySet();
//		for (Object obj:keySet) {
//			String name=String.valueOf(obj);
//			System.out.println(name+"	:	"+p.getProperty(name));
//		}
		System.loadLibrary("HImpl");// 
//		System.load("D:/Workspaces/MyEclipse 8.5/JNI/src/HImpl.dll");// 
	}
	public static void main(String[] args) {
//		System.out.println("hello word");
		HelloWorld h = new HelloWorld();
//		java.lang.UnsatisfiedLinkError: XXXX()V
//		h.sys(); 
		h.displayHelloWorld();
//		String s=;
		System.out.println(h.say("say"));
//		cl -I%java_home%\include -I%java_home%\include\win32 -LD HImpl.c -HWImpl.dll
	}

}