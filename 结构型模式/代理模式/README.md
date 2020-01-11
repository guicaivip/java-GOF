# 代理模式
## 1. 介绍
代理模式(Proxy Pattern):给某一个对象提供一个代理，并由代理对象控制对原对象的引用。  
所谓代理模式是指客户端并不直接调用实际的对象，而是通过调用代理，来间接的调用实际的对象。  

## 2. 适用场景
1. 安全代理：屏蔽对真实角色的直接访问  
2. 远程代理：通过代理类处理远程方法调用(RMI)。  
3. 延迟代理：先加载轻量级的代理对象，真正需要再加载真实对象。  

## 3. 角色分配
![](https://github.com/guicaivip/java-GOF/blob/master/%E7%BB%93%E6%9E%84%E5%9E%8B%E6%A8%A1%E5%BC%8F/%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F/%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F.jpg)
1. Subject(抽象主题)：它声明了真实主题和代理主题的共同接口，这样一来在任何使用真实主题的地方都可以使用代理主题，客户端通常需要针对抽象主题角色进行编程。  
2. RealSubject(真实主题)：它定义了代理角色所代表的真实对象，在真实主题角色中实现了真实的业务操作，客户端可以通过代理主题角色间接调用真实主题角色中定义的操作。  
3. Proxy(代理主题)：它包含了对真实主题的引用，从而可以在任何时候操作真实主题对象；在代理主题角色中提供一个与真实主题角色相同的接口，以便在任何时候都可以替代真实主题；代理主题角色还可以控制对真实主题的使用，负责在需要的时候创建和删除真实主题对象，并对真实主题对象的使用加以约束。通常，在代理主题角色中，客户端在调用所引用的真实主题操作之前或之后还需要执行其他操作，而不仅仅是单纯调用真实主题对象中的操作。  

## 4. 模式应用
### 1. 静态代理实例
抽象主题
```Java

abstract class Subject {
    public abstract void request();
}
```

真实主题
```Java
class RealSubject extends Subject {
    @Override
    public void request() {
        // 业务方法具体实现代码
    }
}
```

代理主题
```Java
public class Proxy extends Subject { 
	private Subject subject;
	public Proxy(Subject subject) { 
		this.subject = subject; 
		} 
	@Override 
	public void request() { 
		this.preRequest(); 
		this.subject.request(); 
		this.afterRequest(); 
		}
	
	/** 
	 *	 预处理 
	 */ 
	private void preRequest() { 
		System.out.println("执行前(before)的处理..."); 
		}
	
	/** 
	 * 	 善后处理
	 */ 
	private void afterRequest() { 
		System.out.println("执行后(after)的处理..."); 
	} 
}
```

客户端
```Java
public class ProxyClient {
     public static void main(String[] args) {
        Subject subject = new RealSubject();
        Proxy proxy = new Proxy(subject);
        proxy.request();
    }
}
```

### 2. 动态代理
动态代理有别于静态代理，是根据代理的对象，动态创建代理类。这样，就可以避免静态代理中代理类接口过多的问题。动态代理是实现方式，是通过反射来实现的，借助Java自带的java.lang.reflect.Proxy,通过固定的规则生成。  
其步骤如下：
1. 写一个委托类的接口，即静态代理的（Subject接口）  
2. 实现一个真正的委托类，即静态代理的（RealSubject类）  
3. 创建一个动态代理类，实现`InvocationHandler`接口，并重写该`invoke`方法  
4. 在测试类中，生成动态代理的对象。

#### 实例
抽象主题
```Java
interface Subject {
    public void request();
}
```

真实主题
```Java
class RealSubject implements Subject {
    @Override
    public void request() {
        // 业务方法具体实现代码
    	System.out.println("request");
    }
}
```

动态代理
```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy  implements InvocationHandler { 
	private Subject subject;
	public DynamicProxy(Subject subject) { 
		this.subject = subject; 
	} 
	 
	/** 
	 *	 预处理 
	 */ 
	private void preRequest() { 
		System.out.println("执行前(before)的处理..."); 
	}
	
	/** 
	 * 	 善后处理
	 */ 
	private void afterRequest() { 
		System.out.println("执行后(after)的处理..."); 
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		preRequest();
		Object result = method.invoke(subject, args);
		afterRequest();
		return result;
	}

	
}
```

客户端
```java
import java.lang.reflect.Proxy;

public class Client {
	public static void main(String[] args) {
		Subject realSubject = new RealSubject();
		DynamicProxy  proxy = new DynamicProxy (realSubject);
		Subject subject = (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(),new  Class[]{Subject.class}, proxy);
		subject.request();
	}
}
```

创建动态代理的对象，需要借助`Proxy.newProxyInstance`。该方法的三个参数分别是：  
* ClassLoader loader表示当前使用到的appClassloader。  
* Class<?>[] interfaces表示目标对象实现的一组接口。
* InvocationHandler h表示当前的InvocationHandler实现实例对象。

## 5. 模式优点
* 协调调用者和被调用者，降低了系统的耦合度。  
* 代理对象作为客户端和目标对象之间的中介，起到了保护目标对象的作用。 

## 6. 模式缺点
* 由于在客户端和真实主题之间增加了代理对象，因此有些类型的代理模式可能会造成请求的处理速度变慢，例如保护代理。  
* 实现代理模式需要额外的工作，而且有些代理模式的实现过程较为复杂，例如远程代理。  

## 7. 模式在JDK中的应用
1. java.lang.reflect.Proxy类