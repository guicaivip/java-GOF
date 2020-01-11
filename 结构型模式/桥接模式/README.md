# 桥接模式
## 1. 介绍
桥接模式 (Bridge)：将抽象部分与它的实现部分分离，使它们都可以独立地变化。如果软件系统中某个类存在两个独立变化的维度，通过该模式可以将这两个维度分离出来，使两者可以独立扩展，让系统更加符合“单一职责原则”。与多层继承方案不同，它将两个独立变化的维度设计为两个独立的继承等级结构，并且在抽象层建立一个抽象关联，该关联关系类似一条连接两个独立继承结构的桥，故名桥接模式。  

## 2. 适用场景
* 如果一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，避免在两个层次之间建立静态的继承联系，通过桥接模式可以使它们在抽象层建立一个关联关系。  
* 抽象化角色和实现化角色可以以继承的方式独立扩展而互不影响，在程序运行时可以动态将一个抽象化子类的对象和一个实现化子类的对象进行组合，即系统需要对抽象化角色和实现化角色进行动态耦合。  
* 一个类存在两个独立变化的维度，且这两个维度都需要进行扩展。  
* 虽然在系统中继承是没有问题的，但是由于抽象化角色和具体化角色需要独立变化，设计要求需要独立管理这两者。  
* 对于那些不希望使用继承或因为多层次继承导致类的个数急剧增加的系统，桥接模式尤为适用。  

## 3. 角色分配
![]()
1. Abstraction（抽象类）：用于定义抽象类的接口，它一般是抽象类而不是接口，其中定义了一个 Implementor（实现类接口）类型的对象并可以维护该对象，它与 Implementor 之间具有关联关系，它既可以包含抽象业务方法，也可以包含具体业务方法。  
2. RefinedAbstraction（扩充抽象类）：扩充由 Abstraction 定义的接口，通常情况下它不再是抽象类而是具体类，它实现了在 Abstraction 中声明的抽象业务方法，在 RefinedAbstraction 中可以调用在 Implementor 中定义的业务方法。  
3. Implementor（实现类接口）：定义实现类的接口，这个接口不一定要与 Abstraction 的接口完全一致，事实上这两个接口可以完全不同，一般而言，Implementor 接口仅提供基本操作，而 Abstraction 定义的接口可能会做更多更复杂的操作。Implementor 接口对这些基本操作进行了声明，而具体实现交给其子类。通过关联关系，在 Abstraction 中不仅拥有自己的方法，还可以调用到 Implementor 中定义的方法，使用关联关系来替代继承关系。  
4. ConcreteImplementor（具体实现类）：具体实现 Implementor 接口，在不同的 ConcreteImplementor 中提供基本操作的不同实现，在程序运行时，ConcreteImplementor 对象将替换其父类对象，提供给抽象类具体的业务操作方法。  

## 4. 实例
实现类接口
```java
public interface Color {
    public void bepaint(String shape);
}
```

具体实现类
```java
public class White implements Color {
    
    @Override
    public void bepaint(String shape) {
        System.out.println("白色的" + shape);
    }
}
```

```java
public class Black implements Color {
    
    @Override
    public void bepaint(String shape) {
        System.out.println("黑色的" + shape);
    }
}
```

抽象类
```java
public abstract class Shape {
    protected Color color;
    
    public Shape(Color color) {
        this.color = color;
    }
    
    public abstract void draw();
}
```

扩充抽象类
```java
public class Circle extends Shape {
    
    public Circle(Color color) {
        super(color);
    }
    
    @Override
    public void draw() {
        color.bepaint("圆形");
    }
}
```

```java
public class Rectangle extends Shape {

    public Rectangle(Color color) {
        super(color);
    }
    
    @Override
    public void draw() {
        color.bepaint("长方形");
    }
}
```

```java
public class Square extends Shape {

    public Square(Color color) {
        super(color);
    }
    
    @Override
    public void draw() {
        color.bepaint("正方形");
    }
}
```

客户端调用
```java
public class Client {
    
    public static void main(String[] args) {
        Shape circle = new Circle(new White());
        circle.draw();
        
        Shape square = new Square(new Black());
        square.draw();
    }
}
```

## 5. 模式优点
* 分离抽象接口及其实现部分。  
* 桥接模式提高了系统的可扩充性，在两个变化维度中任意扩展一个维度，都不需要修改原有系统。  
* 实现细节对客户透明，可以对用户隐藏实现细节。  

## 6. 模式缺点
* 桥接模式的使用会增加系统的理解与设计维度，由于关联关系建立在抽象层，要求开发者一开始就针对抽象层进行设计与编程。  
* 桥接模式要求正确识别出系统中两个独立变化的维度，因此其使用范围具有一定的局限性，如何正确识别两个独立维度也需要一定的经验积累。  

## 7. 模式在JDK中的应用
1. JDBC中的使用