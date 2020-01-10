# 原型模式
## 1. 介绍
原型模式（Prototype）：用一个已创建的实例作为原型(原型实例)，通过拷贝（复制）该原型对象来创建一个和原型相同或相似的对象。  
使用原型模式创建对象非常高效，不需要知道对象创建的细节。例如 Windows 操作系统的 Ghost 安装，在安装一次操作系统后，做成 Ghost 镜像，下次安装就直接拷贝即可，无须再设置操作系统安装的每个细节。  

## 2. 适用场景
1. 对象之间相同或相似，即只有个别的几个属性不同时。
2. 对象的创建过程比较麻烦，但复制比较简单的时候。  

## 3. 角色分配
![]()
1. 抽象原型类(Prototype)：规定了具体原型对象必须实现的接口。  
2. 具体原型类(ConcretePrototype)：实现抽象原型类的 clone() 方法，是可被复制的对象。
3. 访问类(Client)：调用具体原型类中的 clone() 方法来复制新的对象。

## 4. 模式应用
原型模式的**克隆**分为**浅克隆**和**深克隆**，Java 中的 Object 类提供了浅克隆的 clone() 方法，具体原型类只要实现**Cloneable**接口，重写 clone() 方法时引用超父类（Object）的 clone() 方法，就可实现对象的 浅克隆，Cloneable 接口就是抽象原型类。
* 浅克隆：创建一个新对象(副本)，然后将非静态属性复制到新对象。如果属性是值类型，则对该属性执行逐位复制。如果属性是引用类型，则复制引用，但不复制引用的对象；因此，原始对像与副本引用同一个对象。
* 深克隆：创建一个新对象(副本)，属性是引用类型，引用的对象也会被克隆，不再指向原有对象地址。
对象实现 Cloneable 接口并重写 clone() 方法（引用超父类 Object 的 clone() 方法不进行任何额外操作），对象调用 clone() 方法进行的是 浅克隆。若使用对象流将对象写入流然后再读出，则是深克隆。  
对象克隆是创建一个新对象，与原对象不是同一个地址，从而可以引申出，如果对象的引用类型属性对象也实现了 Cloneable 接口，重写了 clone() 方法，直到没有引用类型属性或者引用类型属性为 null 时，整体上就形成了 深克隆。即引用类型属性的引用类型属性都实现了 Cloneable 接口，重写 clone() 方法，并在 clone() 方法中进行调用。

### 1. 浅克隆实例
原型对象实现 Cloneable 接口，重写 clone() 方法。  
Cloneable 是抽象原型类，默认提供由 Object 类实现的 clone() 方法，User 是具体原型类。
```Java
public class User implements Cloneable {

    private String name;
    private Integer age;

    private Student student;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Student getStudent() {
        return student;
    }

    public User setStudent(Student student) {
        this.student = student;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```

客户端调用
```Java
public class MainTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        User user = new User("Kitty", 21).setStudent(new Student(3));
        User cloneUser = (User) user.clone();

        System.out.println(user == cloneUser);
    }
}
```

### 深克隆实例
