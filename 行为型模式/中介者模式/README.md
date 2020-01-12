# 中介者模式
## 1. 介绍
中介者模式(Mediator Pattern)：用一个中介对象（中介者）来封装一系列的对象交互，中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。中介者模式又称为调停者模式，它是一种对象行为型模式。  

## 2. 适用场景
1. 在面向对象的编程中，对象和对象之间必然会有依赖关系。  
2. 中介者模式适用于多个对象之间紧密耦合的情况，紧密耦合的标准是：在类图中出现了蜘蛛网状结构。  

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E8%A1%8C%E4%B8%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E4%B8%AD%E4%BB%8B%E8%80%85%E6%A8%A1%E5%BC%8F/%E4%B8%AD%E4%BB%8B%E8%80%85%E6%A8%A1%E5%BC%8F.png)
1. Mediator（抽象中介者角色）：抽象中介者角色定义统一的接口，用于各同事角色之间的通信。  
2. Concrete Mediator（具体中介者角色）：具体中介者角色通过协调各同事角色实现协作行为，因此它必须依赖于各个同事角色。  
3. Colleague（同事角色）：每一个同事角色都知道中介者角色，而且与其他的同事角色通信的时候，一定要通过中介者角色协作。每个同事类的行为分为两种：一种是同事本身的行为，比如改变对象本身的状态，处理自己的行为等，这种行为叫做自发行为，与其他的同事类或中介者没有任何的依赖；第二种是必须依赖中介者才能完成的行为，叫做依赖方法。  

## 4. 实例
抽象同事类
```java
abstract class AbstractColleague {
	protected int number;
 
	public int getNumber() {
		return number;
	}
 
	public void setNumber(int number){
		this.number = number;
	}
	//注意这里的参数不再是同事类，而是一个中介者
	public abstract void setNumber(int number, AbstractMediator am);
}
```

具体同事类
```java
class ColleagueA extends AbstractColleague{
 
	public void setNumber(int number, AbstractMediator am) {
		this.number = number;
		am.AaffectB();
	}
}
 
class ColleagueB extends AbstractColleague{
 
	@Override
	public void setNumber(int number, AbstractMediator am) {
		this.number = number;
		am.BaffectA();
	}
}
```

抽象中介者类
```java
abstract class AbstractMediator {
	protected AbstractColleague A;
	protected AbstractColleague B;
	
	public AbstractMediator(AbstractColleague a, AbstractColleague b) {
		A = a;
		B = b;
	}
 
	public abstract void AaffectB();
	
	public abstract void BaffectA();
}

中介者类
```java
class Mediator extends AbstractMediator {
 
	public Mediator(AbstractColleague a, AbstractColleague b) {
		super(a, b);
	}
 
	//处理A对B的影响
	public void AaffectB() {
		int number = A.getNumber();
		B.setNumber(number*100);
	}
 
	//处理B对A的影响
	public void BaffectA() {
		int number = B.getNumber();
		A.setNumber(number/100);
	}
}
```

客户端类
```java
public class Client {
	public static void main(String[] args){
		AbstractColleague collA = new ColleagueA();
		AbstractColleague collB = new ColleagueB();
		
		AbstractMediator am = new Mediator(collA, collB);
		
		System.out.println("==========通过设置A影响B==========");
		collA.setNumber(1000, am);
		System.out.println("collA的number值为："+collA.getNumber());
		System.out.println("collB的number值为A的10倍："+collB.getNumber());
 
		System.out.println("==========通过设置B影响A==========");
		collB.setNumber(1000, am);
		System.out.println("collB的number值为："+collB.getNumber());
		System.out.println("collA的number值为B的0.1倍："+collA.getNumber());
		
	}
}
```

## 5. 模式优点
* 简化了对象之间的交互，它用中介者和同事的一对多交互代替了原来同事之间的多对多交互，一对多关系更容易理解、维护和扩展，将原本难以理解的网状结构转换成相对简单的星型结构。  
* 可将各同事对象解耦。中介者有利于各同事之间的松耦合，我们可以独立的改变和复用每一个同事和中介者，增加新的中介者和新的同事类都比较方便，更好地符合 "开闭原则"。  
* 可以减少子类生成，中介者将原本分布于多个对象间的行为集中在一起，改变这些行为只需生成新的中介者子类即可，这使各个同事类可被重用，无须对同事类进行扩展。  

## 6. 模式缺点
在具体中介者类中包含了大量同事之间的交互细节，可能会导致具体中介者类非常复杂，使得系统难以维护。（也就是把具体同事类之间的交互复杂性集中到了中介者类中，结果中介者成了最复杂的类）。  

## 7. 模式在JDK中的应用
1. java.util.Timer (所有scheduleXXX()方法)