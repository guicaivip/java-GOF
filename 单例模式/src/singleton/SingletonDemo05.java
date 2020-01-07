package cn.zyw.singleton;

/**
 * ���Զ���ʽ����ģʽ
 * @author msi-pc
 *
 */
public class SingletonDemo05 {
	
	//���ʼ��ʱ,���������������!������ʱ�������Ǿ�̬����Ȼ�̰߳�ȫ��
	private static SingletonDemo05 instance = new SingletonDemo05(); 
	
	private SingletonDemo05() {
		
	}
	
	//����û��ͬ��������Ч�ʸ�
	public static SingletonDemo05 getInstance() {
		return instance;
	}
	
}
