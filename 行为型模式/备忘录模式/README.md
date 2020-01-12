# 备忘录模式
## 1. 介绍
备忘录模式（Memento Pattern）：备忘录对象是一个用来存储另外一个对象内部状态的快照的对象。备忘录模式的用意是在不破坏封装的条件下，将一个对象的状态捕捉(Capture)住，并外部化，存储起来，从而可以在将来合适的时候把这个对象还原到存储起来的状态。  

## 2. 适用场景
1. 需要保存一个对象在某一个时刻的状态或者部分状态  
2. 如果用一个接口来让其他对象的到这些状态，将会暴露这个对象的实现细节并破坏对象的的封装性，一个对象不希望外界直接访问其内部状态，通过中间对象可以间接访问其内部状态  

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E8%A1%8C%E4%B8%BA%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E5%A4%87%E5%BF%98%E5%BD%95%E6%A8%A1%E5%BC%8F/%E5%A4%87%E5%BF%98%E5%BD%95%E6%A8%A1%E5%BC%8F.png)
1. 备忘录(Memento)角色：将发起人（Originator）对象的内战状态存储起来。备忘录可以根据发起人对象的判断来决定存储多少发起人（Originator）对象的内部状态；备忘录可以保护其内容不被发起人（Originator）对象之外的任何对象所读取。  
2. 发起人（Originator）角色：创建一个含有当前的内部状态的备忘录对象；使用备忘录对象存储其内部状态。  
3. 负责人（Caretaker）角色：负责保存备忘录对象；不检查备忘录对象的内容。  

## 4. 实例
备忘录类
```java
    /**
     * 备忘录类
     */
    public class Memoto {
        public int mCheckpoint;
        public int mLifeValue;
        public String mWeapon;

        @Override
        public String toString() {
            return "Memoto [mCheckpoint="+mCheckpoint+
                    " mLifeValue"+mLifeValue+
                    " mWeapon"+mWeapon+"]";
        }
    }
```

发起人类
```java
    /*
     * 使命召唤游戏演戏（Originator）
     */
    public class CallOfDuty {
        private int mCheckpoint = 1;
        private int mLifeValue = 100;
        private String mWeapon = "沙漠之鹰";

        public void play(){
            System.out.println("打游戏：" + String.format("第%d关", mCheckpoint) + "奋战杀敌中");
            mLifeValue -=10;
            System.out.println("进度升级了");
            mCheckpoint++;
            System.out.println("到达"+String.format("第%d关", mCheckpoint));
        }

        public void quit(){
            System.out.println("---------------------");
            System.out.println("退出前的属性："+this.toString());
            System.out.println("退出游戏");
            System.out.println("---------------------");
        }

        //创建备忘录
        public Memoto createMemoto(){
            Memoto memoto = new Memoto();
            memoto.mCheckpoint = mCheckpoint;
            memoto.mLifeValue = mLifeValue;
            memoto.mWeapon = mWeapon;
            return memoto;
        }

        public void restore(Memoto memoto){
            this.mCheckpoint = memoto.mCheckpoint;
            this.mLifeValue = memoto.mLifeValue;
            this.mWeapon = memoto.mWeapon;
            System.out.println("恢复后的游戏属性："+this.toString());
        }

        @Override
        public String toString() {
            return "CallOfDuty [mCheckpoint="+mCheckpoint+
                    " mLifeValue"+mLifeValue+
                    " mWeapon"+mWeapon+"]";
        }
    }
```

负责人
```java
    /**
     * 负责管理Memoto
     */
    public class Caretaker {
        //备忘录
        Memoto memoto;

        //存档
        public void archive(Memoto memoto){
            this.memoto = memoto;
        }

        //获取存档
        public Memoto getMemoto(){
            return memoto;
        }
    }
```

客户端
```java
    public class Client {

    public static void main(String[] args) {
        //构建游戏对象
        CallOfDuty callOfDuty = new CallOfDuty();
        //开始游戏
        callOfDuty.play();
        //构建caretaker，用于游戏存档
        Caretaker caretaker = new Caretaker();
        //通过游戏本身创建备忘录，caretaker执行存档操作
        caretaker.archive(callOfDuty.createMemoto());
        //退出游戏
        callOfDuty.quit();
        //重新开启游戏，并通过caretaker恢复游戏进度
        CallOfDuty callOfDuty1 = new CallOfDuty();
        callOfDuty1.restore(caretaker.getMemoto());
        }
    }
```

## 5. 模式优点
* 当发起人角色中的状态改变时，有可能这是个错误的改变，我们使用备忘录模式就可以把这个错误的改变还原。  
* 备份的状态是保存在发起人角色之外的，这样，发起人角色就不需要对各个备份的状态进行管理。  

## 6. 模式缺点
* 在实际应用中，备忘录模式都是多状态和多备份的，发起人角色的状态需要存储到备忘录对象中，对资源的消耗是比较严重的。  

## 7. 模式在JDK中的应用
1. java.util.Date