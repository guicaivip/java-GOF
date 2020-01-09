# 单例模式
## 介绍
  单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。  
  这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。  

## 优点
  节省资源和时间  
  1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例。  
  2、避免对资源的多重占用（比如写文件操作）。  

## 单例模式实现方式

||线程安全|并发性能好|可以延迟加载|序列化/反序列话安全|能够抵御反射|
|----|----|----|----|----|----|
|饿汉式|是|是|否|否|否|
|懒汉式（不加锁）|否|是|是|否|否|
|懒汉式（加锁）|是|否|是|否|否|
|双重检验锁|是|是|是|否|否|
|静态内部类|是|是|是|否|否|
|枚举式|是|是|否|是|是|

### 饿汉式
  这种方法非常简单，因为单例的实例被声明成 static 和 final 变量了，在第一次加载类到内存中时就会初始化，所以创建实例本身是线程安全的。  
  缺点是它不是一种懒加载模式（lazy initialization），单例会在加载类后一开始就被初始化，即使客户端没有调用 getInstance()方法。  
```Java
public class Singleton{
    //类加载时就初始化
    private static final Singleton instance = new Singleton();
    
    private Singleton(){}

    public static Singleton getInstance(){
        return instance;
    }
}
```

### 懒汉式
#### 线程不安全
  这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁 synchronized，所以严格意义上它并不算单例模式。  
  这种方式 lazy loading 很明显，不要求线程安全，在多线程不能正常工作。  
```Java
public class Singleton {  
    private static Singleton instance;  
    private Singleton (){}  
  
    public static Singleton getInstance() {  
    if (instance == null) {  
        instance = new Singleton();  
    }  
    return instance;  
    }  
}
```

#### 线程安全
  这种方式具备很好的 lazy loading，能够在多线程中很好的工作，但是，效率很低，很多情况下不需要同步。  
```Java
public class Singleton {  
    private static Singleton instance;  
    private Singleton (){}  
    public static synchronized Singleton getInstance() {  
    if (instance == null) {  
        instance = new Singleton();  
    }  
    return instance;  
    }  
}
```

### 双重检验锁
  由于编译器优化问题和JVM底层内部模型问题，不建议使用，不做介绍。  
```Java
public class Singleton {  
    private volatile static Singleton singleton;  
    private Singleton (){}  
    public static Singleton getSingleton() {  
    if (singleton == null) {  
        synchronized (Singleton.class) {  
        if (singleton == null) {  
            singleton = new Singleton();  
        }  
        }  
    }  
    return singleton;  
    }  
}
```

### 内部静态类
  这种写法仍然使用JVM本身机制保证了线程安全问题；由于 SingletonHolder 是私有的，除了 getInstance() 之外没有办法访问它，因此它是懒汉式的；同时读取实例的时候不会进行同步，没有性能缺陷；也不依赖 JDK 版本。  
```Java
public class Singleton {  
    private static class SingletonHolder {  
        private static final Singleton INSTANCE = new Singleton();  
    }  
    private Singleton (){}  
    public static final Singleton getInstance() {  
        return SingletonHolder.INSTANCE; 
    }  
}
```

### 枚举类
  这种实现方式还没有被广泛采用，但这是实现单例模式的最佳方法。它更简洁，自动支持序列化机制，绝对防止多次实例化。  
  这种方式是 Effective Java 作者 Josh Bloch 提倡的方式，它不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化。不过，由于 JDK1.5 之后才加入 enum 特性，用这种方式写不免让人感觉生疏，在实际工作中，也很少用。  
  不能通过 reflection attack 来调用私有构造方法。
```Java
public enum EasySingleton{
    INSTANCE;
}
```

## 破坏单例模式与防止
  1.反射  
  2.序列化与反序列化
  
  静态内部类单例来举例  
```Java
public class LazyInnerClassSingleton {
    // 私有的构造方法
    private LazyInnerClassSingleton(){}
    // 公有的获取实例方法
    public static final LazyInnerClassSingleton getInstance(){
        return LazyHolder.LAZY;
    }
    // 静态内部类
    private static class LazyHolder{
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
```

### 反射破坏单例模式
```Java
public static void main(String[] args) {
        try {
            //很无聊的情况下，进行破坏
            Class<?> clazz = LazyInnerClassSingleton.class;
            //通过反射拿到私有的构造方法
            Constructor c = clazz.getDeclaredConstructor(null);
            //因为要访问私有的构造方法，这里要设为true，相当于让你有权限去操作
            c.setAccessible(true);
            //暴力初始化
            Object o1 = c.newInstance();
            //调用了两次构造方法，相当于 new 了两次
            Object o2 = c.newInstance();
            //这里输出结果为false
            System.out.println(o1 == o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

### 防止反射破坏单例模式
```Java
public class LazyInnerClassSingleton {
    // 私有的构造方法
    private LazyInnerClassSingleton(){
        // 防止反射创建多个对象
        if(LazyHolder.LAZY != null){
			throw new RuntimeException("不允许创建多个实例");
		}
    }
    // 公有的获取实例方法
    public static final LazyInnerClassSingleton getInstance(){
        return LazyHolder.LAZY;
    }
    // 静态内部类
    private static class LazyHolder{
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
```

### 序列化破坏单例模式
```Java
	//序列化创建单例类
    public static void main(String[] args) {
        LazyInnerClassSingleton s1 = null;
        //通过类本身获得实例对象
        LazyInnerClassSingleton s2 = LazyInnerClassSingleton.getInstance();
        FileOutputStream fos = null;
        try {
            //序列化到文件中
            fos = new FileOutputStream("SeriableSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();
			//从文件中反序列化为对象
            FileInputStream fis = new FileInputStream("SeriableSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (LazyInnerClassSingleton) ois.readObject();
            ois.close();
            //对比结果,这里输出的结果为false
            System.out.println(s1 == s2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

### 防止序列化破坏单例模式
```Java
public class LazyInnerClassSingleton implements Serializable {

    private static final long serialVersionUID = -4264591697494981165L;

    // 私有的构造方法
    private LazyInnerClassSingleton(){
        // 防止反射创建多个对象
        if(LazyHolder.LAZY != null){
            throw new RuntimeException("只能实例化1个对象");
        }
    }
    // 公有的获取实例方法
    public static final LazyInnerClassSingleton getInstance(){
        return LazyHolder.LAZY;
    }
    // 静态内部类
    private static class LazyHolder{
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
    // 防止序列化创建多个对象,这个方法是关键
    private  Object readResolve(){
        return  LazyHolder.LAZY;
    }

}
```
