package org.fenixsoft.oom;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * <p>
 * 借助CGLib使方法区出现内存溢出异常
 * <br />
 * <b>VM Args： -XX:PermSize=10M -XX:MaxPermSize=10M</b>
 * </p>
 * <p>
 * 方法区用于存放Class的相关信息，如类名、访问修饰符、常量池、字段描述、方法描述等。
 * 对于这些区域的测试，基本的思路是运行时产生大量的类去填满方法区，直到溢出。
 * 虽然直接使用Java SE API也可以动态产生类（如反射时的GeneratedConstructorAccessor和动态代理等），
 * 但在本次实验中操作起来比较麻烦。在代码中借助CGLib直接操作字节码运行时生成了大量的动态类。
 * </p>
 * <p>
 * 值得特别注意的是，我们在这个例子中模拟的场景并非纯粹是一个实验，这样的情况经常会出现在实际应用中：
 * 当前的很多主流框架，如Spring、Hibernate，在对类进行增强时，都会使用到CGLib这类字节码技术，
 * 增强的类越多，就需要越大的方法区来保证动态生成的Class可以加载入内存。
 * 另外，JVM上的动态语言（例如Groovy等）通常都会持续创建类来实现语言的动态性，
 * 随着这类语言的流行，也越来越容易遇到与以下代码相似的溢出场景。
 * </p>
 * <p>
 * 方法区溢出也是一种常见的内存溢出异常，一个类要被垃圾收集器回收掉，判定条件是比较苛刻的。
 * 在经常动态生成大量Class的应用中，需要特别注意类的回收状况。这类场景除了上面提到的程序使用了CGLib字节码增强和动态语言之外，
 * 常见的还有：大量JSP或动态产生JSP文件的应用（JSP第一次运行时需要编译为Java类）、基于OSGi的应用（即使是同一个类文件，被不同的加载器加载也会视为不同的类）等。
 * </p>
 * 
 * @author zzm
 */
public class JavaMethodAreaOOM {

    public static void main(String[] args) {

        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {

                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }

    static class OOMObject {
    }
}
