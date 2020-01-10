# 工厂模式
## 介绍
  工厂模式（Factory Pattern）是 Java 中最常用的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。  
  在工厂模式中，我们在创建对象时不会对客户端暴露创建逻辑，而是通过使用一个共同的接口来指向新创建的对象。  
  实现了创建者和调用者的分离

## 用途
  (1) 解耦 ：把对象的创建和使用的过程分开。  
  (2) 降低代码重复 : 如果创建某个对象的过程都很复杂，需要一定的代码量，而且很多地方都要用到，那么就会有很多的重复代码。  
  (3) 降低维护成本 ：由于创建过程都由工厂统一管理，所以发生业务逻辑变化，不需要找到所有需要创建对象B的地方去逐个修正，只需要在工厂里修改即可，降低维护成本。  

## 分类
### 1. 简单工厂模式
#### 介绍
  简单工厂模式并不是23种设计模式之一，它只算工厂模式的一个特征实现。
  简单工厂模式也叫静态工厂模式，因为工厂类中一般使用静态方法，通过接收的参数的不同来返回不同的对象实例。  
  它违背了开闭原则，因为每次需要添加新功能时，都需要在switch-case语句（或者if-else）语句中修改代码，添加分支条件。  

#### 适用场景
  （1）需要创建的对象较少。  
  （2）客户端不关心对象的创建过程。  

#### 角色分配
  ![图片](https://github.com/guicaivip/java-GOF/blob/master/%E5%88%9B%E5%BB%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E5%B7%A5%E5%8E%82%E6%A8%A1%E5%BC%8F/%E7%AE%80%E5%8D%95%E5%B7%A5%E5%8E%82%E6%A8%A1%E5%BC%8F.jpg)
  1. 工厂(Factory)角色 :简单工厂模式的核心，它负责实现创建所有实例的内部逻辑。工厂类可以被外界直接调用，创建所需的产品对象，它往往由一个具体Java类实现。  
  2. 抽象产品(AbstractProduct)角色 :简单工厂模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。抽象产品角色可以用一个Java接口或者Java抽象类实现。  
  3. 具体产品(Concrete Product)角色:简单工厂模式的创建目标，所有创建的对象都是充当这个角色的某个具体类的实例。具体产品角色由一个具体Java类实现。

#### 实例
  (1) 创建Shape接口
```Java
public interface Shape {
    void draw();
}
```

  (2) 创建实现该接口的具体图形类
  圆形
```Java
public class Circle implements Shape {
    public Circle() {
        System.out.println("Circle");
    }
    @Override
    public void draw() {
        System.out.println("Draw Circle");
    }
}
```

  长方形
```Java
public class Rectangle implements Shape {
    public Rectangle() {
        System.out.println("Rectangle");
    }
    @Override
    public void draw() {
        System.out.println("Draw Rectangle");
    }
}
```

  正方形
```Java
public class Square implements Shape {
    public Square() {
        System.out.println("Square");
    }

    @Override
    public void draw() {
        System.out.println("Draw Square");
    }
}
```

  (3) 创建工厂类：
```Java
public class ShapeFactory {

    // 使用 getShape 方法获取形状类型的对象
    public static Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }
}
```

  (4) 测试方法
```Java
public class Test {

    public static void main(String[] args) {

        // 获取 Circle 的对象，并调用它的 draw 方法
        Shape circle = ShapeFactory.getShape("CIRCLE");
        circle.draw();

        // 获取 Rectangle 的对象，并调用它的 draw 方法
        Shape rectangle = ShapeFactory.getShape("RECTANGLE");
        rectangle.draw();

        // 获取 Square 的对象，并调用它的 draw 方法
        Shape square = ShapeFactory.getShape("SQUARE");
        square.draw();
    }
}
```

  输出结果
```Java
Circle
Draw Circle
Rectangle
Draw Rectangle
Square
Draw Square
```

#### 使用反射机制改善简单工厂
```Java
package factory_pattern;

/**
 * 利用反射解决简单工厂每次增加新了产品类都要修改产品工厂的弊端
 * 
 *
 */
public class ShapeFactory2 {
    public static Object getClass(Class<? extends Shape> clazz) {
        Object obj = null;

        try {
            obj = Class.forName(clazz.getName()).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
```

```Java
package factory_pattern;

public class Test2 {
    public static void main(String[] args) {

        Circle circle = (Circle) ShapeFactory2.getClass(factory_pattern.Circle.class);
        circle.draw();

        Rectangle rectangle = (Rectangle) ShapeFactory2.getClass(factory_pattern.Rectangle.class);
        rectangle.draw();

        Square square = (Square) ShapeFactory2.getClass(factory_pattern.Square.class);
        square.draw();
    }

}
```

  这种方式虽然符合了开闭原则，但是每一次传入的都是产品类的全部路径，使用比较麻烦。再次改善的方式可以通过**反射+配置文件**的形式来改善，这种方式使用较多。

### 2. 工厂方法模式
#### 介绍
  工厂方法模式又称为工厂模式， 在工厂方法模式中，不再提供一个统一的工厂类来创建所有的对象，而是针对不同的对象提供不同的工厂。也就是说 每个对象都有一个与之对应的工厂。  

#### 适用场景
  1. 一个类不知道它所需要的对象的类：在工厂方法模式中，客户端不需要知道具体产品类的类名，只需要知道所对应的工厂即可，具体的产品对象由具体工厂类创建；客户端需要知道创建具体产品的工厂类。
  2. 一个类通过其子类来指定创建哪个对象：在工厂方法模式中，对于抽象工厂类只需要提供一个创建产品的接口，而由其子类来确定具体要创建的对象，利用面向对象的多态性和里氏代换原则，在程序运行时，子类对象将覆盖父类对象，从而使得系统更容易扩展。  
  3. 将创建对象的任务委托给多个工厂子类中的某一个，客户端在使用时可以无须关心是哪一个工厂子类创建产品子类，需要时再动态指定，可将具体工厂类的类名存储在配置文件或数据库中。

#### 角色分配
