package cn.zyw.singleton;

/**
 * 测试饿汉式单例模式
 * @author msi-pc
 *
 */
public class SingletonDemo05 {
	
	//类初始化时,立即加载这个对象!加载类时，由于是静态，天然线程安全。
	private static SingletonDemo05 instance = new SingletonDemo05(); 
	
	private SingletonDemo05() {
		
	}
	
	//方法没有同步，调用效率高
	public static SingletonDemo05 getInstance() {
		return instance;
	}
	
}
