# 装饰模式
## 1. 介绍
装饰模式(Decorator Pattern) ：动态地给一个对象增加一些额外的职责(Responsibility)，就增加对象功能来说，装饰模式比生成子类实现更为灵活。  
装饰模式又名包装(Wrapper)模式。装饰模式以对客户端透明的方式扩展对象的功能，是继承关系的一个替代方案。  

## 2. 适用场景
1. 在不影响其他对象的情况下，以动态、透明的方式给单个对象添加职责。  
2. 需要动态地给一个对象增加功能，这些功能也可以动态地被撤销。  
3. 当不能采用继承的方式对系统进行扩充或者采用继承不利于系统扩展和维护时。不能采用继承的情况主要有两类：第一类是系统中存在大量独立的扩展，为支持每一种组合将产生大量的子类，使得子类数目呈爆炸性增长；第二类是因为类定义不能继承（如final类）。  

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E7%BB%93%E6%9E%84%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E8%A3%85%E9%A5%B0%E6%A8%A1%E5%BC%8F/%E8%A3%85%E9%A5%B0%E5%99%A8%E6%A8%A1%E5%BC%8F.jpg)
1. 抽象组件角色(Component)：定义一个对象接口，以规范准备接受附加责任的对象，即可以给这些对象动态地添加职责。  
2. 具体组件角色(ConcreteComponent) :被装饰者，定义一个将要被装饰增加功能的类。可以给这个类的对象添加一些职责。  
3. 抽象装饰器(Decorator):维持一个指向构件Component对象的实例，并定义一个与抽象组件角色Component接口一致的接口。  
4. 具体装饰器角色（ConcreteDecorator):向组件添加职责。  

## 4. 实例
Beverage饮料接口
```java
public interface Beverage {
	//返回商品描述
	public String getDescription();
	//返回价格
	public double getPrice();
}
```

CoffeeBean1——具体被装饰的对象类1
```java
public class CoffeeBean1 implements Beverage {
	private String description = "选了第一种咖啡豆";
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public double getPrice() {
		return 50;
	}
 
}
```

CoffeeBean2——具体被装饰的对象类2
```java
public class CoffeeBean2 implements Beverage {
	private String description = "第二种咖啡豆！";
	@Override
	public String getDescription() {
		return description;
	}
 
	@Override
	public double getPrice() {
		return 100;
	}
 
}
```

Decorator——装饰类
```java
public class Decorator implements Beverage {
	private String description = "我只是装饰器，不知道具体的描述";
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public double getPrice() {
		return 0;		//价格由子类来决定
	}
 
}
```

Milk——具体装饰类，给咖啡加入牛奶
```java
public class Milk extends Decorator{
	private String description = "加了牛奶！";
	private Beverage beverage = null;
	public Milk(Beverage beverage){
		this.beverage = beverage;
	}
	public String getDescription(){
		return beverage.getDescription()+"\n"+description;
	}
	public double getPrice(){
		return beverage.getPrice()+20;	//20表示牛奶的价格
	}
}
```

Mocha——给咖啡加入摩卡
```java
public class Mocha extends Decorator {
	private String description = "加了摩卡！";
	private Beverage beverage = null;
	public Mocha(Beverage beverage){
		this.beverage = beverage;
	}
	public String getDescription(){
		return beverage.getDescription()+"\n"+description;
	}
	public double getPrice(){
		return beverage.getPrice()+49;	//30表示摩卡的价格
	}
}
```

Soy——给咖啡加入豆浆
```java
public class Soy extends Decorator {
	private String description = "加了豆浆！";
	private Beverage beverage = null;
	public Soy(Beverage beverage){
		this.beverage = beverage;
	}
	public String getDescription(){
		return beverage.getDescription()+"\n"+description;
	}
	public double getPrice(){
		return beverage.getPrice()+30;	//30表示豆浆的价格
	}
}
```

客户端类
```java
public class Client {
 
	public static void main(String[] args) {
		Beverage beverage = new CoffeeBean1();	//选择了第一种咖啡豆磨制的咖啡
		beverage = new Mocha(beverage);		//为咖啡加了摩卡
		beverage = new Milk(beverage);
		System.out.println(beverage.getDescription()+"\n加了摩卡和牛奶的咖啡价格："+beverage.getPrice());
		
	}
}
```

## 5. 模式优点
* 装饰模式与继承关系的目的都是要扩展对象的功能，但是装饰模式可以提供比继承更多的灵活性。  
* 可以通过一种动态的方式来扩展一个对象的功能，通过配置文件可以在运行时选择不同的装饰器，从而实现不同的行为。  
* 通过使用不同的具体装饰类以及这些装饰类的排列组合，可以创造出很多不同行为的组合。可以使用多个具体装饰类来装饰同一对象，得到功能更为强大的对象。  
* 具体构件类与具体装饰类可以独立变化，用户可以根据需要增加新的具体构件类和具体装饰类，在使用时再对其进行组合，原有代码无须改变，符合“开闭原则”。  

## 6. 模式缺点
* 使用装饰模式进行系统设计时将产生很多小对象，这些对象的区别在于它们之间相互连接的方式有所不同，而不是它们的类或者属性值有所不同，同时还将产生很多具体装饰类。这些装饰类和小对象的产生将增加系统的复杂度，加大学习与理解的难度。  
* 这种比继承更加灵活机动的特性，也同时意味着装饰模式比继承更加易于出错，排错也很困难，对于多次装饰的对象，调试时寻找错误可能需要逐级排查，较为烦琐。  

## 7. 模式在JDK中的应用
1. InputStream 和 OutputStream中大量使用