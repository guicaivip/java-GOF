# 责任链模式
## 1. 介绍
责任链模式(Chain of Responsibility)：在责任链模式里，很多对象由每一个对象对其下家的引用而连接起来形成一条链。请求在这个链上传递，直到链上的某一个对象决定处理此请求。发出这个请求的客户端并不知道链上的哪一个对象最终处理这个请求，这使得系统可以在不影响客户端的情况下动态地重新组织和分配责任。  

## 2. 适用场景
1. 有多个对象可以处理同一个请求，具体哪个对象处理该请求由运行时刻自动确定。  
2. 在不明确指定接收者的情况下，向多个对象中的一个提交一个请求。  
3. 可动态指定一组对象处理请求

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E8%A1%8C%E4%B8%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E8%B4%A3%E4%BB%BB%E9%93%BE%E6%A8%A1%E5%BC%8F/%E8%B4%A3%E4%BB%BB%E9%93%BE%E6%A8%A1%E5%BC%8F.jpg)
1. 抽象处理者(Handler)角色：定义出一个处理请求的接口。如果需要，接口可以定义 出一个方法以设定和返回对下家的引用。这个角色通常由一个Java抽象类或者Java接口实现。上图中Handler类的聚合关系给出了具体子类对下家的引用，抽象方法handleRequest()规范了子类处理请求的操作。  
2. 具体处理者(ConcreteHandler)角色：具体处理者接到请求后，可以选择将请求处理掉，或者将请求传给下家。由于具体处理者持有对下家的引用，因此，如果需要，具体处理者可以访问下家。  

## 4. 实例
抽象处理者
```java
public abstract class Handler {
    private Handler nextHandler;
    private int level;
    public Handler(int level) {
        this.level = level;
    }

    // 处理请求传递，注意final，子类不可重写
    public final void handleMessage(Demand demand) {
        if (level == demand.demandLevel()) {
            this.report(demand);
        } else {
            if (this.nextHandler != null) {
                System.out.println("事情太严重，需报告上一级");
                this.nextHandler.handleMessage(demand);
            } else {
                System.out.println("我就是boss，没有上头");
            }
        }
    }

    public void setNextHandler(Handler handler) {
        this.nextHandler = handler;
    }

    // 抽象方法，子类实现
    public abstract void report(Demand demand);
}
```

具体处理者
```java
// 技术经理
public class TechnicalManager extends Handler {
    public TechnicalManager() {
        super(1);
    }

    @Override
    public void report(Demand demand) {
        System.out.println("需求：" + demand.detail());
        System.out.println(getClass().getSimpleName() + "：小猿我挺你，这个需求不干");
    }
}

// boss
public class Boss extends Handler {
    public Boss() {
        super(2);
    }

    @Override
    public void report(Demand demand) {
        System.out.println("需求：" + demand.detail());
        System.out.println(getClass().getSimpleName() + "：你们打一架吧，打赢的做决定");
    }
}
```

客户端
```java
public class Client {
    public static void main(String[] args) {
        Demand demandA = new DemandA(); // 请求等级低
        Demand demandB = new DemandB(); // 请求等级高

        Boss boss = new Boss();
        TechnicalManager technicalManager = new TechnicalManager();
        technicalManager.setNextHandler(boss); // 设置下一级

        technicalManager.handleMessage(demandA);
        System.out.println("============================");
        technicalManager.handleMessage(demandB);
    }
}
```

## 5. 模式优点
* 对象仅需知道该请求会被处理即可，且链中的对象不需要知道链的结构，由客户端负责链的创建，降低了系统的耦合度    
* 请求处理对象仅需维持一个指向其后继者的引用，而不需要维持它对所有的候选处理者的引用，可简化对象的相互连接    
* 在给对象分派职责时，职责链可以给我们更多的灵活性，可以在运行时对该链进行动态的增删改，改变处理一个请求的职责    
* 新增一个新的具体请求处理者时无须修改原有代码，只需要在客户端重新建链即可，符合 "开闭原则"    

## 6. 模式缺点
* 一个请求可能因职责链没有被正确配置而得不到处理  
* 对于比较长的职责链，请求的处理可能涉及到多个处理对象，系统性能将受到一定影响，且不方便调试  
* 可能因为职责链创建不当，造成循环调用，导致系统陷入死循环

## 7. 模式在JDK中的应用
1. JDK中的错误处理机制