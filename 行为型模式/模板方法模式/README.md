# 模板方法模式
## 1. 介绍
模板方法模式（Template method）：定义抽象类并且声明一些抽象基本方法供子类实现不同逻辑，同时在抽象类中定义具体方法把抽象基本方法封装起来，这就是模板方法模式。  

## 2. 适用场景
1. 一次性实现一个算法的不变的部分，并将可变的行为留给子类来实现；  
2. 各子类中公共的行为应被提取出来并集中到一个公共父类中以避免代码重复；  
3. 控制子类的扩展。  

## 3. 角色分配
![]()
1. 抽象模板(Abstract Template)角色：定义一组基本方法供子类实现，定义并实现组合了基本方法的模板方法。  
2. 具体模板(Concrete Template)角色：实现抽象模板角色定义的基本方法。  

## 4. 实例
抽象模板结构
```java
public  abstract class Abstract Class {  
//模板方法，用来控制炒菜的流程 （炒菜的流程是一样的-复用）
//申明为final，不希望子类覆盖这个方法，防止更改流程的执行顺序 
        final void cookProcess(){  
        //第一步：倒油
        this.pourOil()；
        //第二步：热油
         this.HeatOil();
        //第三步：倒蔬菜
         this.pourVegetable();
        //第四步：倒调味料
         this.pourSauce（）；
        //第五步：翻炒
         this.fry();
    }  

//定义结构里哪些方法是所有过程都是一样的可复用的，哪些是需要子类进行实现的

//第一步：倒油是一样的，所以直接实现
void pourOil(){  
        System.out.println("倒油");  
    }  

//第二步：热油是一样的，所以直接实现
    void  HeatOil(){  
        System.out.println("热油");  
    }  

//第三步：倒蔬菜是不一样的（一个下包菜，一个是下菜心）
//所以声明为抽象方法，具体由子类实现 
    abstract void  pourVegetable()；

//第四步：倒调味料是不一样的（一个下辣椒，一个是下蒜蓉）
//所以声明为抽象方法，具体由子类实现 
    abstract void  pourSauce（）；


//第五步：翻炒是一样的，所以直接实现
    void fry();{  
        System.out.println("炒啊炒啊炒到熟啊");  
    }  
}
```

具体模板
```java
//炒手撕包菜的类
  public class ConcreteClass_BaoCai extend  Abstract Class{
    @Override
    public void  pourVegetable(){  
        System.out.println(”下锅的蔬菜是包菜“);  
    }  
    @Override
    public void  pourSauce（）{  
        System.out.println(”下锅的酱料是辣椒“);  
    }  
}
//炒蒜蓉菜心的类
  public class ConcreteClass_CaiXin extend  Abstract Class{
    @Override
    public void  pourVegetable(){  
        System.out.println(”下锅的蔬菜是菜心“);  
    }  
    @Override
    public void  pourSauce（）{  
        System.out.println(”下锅的酱料是蒜蓉“);  
    }  
}
```

客户端
```java
public class Client{
  public static void main(String[] args){

//炒 - 手撕包菜
    ConcreteClass_BaoCai BaoCai = new ConcreteClass_BaoCai（）；
    BaoCai.cookProcess()；

//炒 - 蒜蓉菜心
  ConcreteClass_ CaiXin = new ConcreteClass_CaiXin（）；
    CaiXin.cookProcess()；
    }
        
}
```

## 5. 模式优点
* 提高代码复用性，将相同部分的代码放在抽象的父类中。  
* 提高了拓展性，将不同的代码放入不同的子类中，通过对子类的扩展增加新的行为。  
* 实现了反向控制， 通过一个父类调用其子类的操作，通过对子类的扩展增加新的行为，实现了反向控制 & 符合“开闭原则”。  

## 6. 模式缺点
* 引入了抽象类，每一个不同的实现都需要一个子类来实现，导致类的个数增加，从而增加了系统实现的复杂度。

## 7. 模式在JDK中的应用
1. java.util.AbstractList, java.util.AbstractSet和java.util.AbstractMap的所有非抽象方法