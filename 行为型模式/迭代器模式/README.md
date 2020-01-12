# 迭代器模式
## 1. 介绍
迭代器模式(Iterator)：迭代器模式提供一种方法顺序访问一个聚合对象中的各个元素，而又不暴露其内部的表示。把游走的任务放在迭代器上，而不是聚合上。这样简化了聚合的接口和实现，也让责任各得其所。  

## 2. 适用场景
1. 访问一个聚合对象的内容而无须暴露它的内部表示。  
2. 需要为聚合对象提供多种遍历方式。  
3. 为遍历不同的聚合结构提供一个统一的接口(支持多态迭代化)。  

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E8%A1%8C%E4%B8%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E8%BF%AD%E4%BB%A3%E5%99%A8%E6%A8%A1%E5%BC%8F/%E8%BF%AD%E4%BB%A3%E5%99%A8%E6%A8%A1%E5%BC%8F.png)
1. 迭代器角色(Iterator)：定义遍历元素所需要的方法，一般来说会有这么三个方法：取得下一个元素的方法next()，判断是否遍历结束的方法hasNext()），移出当前对象的方法remove()。  
2. 具体迭代器角色(Concrete Iterator)：实现迭代器接口中定义的方法，完成集合的迭代。  
3. 容器角色(Aggregate): 一般是一个接口，提供一个iterator()方法，例如java中的Collection接口，List接口，Set接口等。  
4. 具体容器角色(ConcreteAggregate)：就是抽象容器的具体实现类，比如List接口的有序列表实现ArrayList，List接口的链表实现LinkList，Set接口的哈希列表的实现HashSet等。  

## 4. 实例
抽象迭代器
```java
interface Iterator {
    Object first();
    Object next();
    boolean hasNext();
    Object currentItem();
}
```

具体迭代器
```java
class ConcreteIterator implements Iterator {
    private List<Object> list;
    private int cursor;

    public ConcreteIterator(List<Object> list) {
        this.list = list;
    }

    @Override
    public Object first() {
        cursor = 0;
        return list.get(cursor);
    }

    @Override
    public Object next() {
        Object ret = null;
        if (hasNext()) {
            ret = list.get(cursor);
        }
        cursor++;
        return ret;
    }

    @Override
    public boolean hasNext() {
        return !(cursor == list.size());
    }

    @Override
    public Object currentItem() {
        return list.get(cursor);
    }
}
```

容器角色
```java
interface Container {
    void add(Object obj);
    void remove(Object obj);
    Iterator createIterator();
}
```

具体容器角色
```java
class ConcreteContainer implements Container {
    private List<Object> list;

    public ConcreteContainer(List<Object> list) {
        this.list = list;
    }

    @Override
    public void add(Object obj) {
        list.add(obj);
    }

    @Override
    public void remove(Object obj) {
        list.remove(obj);
    }

    @Override
    public Iterator createIterator() {
        return new ConcreteIterator(list);
    }
}
```

客户端
```java
public class Client {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<Object>();
        list.add("Android");
        list.add("PHP");
        list.add("C Language");

        Container container = new ConcreteContainer(list);
        container.add("HardWare");

        Iterator iterator = container.createIterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
```

## 5. 模式优点
* 简化了遍历方式，对于对象集合的遍历，还是比较麻烦的，对于数组或者有序列表，我们尚可以通过游标来取得，但用户需要在对集合了解很清楚的前提下，自行遍历对象，但是对于hash表来说，用户遍历起来就比较麻烦了。而引入了迭代器方法后，用户用起来就简单的多了。  
* 可以提供多种遍历方式，比如说对有序列表，我们可以根据需要提供正序遍历，倒序遍历两种迭代器，用户用起来只需要得到我们实现好的迭代器，就可以方便的对集合进行遍历了。  
* 封装性良好，用户只需要得到迭代器就可以遍历，而对于遍历算法则不用去关心。  

## 6. 模式缺点
* 对于比较简单的遍历（数组或者有序列表），使用迭代器方式遍历较为繁琐而且遍历效率不高，使用迭代器的方式比较适合那些底层以链表形式实现的集合。

## 7. 模式在JDK中的应用
1. java.util.Iterator