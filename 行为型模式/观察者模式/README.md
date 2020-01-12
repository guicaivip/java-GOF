# 观察者模式
## 1. 介绍
观察者模式（observer pattern)：观察者模式定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态发生变化时，会通知所有观察者对象，使它们能够自动更新自己。  

## 2. 适用场景
1. 当一个对象的改变需要改变其他对象的时候。

## 3. 角色分配
![]()
1. Subject：抽象主题（抽象被观察者），抽象主题角色把所有观察者对象保存在一个集合里，每个主题都可以有任意数量的观察者，抽象主题提供一个接口，可以增加和删除观察者对象。  
2. ConcreteSubject：具体主题（具体被观察者），该角色将有关状态存入具体观察者对象，在具体主题的内部状态发生改变时，给所有注册过的观察者发送通知。  
3. Observer：抽象观察者，是观察者者的抽象类，它定义了一个更新接口，使得在得到主题更改通知时更新自己。  
4. ConcrereObserver：具体观察者，实现抽象观察者定义的更新接口，以便在得到主题更改通知时更新自身的状态。  

## 4. 实例
抽象观察者
```java
public interface Observer {
    public void update(String message);
}
```

具体观察者
```java
public class WeixinUser implements Observer {
    // 微信用户名
    private String name;
    public WeixinUser(String name) {
        this.name = name;
    }
    @Override
    public void update(String message) {
        System.out.println(name + "-" + message);
    }


}
```

抽象被观察者
```java
public interface Subject {
    /**
     * 增加订阅者
     * @param observer
     */
    public void attach(Observer observer);
    /**
     * 删除订阅者
     * @param observer
     */
    public void detach(Observer observer);
    /**
     * 通知订阅者更新消息
     */
    public void notify(String message);
}
```

具体被观察者
```java
public class SubscriptionSubject implements Subject {
    //储存订阅公众号的微信用户
    private List<Observer> weixinUserlist = new ArrayList<Observer>();

    @Override
    public void attach(Observer observer) {
        weixinUserlist.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        weixinUserlist.remove(observer);
    }

    @Override
    public void notify(String message) {
        for (Observer observer : weixinUserlist) {
            observer.update(message);
        }
    }
}
```

客户端
```java
public class Client {
    public static void main(String[] args) {
        SubscriptionSubject mSubscriptionSubject=new SubscriptionSubject();
        //创建微信用户
        WeixinUser user1=new WeixinUser("AA");
        WeixinUser user2=new WeixinUser("BB");
        WeixinUser user3=new WeixinUser("VV");
        //订阅公众号
        mSubscriptionSubject.attach(user1);
        mSubscriptionSubject.attach(user2);
        mSubscriptionSubject.attach(user3);
        //公众号更新发出消息给订阅的微信用户
        mSubscriptionSubject.notify("DD");
    }
}
```

## 5. 模式优点
* 解耦，被观察者只知道观察者列表「抽象接口」，被观察者不知道具体的观察者  
* 被观察者发送通知，所有注册的观察者都会收到信息「可以实现广播机制」  

## 6. 模式缺点
* 如果观察者非常多的话，那么所有的观察者收到被观察者发送的通知会耗时  
* 观察者知道被观察者发送通知了，但是观察者不知道所观察的对象具体是如何发生变化的  
* 如果被观察者有循环依赖的话，那么被观察者发送通知会使观察者循环调用，会导致系统崩溃  

## 7. 模式在JDK中的应用
1. java.util.EventListener接口