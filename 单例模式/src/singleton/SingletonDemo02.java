package cn.zyw.singleton;
/**
 * 懒汉式单例模式
 * @author msi-pc
 *
 */
public class SingletonDemo02 {
	//初始化时，不初始化对象，延迟加载对象
	private static SingletonDemo02 instance;
	
	private SingletonDemo02() {
		
	}
	
	//方法同步，调用效率低
	public static synchronized SingletonDemo02 getInstance(){
		if(instance == null) {
			instance = new SingletonDemo02();
		}
		return instance;
	}
}
