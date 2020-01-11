# 组合模式
## 1. 介绍
组合模式(Composite Pattern)：组合多个对象形成树形结构以表示具有 "整体—部分" 关系的层次结构。组合模式对单个对象（即叶子对象）和组合对象（即容器对象）的使用具有一致性，组合模式又可以称为 "整体—部分"(Part-Whole) 模式。  
组合模式对单个对象(叶子对象)和组合对象(组合对象)具有一致性，它将对象组织到树结构中，可以用来描述整体与部分的关系。同时它也模糊了简单元素(叶子对象)和复杂元素(容器对象)的概念，使得客户能够像处理简单元素一样来处理复杂元素，从而使客户程序能够与复杂元素的内部结构解耦。  

## 2. 适用场景
1. 在具有整体和部分的层次结构中，希望通过一种方式忽略整体与部分的差异，客户端可以一致地对待它们。  
2. 在一个使用面向对象语言开发的系统中需要处理一个树形结构。  
3. 在一个系统中能够分离出叶子对象和容器对象，而且它们的类型不固定，需要增加一些新的类型。  

## 3. 角色分配
![]()
1. Component(抽象构件角色): 它可以是接口或抽象类，为叶子构件和容器构件对象声明接口，在该角色中可以包含所有子类共有行为的声明和实现。在抽象构件中定义了访问及管理它的子构件的方法，如增加子构件、删除子构件、获取子构件等。  
2. Leaf（叶子构件）：它在组合结构中表示叶子节点对象，叶子节点没有子节点，它实现了在抽象构件中定义的行为。对于那些访问及管理子构件的方法，可以通过异常等方式进行处理。  
3. Composite（容器构件）：它在组合结构中表示容器节点对象，容器节点包含子节点，其子节点可以是叶子节点，也可以是容器节点，它提供一个集合用于存储子节点，实现了在抽象构件中定义的行为，包括那些访问及管理子构件的方法，在其业务方法中可以递归调用其子节点的业务方法。  

## 4. 实例
文件类
```java
public abstract class File {
    String name;
    
    public File(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void display();
}
```

文件夹类
```java
public class Folder extends File{
    private List<File> files;
    
    public Folder(String name){
        super(name);
        files = new ArrayList<File>();
    }
    
    /**
     * 浏览文件夹中的文件
     */
    public void display() {
        for(File file : files){
            file.display();
        }
    }
    
    /**
     * @desc 向文件夹中添加文件
     * @param file
     * @return void
     */
    public void add(File file){
        files.add(file);
    }
    
    /**
     * @desc 从文件夹中删除文件
     * @param file
     * @return void
     */
    public void remove(File file){
        files.remove(file);
    }
}
```

文本文件
```java
public class TextFile extends File{

    public TextFile(String name) {
        super(name);
    }

    public void display() {
        System.out.println("这是文本文件，文件名：" + super.getName());
    }
    
}
```

图像文件
```java
public class ImagerFile extends File{

    public ImagerFile(String name) {
        super(name);
    }

    public void display() {
        System.out.println("这是图像文件，文件名：" + super.getName());
    }

}
```

影像文件
```java
public class VideoFile extends File{

    public VideoFile(String name) {
        super(name);
    }

    public void display() {
        System.out.println("这是影像文件，文件名：" + super.getName());
    }

}
```

客户端
```java
public class Client {
    public static void main(String[] args) {

        //总文件夹
        Folder zwjj = new Folder("总文件夹");
        //向总文件夹中放入三个文件：1.txt、2.jpg、1文件夹
        TextFile aText= new TextFile("a.txt");
        ImagerFile bImager = new ImagerFile("b.jpg");
        Folder cFolder = new Folder("C文件夹");
        
        zwjj.add(aText);
        zwjj.add(bImager);
        zwjj.add(cFolder);
        
        //向C文件夹中添加文件：c_1.txt、c_1.rmvb、c_1.jpg 
        TextFile cText = new TextFile("c_1.txt");
        ImagerFile cImage = new ImagerFile("c_1.jpg");
        VideoFile cVideo = new VideoFile("c_1.rmvb");
        
        cFolder.add(cText);
        cFolder.add(cImage);
        cFolder.add(cVideo);
        
        //遍历C文件夹
        cFolder.display();
        //将c_1.txt删除
        cFolder.remove(cText);
        System.out.println("-----------------------");
        cFolder.display();
    }
}
```

## 5. 模式优点
* 可以清楚地定义分层次的复杂对象，表示对象的全部或部分层次，使得增加新构件也更容易。  
* 客户端调用简单，客户端可以一致的使用组合结构或其中单个对象。  
* 定义了包含叶子对象和容器对象的类层次结构，叶子对象可以被组合成更复杂的容器对象，而这个容器对象又可以被组合，这样不断递归下去，可以形成复杂的树形结构。  
* 更容易在组合体内加入对象构件，客户端不必因为加入了新的对象构件而更改原有代码。  

## 6. 模式缺点
* 使得设计更加复杂，客户端需要花更多时间理清类之间的层次关系。  
* 在增加新构件时很难对容器中的构件类型进行限制。  

## 7. 模式在JDK中的应用
1. java.awt.Container类