# 建造者模式
## 1. 介绍
> 建造者模式将客户端和包含各个组成部分的复杂对象的创建过程分离，客户端无须知道复杂对象的内部组成部分与装配方式，只需要知道所需建造者的类型即可。  

## 2. 适用场景
> 1. 需要生成的产品对象有复杂的内部结构，这些产品对象通常包含多个成员属性。  
> 2. 需要生成的产品对象的属性相互依赖，需要指定其生成顺序。  
> 3. 对象的创建过程独立于创建该对象的类。在建造者模式中通过引入了指挥者类，将创建过程封装在指挥者类中，而不在建造者类和客户类中。  
> 4. 隔离复杂对象的创建和使用，并使得相同的创建过程可以创建不同的产品。  

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E5%88%9B%E5%BB%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E5%BB%BA%E9%80%A0%E8%80%85%E6%A8%A1%E5%BC%8F/%E5%BB%BA%E9%80%A0%E8%80%85%E6%A8%A1%E5%BC%8F.jpg)
> 1. Builder（抽象建造者）：它为创建一个产品Product对象的各个部件指定抽象接口，在该接口中一般声明两类方法，一类方法是buildPartX()，它们用于创建复杂对象的各个部件；另一类方法是getResult()，它们用于返回复杂对象。Builder既可以是抽象类，也可以是接口。  
> 2. ConcreteBuilder（具体建造者）：它实现了Builder接口，实现各个部件的具体构造和装配方法，定义并明确它所创建的复杂对象，也可以提供一个方法返回创建好的复杂产品对象。  
> 3. Product（产品角色）：它是被构建的复杂对象，包含多个组成部件，具体建造者创建该产品的内部表示并定义它的装配过程。  
> 4. Director（指挥者）：指挥者又称为导演类，它负责安排复杂对象的建造次序，指挥者与抽象建造者之间存在关联关系，可以在其construct()建造方法中调用建造者对象的部件构造与装配方法，完成复杂对象的建造。客户端一般只需要与指挥者进行交互，在客户端确定具体建造者的类型，并实例化具体建造者对象（也可以通过配置文件和反射机制），然后通过指挥者类的构造函数或者Setter方法将该对象传入指挥者类中。  
> 导演者角色是与客户端打交道的角色。导演者将客户端创建产品的请求划分为对各个零件的建造请求，再将这些请求委派给具体建造者角色。具体建造者角色是做具体建造工作的，但是却不为客户端所知。

## 4. 实例
    (1) 产品类Product
```Java
public class Product {
    /**
     * 产品零件
     */
    private String part1;
    private String part2;
    
    public String getPart1() {
        return part1;
    }
    public void setPart1(String part1) {
        this.part1 = part1;
    }
    public String getPart2() {
        return part2;
    }
    public void setPart2(String part2) {
        this.part2 = part2;
    }
    
    @Override
    public String toString() {
        return "Product [part1=" + part1 + ", part2=" + part2 + "]";
    }
}
```

    (2) 抽象建造者接口Builder
```Java
/**
 * 抽象建造者角色
 * 
 * 提供零件建造方法及返回结果方法
 */
public interface Builder {
    void buildPart1();
    void buildPart2();
    
    Product retrieveResult();
}
```

    (3) 具体建造者角色类ConcreteBuilder
```Java
/**
 * 具体建造者角色
 */
public class ConcreteBuilder implements Builder {
    
    private Product product = new Product();
    
    /**
     * 建造零件1
     */
    @Override
    public void buildPart1() {
        product.setPart1("编号：9999");
    }

    /**
     * 建造零件2
     */
    @Override
    public void buildPart2() {
        product.setPart2("名称：建造攻城狮");
    }

    /**
     * 返回建造后成功的产品
     * @return
     */
    @Override
    public Product retrieveResult() {
        return product;
    }

}
```

    (4) 导演者角色类
```Java
/**
 * 导演者角色
 */
public class Director {
    /**
     * 创建建造者对象
     */
    private Builder builder;
    
    /**
     * 构造函数，给定建造者对象
     * @param builder 建造者对象
     */
    public Director(Builder builder) {
        this.builder = builder;
    }
    
    /**
     * 产品构造方法，在该方法内，调用产品零件建造方法。
     */
    public void construct(){
        builder.buildPart1();
        builder.buildPart2();
    }
}
```

    (5) 客户端类Client
```Java
/*
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        //创建具体建造者对象
        Builder builder = new ConcreteBuilder();
        //创造导演者角色，给定建造者对象
        Director director = new Director(builder);
        //调用导演者角色，创建产品零件
        director.construct();
        //接收建造者角色产品建造结果
        Product product = builder.retrieveResult();
        System.out.println(product.toString());
    }
}
```

## 5. 模式优点
* 在建造者模式中，客户端不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象。  
* 每一个具体建造者都相对独立，而与其他的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象。由于指挥者类针对抽象建造者编程，增加新的具体建造者无须修改原有类库的代码，系统扩展方便，符合“开闭原则”。  
* 可以更加精细地控制产品的创建过程。将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，也更方便使用程序来控制创建过程。  

## 6. 模式缺点
* 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，例如很多组成部分都不相同，不适合使用建造者模式，因此其使用范围受到一定的限制。
* 如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大，增加系统的理解难度和运行成本。  

## 7. 模式在JDK中的应用
    1. JDK 中的 StringBuilder 是建造者模式的典型应用。