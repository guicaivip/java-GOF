package cn.zyw.singleton;

/**
 * 测试枚举类实现单例模式(没有延时加载)
 * @author msi-pc
 *
 */
public enum SingletonDemo04 {
	
	//这个枚举元素，本身就是单例对象!
	INSTANCE;
	
	//添加自己需要的操作!
	public void singletonOperation() {
		
	}
	
}
