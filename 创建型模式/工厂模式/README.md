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

#### 模式优点
  * 工厂类含有必要的判断逻辑，可以决定在什么时候创建哪一个产品类的实例，客户端可以免除直接创建产品对象的责任，而仅仅“消费”产品；简单工厂模式通过这种做法实现了对责任的分割，它提供了专门的工厂类用于创建对象。  
  * 客户端无须知道所创建的具体产品类的类名，只需要知道具体产品类所对应的参数即可，对于一些复杂的类名，通过简单工厂模式可以减少使用者的记忆量。  
  * 通过引入配置文件，可以在不修改任何客户端代码的情况下更换和增加新的具体产品类，在一定程度上提高了系统的灵活性。  
  * 当需要引入新的产品是不需要修改客户端的代码，只需要添加相应的产品类并修改工厂类就可以了，所以说从产品的角度上简单工厂模式是符合“开-闭”原则的。

#### 模式缺点
  * 由于工厂类集中了所有产品创建逻辑，工厂类一般被我们称作“全能类”或者“上帝类”，因为所有的产品创建他都能完成，一旦不能正常工作，整个系统都要受到影响。
  * 使用简单工厂模式将会增加系统中类的个数，在一定程序上增加了系统的复杂度和理解难度。  
  * 系统扩展困难，一旦添加新产品就不得不修改工厂逻辑，在产品类型较多时，有可能造成工厂逻辑过于复杂，不利于系统的扩展和维护。所以说从工厂的角度来说简单工厂模式是不符合“开-闭”原则的。
  * 简单工厂模式由于使用了静态工厂方法，造成工厂角色无法形成基于继承的等级结构。

#### 模式在JDK中的应用
  JDK类库中广泛使用了简单工厂模式，如工具类java.text.DateFormat，它用于格式化一个本地日期或者时间。
```Java
public final static DateFormat getDateInstance();
public final static DateFormat getDateInstance(int style);
public final static DateFormat getDateInstance(int style,Locale
locale);
```

### 2. 工厂方法模式
#### 介绍
  工厂方法模式又称为工厂模式， 在工厂方法模式中，不再提供一个统一的工厂类来创建所有的对象，而是针对不同的对象提供不同的工厂。也就是说 每个对象都有一个与之对应的工厂。  

#### 适用场景
  1. 一个类不知道它所需要的对象的类：在工厂方法模式中，客户端不需要知道具体产品类的类名，只需要知道所对应的工厂即可，具体的产品对象由具体工厂类创建；客户端需要知道创建具体产品的工厂类。
  2. 一个类通过其子类来指定创建哪个对象：在工厂方法模式中，对于抽象工厂类只需要提供一个创建产品的接口，而由其子类来确定具体要创建的对象，利用面向对象的多态性和里氏代换原则，在程序运行时，子类对象将覆盖父类对象，从而使得系统更容易扩展。  
  3. 将创建对象的任务委托给多个工厂子类中的某一个，客户端在使用时可以无须关心是哪一个工厂子类创建产品子类，需要时再动态指定，可将具体工厂类的类名存储在配置文件或数据库中。

#### 角色分配
  ![图片](https://github.com/guicaivip/java-GOF/blob/master/%E5%88%9B%E5%BB%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E5%B7%A5%E5%8E%82%E6%A8%A1%E5%BC%8F/%E5%B7%A5%E5%8E%82%E6%96%B9%E6%B3%95%E6%A8%A1%E5%BC%8F.jpg)
  1. 抽象工厂(Abstract Factory)角色：是工厂方法模式的核心，与应用程序无关。任何在模式中创建的对象的工厂类必须实现这个接口。这个角色常常有Java抽象类来实现。
  2. 具体工厂(Concrete Factory)角色 ：这是实现抽象工厂接口的具体工厂类，包含与应用程序密切相关的逻辑，并且受到应用程序调用以创建某一种产品对象。
  3. 抽象产品(Abstract Product)角色 ：工厂方法模式所创建的对象的超类型，也就是产品对象的共同父类或共同拥有的接口。在实际应用中这个角色常常由Java的抽象类来实现。
  4. 具体产品(Concrete Product)角色 ：这个角色实现了抽象产品角色所定义的接口。某具体产品有专门的具体工厂创建，它们之间往往一一对应。

#### 实例
  上面简单工厂例子中的图形接口以及相关图像实现类不变。只需要增加一个工厂接口以及实现这个接口的工厂类即可。

  (1) 增加一个工厂接口
```Java
public interface Factory {
    public Shape getShape();
}
```

  (2) 增加相关工厂类
  圆形工厂类
```Java
public class CircleFactory implements Factory {

    @Override
    public Shape getShape() {
        // TODO Auto-generated method stub
        return new Circle();
    }

}
```

  长方形工厂类
```Java
public class RectangleFactory implements Factory{

    @Override
    public Shape getShape() {
        // TODO Auto-generated method stub
        return new Rectangle();
    }

}
```

  圆形工厂类
```Java
public class SquareFactory implements Factory{

    @Override
    public Shape getShape() {
        // TODO Auto-generated method stub
        return new Square();
    }

}
```

  (3) 测试
```Java
public class Test {

    public static void main(String[] args) {
        Factory circlefactory = new CircleFactory();
        Shape circle = circlefactory.getShape();
        circle.draw();
    }

}
```

#### 模式优点
  * 在工厂方法模式中，工厂方法用来创建客户所需要的产品，同时还向客户隐藏了哪种具体产品类将被实例化这一细节，用户只需要关心所需产品对应的工厂，无须关心创建细节，甚至无须知道具体产品类的类名。
  * 基于工厂角色和产品角色的多态性设计是工厂方法模式的关键。它能够使工厂可以自主确定创建何种产品对象，而如何创建这个对象的细节则完全封装在具体工厂内部。工厂方法模式之所以又被称为多态工厂模式，是因为所有的具体工厂类都具有同一抽象父类。
  * 使用工厂方法模式的另一个优点是在系统中加入新产品时，无须修改抽象工厂和抽象产品提供的接口，无须修改客户端，也无须修改其他的具体工厂和具体产品，而只要添加一个具体工厂和具体产品就可以了。这样，系统的可扩展性也就变得非常好，完全符合“开闭原则”。

#### 模式缺点
  * 在添加新产品时，需要编写新的具体产品类，而且还要提供与之对应的具体工厂类，系统中类的个数将成对增加，在一定程度上增加了系统的复杂度，有更多的类需要编译和运行，会给系统带来一些额外的开销。
  * 由于考虑到系统的可扩展性，需要引入抽象层，在客户端代码中均使用抽象层进行定义，增加了系统的抽象性和理解难度，且在实现时可能需要用到DOM、反射等技术，增加了系统的实现难度。

#### 模式在JDK中的应用
  1. Object中的toString方法：Object仅仅定义了返回String，由Object的子类来决定如何生成这个字符串。

### 3. 抽象工厂模式
#### 介绍
  在工厂方法模式中，生产的都是同一类产品。抽象工厂模式不单单可以创建一个对象，而是可以创建一组对象。  
  提供一个创建一系列相关或相互依赖对象的接口，而无须指定它们具体的类。（ 在抽象工厂模式中，每一个具体工厂都提供了多个工厂方法用于产生多种不同类型的对象）。  

#### 适用场景
  1. 和工厂方法一样客户端不需要知道它所创建的对象的类。
  2. 需要一组对象共同完成某种功能时，并且可能存在多组对象完成不同功能的情况。（同属于同一个产品族的产品）。
  3. 系统结构稳定，不会频繁的增加对象。（因为一旦增加就需要修改原有代码，不符合开闭原则）。

#### 角色分配
  ![图片](https://github.com/guicaivip/java-GOF/blob/master/%E5%88%9B%E5%BB%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E5%B7%A5%E5%8E%82%E6%A8%A1%E5%BC%8F/%E6%8A%BD%E8%B1%A1%E5%B7%A5%E5%8E%82%E6%A8%A1%E5%BC%8F.jpg)
  1. 抽象工厂（Abstract Factory）角色 ：是工厂方法模式的核心，与应用程序无关。任何在模式中创建的对象的工厂类必须实现这个接口。通常使用Java接口或者抽象Java类实现。所有的具体工厂必须实现这个Java接口或继承这个抽象的Java类。  
  2. 具体工厂类（Conrete Factory）角色 ：这是实现抽象工厂接口的具体工厂类，包含与应用程序密切相关的逻辑，并且受到应用程序调用以创建某一种产品对象。  
  3. 抽象产品（Abstract Product）角色 ：工厂方法模式所创建的对象的超类型，也就是产品对象的共同父类或共同拥有的接口。通常使用Java接口或者抽象Java类实现这一角色。  
  4. 具体产品（Concrete Product）角色 ：抽象工厂模式所创建的任何产品对象都是某一个具体产品类的实例。在抽象工厂中创建的产品属于同一产品族，这不同于工厂模式中的工厂只创建单一产品。

#### 实例
  (1) 创建相关接口
  枪
```Java
public interface Gun {
    public void shooting();
}
```

  子弹
```Java
public interface Bullet {
    public void load();
}
```

  (2) 创建接口对应实现类
  AK类
```Java
public class AK implements Gun{

    @Override
    public void shooting() {
        System.out.println("shooting with AK");
        
    }

}
```

  M4A1类
```Java
public class M4A1 implements Gun {

    @Override
    public void shooting() {
        System.out.println("shooting with M4A1");

    }

}
```

  AK子弹类
```Java
public class AK_Bullet implements Bullet {

    @Override
    public void load() {
        System.out.println("Load bullets with AK");
    }

}
```

  M4A1类
```Java
public class M4A1_Bullet implements Bullet {

    @Override
    public void load() {
        System.out.println("Load bullets with M4A1");
    }

}
```

  (3) 创建工厂接口
```Java
public interface Factory {
    public Gun produceGun();
    public Bullet produceBullet();
}
```

  (4) 创建具体工厂
  生产AK和AK子弹的工厂
```Java
public class AK_Factory implements Factory{

    @Override
    public Gun produceGun() {
        return new AK();
    }

    @Override
    public Bullet produceBullet() {
        return new AK_Bullet();
    }

}
```

  生产M4A1和M4A1子弹的工厂
```Java
public class M4A1_Factory implements Factory{

    @Override
    public Gun produceGun() {
        return new M4A1();
    }

    @Override
    public Bullet produceBullet() {
        return new M4A1_Bullet();
    }

}
```

  (5) 测试
```Java
public class Test {

    public static void main(String[] args) {  
        
     Factory factory;
     Gun gun;
     Bullet bullet;
     
     factory =new AK_Factory();
     bullet=factory.produceBullet();
     bullet.load();
     gun=factory.produceGun();
     gun.shooting(); 
     
    }

}
```

#### 模式优点
  * 抽象工厂模式隔离了具体类的生成，使得客户并不需要知道什么被创建。由于这种隔离，更换一个具体工厂就变得相对容易。所有的具体工厂都实现了抽象工厂中定义的那些公共接口，因此只需改变具体工厂的实例，就可以在某种程度上改变整个软件系统的行为。另外，应用抽象工厂模式可以实现高内聚低耦合的设计目的，因此抽象工厂模式得到了广泛的应用。当一个产品族中的多个对象被设计成一起工作时，它能够保证客户端始终只使用同一个产品族中的对象。这对一些需要根据当前环境来决定其行为的软件系统来说，是一种非常实用的设计模式。
  * 增加新的具体工厂和产品族很方便，无须修改已有系统，符合“开闭原则”。

#### 模式缺点
  * 在添加新的产品对象时，难以扩展抽象工厂来生产新种类的产品，这是因为在抽象工厂角色中规定了所有可能被创建的产品集合，要支持新种类的产品就意味着要对该接口进行扩展，而这将涉及到对抽象工厂角色及其所有子类的修改，显然会带来较大的不便。
  * 开闭原则的倾斜性（增加新的工厂和产品族容易，增加新的产品等级结构麻烦）。