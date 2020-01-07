package cn.zyw.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 *	 懒汉式单例模式
 * 	如何防止反射和反序列化漏洞
 * @author msi-pc
 *
 */
public class SingletonDemo06 implements Serializable{
	//初始化时，不初始化对象，延迟加载对象
	private static SingletonDemo06 instance;
	
	private SingletonDemo06() {
		if(instance != null) {
			throw new RuntimeException();
		}
	}
	
	//方法同步，调用效率低
	public static synchronized SingletonDemo06 getInstance(){
		if(instance == null) {
			instance = new SingletonDemo06();
		}
		return instance;
	}
	
	//反序列化时，如果定义了readResolve()，则直接返回此方法指定的对象/
	private Object readResolve() throws ObjectStreamException{
		return instance;
	}
}
