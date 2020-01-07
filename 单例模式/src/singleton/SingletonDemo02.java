package cn.zyw.singleton;
/**
 * ����ʽ����ģʽ
 * @author msi-pc
 *
 */
public class SingletonDemo02 {
	//��ʼ��ʱ������ʼ�������ӳټ��ض���
	private static SingletonDemo02 instance;
	
	private SingletonDemo02() {
		
	}
	
	//����ͬ��������Ч�ʵ�
	public static synchronized SingletonDemo02 getInstance(){
		if(instance == null) {
			instance = new SingletonDemo02();
		}
		return instance;
	}
}
