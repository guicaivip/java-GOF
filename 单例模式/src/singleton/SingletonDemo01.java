package cn.zyw.singleton;

/**
 * ���Զ���ʽ����ģʽ
 * @author msi-pc
 *
 */
public class SingletonDemo01 {
	
	//���ʼ��ʱ,���������������!������ʱ�������Ǿ�̬����Ȼ�̰߳�ȫ��
	private static SingletonDemo01 instance = new SingletonDemo01(); 
	
	private SingletonDemo01() {
		
	}
	
	//����û��ͬ��������Ч�ʸ�
	public static SingletonDemo01 getInstance() {
		return instance;
	}
	
}
