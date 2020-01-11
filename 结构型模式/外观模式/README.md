# 外观模式
## 1. 介绍
外观模式(Facade Pattern)：外观模式是一种使用频率非常高的结构型设计模式，它通过引入一个外观角色来简化客户端与子系统之间的交互，为复杂的子系统调用提供一个统一的入口，降低子系统与客户端的耦合度，且客户端调用非常方便。  

## 2. 适用场景
1. 当要为一个复杂子系统提供一个简单接口时可以使用外观模式。该接口可以满足大多数用户的需求，而且用户也可以越过外观类直接访问子系统。  
2. 客户程序与多个子系统之间存在很大的依赖性。引入外观类将子系统与客户以及其他子系统解耦，可以提高子系统的独立性和可移植性。  
3. 在层次化结构中，可以使用外观模式定义系统中每一层的入口，层与层之间不直接产生联系，而通过外观类建立联系，降低层之间的耦合度。  

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E7%BB%93%E6%9E%84%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E5%A4%96%E8%A7%82%E6%A8%A1%E5%BC%8F/%E5%A4%96%E8%A7%82%E6%A8%A1%E5%BC%8F.png)
1. Facade（外观角色）：在客户端可以调用它的方法，在外观角色中可以知道相关的（一个或者多个）子系统的功能和责任；在正常情况下，它将所有从客户端发来的请求委派到相应的子系统去，传递给相应的子系统对象处理。  
2. SubSystem（子系统角色）：在软件系统中可以有一个或者多个子系统角色，每一个子系统可以不是一个单独的类，而是一个类的集合，它实现子系统的功能；每一个子系统都可以被客户端直接调用，或者被外观角色调用，它处理由外观类传过来的请求；子系统并不知道外观的存在，对于子系统而言，外观角色仅仅是另外一个客户端而已。  

## 4. 实例
电灯类  
```java
public class Light {
    public void open(){
        System.out.println("Light has been opened!");
    }
}
```

热水器类  
```java
public class Heater {
    public void open(){
        System.out.println("Heater has been opened!");
    }
}
```

电视机类  
```java
public class TV {

    public void open(){
        System.out.println("TV has been opened!");
    }
}
```

没用使用外观模式时，需要一个个启动类的open方法：  
```java
public class Main {

    public static void main(String[] args){
        Light light1 = new Light();
        Light light2 = new Light();
        Light light3 = new Light();
        Heater heater = new Heater();
        TV tv = new TV();

        /**
         * 需要一步一步的操作
         */
        light1.open();
        light2.open();
        light3.open();
        heater.open();
        tv.open();
    }
}
```

使用外观模式后:
```java
public class Facade {

    private Light light1, light2, light3;
    private Heater heater;
    private TV tv;

    public Facade() {
        light1 = new Light();
        light2 = new Light();
        light3 = new Light();
        heater = new Heater();
        tv = new TV();
    }

    public void open() {
        light1.open();
        light2.open();
        light3.open();
        heater.open();
        tv.open();
    }
}
```

客户端类：
```java
public class Client {

    public static void main(String[] args) {
        Facade facade = new Facade();
        /**
         * 一步操作就可以完成所有的准备工作
         */
        facade.open();
    }

}
```

## 5. 模式优点
* 对客户屏蔽子系统组件，减少了客户处理的对象数目并使得子系统使用起来更加容易。通过引入门面模式，客户代码将变得很简单，与之关联的对象也很少。  
* 实现了子系统与客户之间的松耦合关系，这使得子系统的组件变化不会影响到调用它的客户类，只需要调整外观类即可。  
* 降低了大型软件系统中的编译依赖性，并简化了系统在不同平台之间的移植过程，因为编译一个子系统一般不需要编译所有其他的子系统。一个子系统的修改对其他子系统没有任何影响，而且子系统内部变化也不会影响到外观对象。  
* 只是提供了一个访问子系统的统一入口，并不影响用户直接使用子系统类。  

## 6. 模式缺点
* 不能很好地限制客户使用子系统类，如果对客户访问子系统类做太多的限制则减少了可变性和灵活性。  
* 在不引入抽象外观类的情况下，增加新的子系统可能需要修改外观类或客户端的源代码，违背了“开闭原则”。  

## 7. 模式在JDK中的应用
1. java.lang.Class