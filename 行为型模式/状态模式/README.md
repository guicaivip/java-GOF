# 状态模式
## 1. 介绍
状态模式（State Pattern）: 允许一个对象在其内部状态改变时改变它的行为，对象看起来似乎修改了它的类。  

## 2. 适用场景
1. 对象的行为依赖于它的某些属性值，状态的改变将导致行为的变化。  
2. 在代码中包含大量与对象状态有关的条件语句，这些条件语句的出现，会导致代码的可维护性和灵活性变差，不能方便地增加和删除状态，并且导致客户类与类库之间的耦合增强。  

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E8%A1%8C%E4%B8%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E7%8A%B6%E6%80%81%E6%A8%A1%E5%BC%8F/%E7%8A%B6%E6%80%81%E6%A8%A1%E5%BC%8F.png)
1. 环境(Context)角色：定义客户端所感兴趣的接口，并且保留一个具体状态类的实例。这个具体状态类的实例给出此环境对象的现有状态。  
2. 抽象状态(State)角色：定义一个接口，用以封装环境（Context）对象的一个特定的状态所对应的行为。  
3. 具体状态(ConcreteState)角色：每一个具体状态类都实现了环境（Context）的一个状态所对应的行为。  

## 4. 实例
抽象状态类
```java
interface State {
    void handleState(String str);
}
```

具体状态类
```java
class ConcreteStateWating implements State {
    @Override
    public void handleState(String str) {
        System.out.println("State: wating, str="+str);
    }
}

class ConcreteStateLoading implements State {
    @Override
    public void handleState(String str) {
        System.out.println("State: loading, str="+str);
    }
}

class ConcreteStateFinish implements State {
    @Override
    public void handleState(String str) {
        System.out.println("State: finish, str="+str);
    }
}

class ConcreteStateError implements State {
    @Override
    public void handleState(String str) {
        System.out.println("State: error, str="+str);
    }
}
```

环境类
```java
class Context {
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public void request(String str) {
        state.handleState(str);
    }
}
```

客户端
```java
public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(new ConcreteStateWating());
        context.request("1");
        context.setState(new ConcreteStateLoading());
        context.request("2");
        context.setState(new ConcreteStateFinish());
        context.request("3");
        context.setState(new ConcreteStateError());
        context.request("e");
    }
}
```

## 5. 模式优点
* 封装了状态的转换规则，在状态模式中可以将状态的转换代码封装在环境类或者具体状态类中，可以对状态转换代码进行集中管理，而不是分散在一个个业务方法中。  
* 将所有与某个状态有关的行为放到一个类中，只需要注入一个不同的状态对象即可使环境对象拥有不同的行为。  
* 允许状态转换逻辑与状态对象合成一体，而不是提供一个巨大的条件语句块，状态模式可以让我们避免使用庞大的条件语句来将业务方法和状态转换代码交织在一起。  
* 可以让多个环境对象共享一个状态对象，从而减少系统中对象的个数。  

## 6. 模式缺点
* 状态模式的使用必然会增加系统中类和对象的个数，导致系统运行开销增大。  
* 状态模式的结构与实现都较为复杂，如果使用不当将导致程序结构和代码的混乱，增加系统设计的难度。  
* 状态模式对“开闭原则”的支持并不太好，增加新的状态类需要修改那些负责状态转换的源代码，否则无法转换到新增状态；而且修改某个状态类的行为也需修改对应类的源代码。  

## 7. 模式在JDK中的应用
1. javax.faces.lifecycle.LifeCycle#execute()