package cn.zyw.singleton;

/**
 * ���Ծ�̬�ڲ���ʵ�ֵ���ģʽ
 * �̰߳�ȫ������Ч�ʸߣ�����ʵ������ʱ����
 * @author msi-pc
 *
 */
public class SingletonDemo03 {
	
	private static class SingletonClassInstance {
		private static final SingletonDemo03 instance = new SingletonDemo03();
	}
	
	private SingletonDemo03() {
		
	}
	
	//����û��ͬ��������Ч�ʸ�
	public static SingletonDemo03 getInstance() {
		return SingletonClassInstance.instance;
	}
	
}
