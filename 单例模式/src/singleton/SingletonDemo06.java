package cn.zyw.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 *	 ����ʽ����ģʽ
 * 	��η�ֹ����ͷ����л�©��
 * @author msi-pc
 *
 */
public class SingletonDemo06 implements Serializable{
	//��ʼ��ʱ������ʼ�������ӳټ��ض���
	private static SingletonDemo06 instance;
	
	private SingletonDemo06() {
		if(instance != null) {
			throw new RuntimeException();
		}
	}
	
	//����ͬ��������Ч�ʵ�
	public static synchronized SingletonDemo06 getInstance(){
		if(instance == null) {
			instance = new SingletonDemo06();
		}
		return instance;
	}
	
	//�����л�ʱ�����������readResolve()����ֱ�ӷ��ش˷���ָ���Ķ���/
	private Object readResolve() throws ObjectStreamException{
		return instance;
	}
}
