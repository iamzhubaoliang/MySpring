package com.baoliang.spring.Helper;

import java.awt.image.renderable.ContextualRenderedImageFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.baoliang.spring.Annotation.*;
import com.baoliang.spring.Annotation.*;
import com.baoliang.spring.Enum.ScopeEnum;
import com.baoliang.spring.Helper.System.Utils;
import com.baoliang.spring.Interface.BeanNameAware;
import com.baoliang.spring.Interface.BeanPostProcessor;
import com.baoliang.spring.Interface.InitializingBean;


public class LoadBeanHelper {

    private static HashSet<Class<?>> classesHashSet = new HashSet<>();
    private static ClassLoader classLoader = Container.classLoader;
    private static HashMap<String, BeanDefintion> beanDefintionHashMap = new HashMap<>();
    private static HashMap<String, ArrayList<MethodNode>> BeforeDelegatedSet = new HashMap<>();
    private static HashMap<String, ArrayList<MethodNode>> AfterDelegatedSet = new HashMap<>();
    private static ArrayList<BeanPostProcessor> BeanPostProcessorList=new ArrayList<>();
    private static String splitOP= "\\";
    static {
        if(Utils.getSystem().equals("Linux"))
            splitOP="/";

    }

    /**
     * 将所有的BeanDefintion放入map中，挑选出Bean
     */
    public static void LoadAllBean() {
        for (Class<?> clazz : classesHashSet) {
            if (!(clazz.isAnnotationPresent(Component.class)||clazz.isAnnotationPresent(Controller.class)))
                continue;
            BeanDefintion newBeanDefine = new BeanDefintion();
            newBeanDefine.setClazz(clazz);
            String BeanName=clazz.getName();
            BeanName=getBeanName(BeanName);
            if(clazz.isAnnotationPresent(Component.class)&&!clazz.getAnnotation(Component.class).value().equals(""))
                BeanName=clazz.getAnnotation(Component.class).value();
            if(clazz.isAnnotationPresent(Controller.class)&&!clazz.getAnnotation(Controller.class).value().equals("")) {
                BeanName = clazz.getAnnotation(Controller.class).value();

            }
            if(clazz.isAnnotationPresent(Controller.class))
                newBeanDefine.setIsController(true);
            newBeanDefine.setBeanName(BeanName);
            //加载后置处理器
            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                try {
                    BeanPostProcessorList.add((BeanPostProcessor) clazz.getDeclaredConstructor().newInstance());

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }


            //scope
            if (clazz.isAnnotationPresent(Scope.class)) {
                String scope = clazz.getDeclaredAnnotation(Scope.class).value();
                newBeanDefine.setScope(scope);
            } else {
                //默认为单例模式
                newBeanDefine.setScope("SingleTon");
            }
            //添加切面
            if (clazz.isAnnotationPresent(Aspect.class)) {
                for (Method method : clazz.getMethods()) {
                    String annotationPath = "";

                    if (method.isAnnotationPresent(PointCut.class)) {
                        String delegateString = method.getAnnotation(PointCut.class).value();
                        annotationPath=delegateString;
                        if (delegateString.charAt(delegateString.length() - 1) == ')') {

                            //说明是在某个方法上面的切面
                            annotationPath =annotationPath.replace("()","");
                            //切掉方法保留到类
                            String[] seg=cutName(annotationPath);
                            AddToAspects(clazz,seg[0],true,seg[1]);
                        }else
                        {//切面是某个包或者类
                            String annotationPathClone=new String(annotationPath);
                            URL resource=Container.classLoader.getResource(annotationPathClone.replace(".","/"));
                            if(resource==null)
                                resource=Container.classLoader.getResource(annotationPathClone.replace(".","/")+".class");
                            File file=new File(resource.getFile());
//                            System.out.println(file);
                            if(file.isDirectory()) {
                                ArrayList<File> fileArray=new ArrayList<>();
                                DFSgetCureentDir(file,fileArray);
                                for(File f:fileArray)
                                {
                                   String key= f.getAbsolutePath().replace(splitOP,".");
                                   key=key.substring(key.indexOf(annotationPath),key.indexOf(f.getName())+f.getName().length()-6);
                                   AddToAspects(clazz,key,false,"");
                                }

                            }else
                            {
                                String key= file.getAbsolutePath().replace(splitOP,".");
                                key=key.substring(key.indexOf(annotationPath),key.indexOf(file.getName())+file.getName().length()-6);
                                AddToAspects(clazz,key,false,"");
                            }
                        }

                    }


                }
            }
                beanDefintionHashMap.put(newBeanDefine.getBeanName(), newBeanDefine);

        }


    }

    private static void AddToAspects(Class<?> clazz,String key,boolean isMethod,String MethodName) {
        for (Method method : clazz.getMethods())
        {
            MethodNode methodNode=new MethodNode(method,isMethod);
            methodNode.setMethodName(MethodName);

            if(method.isAnnotationPresent(Before.class))
            {
                if(!BeforeDelegatedSet.containsKey(key))
                    BeforeDelegatedSet.put(key,new ArrayList<>());
               BeforeDelegatedSet.get(key).add(methodNode);
            }
            if(method.isAnnotationPresent(After.class))
            {
                if(!AfterDelegatedSet.containsKey(key))
                    AfterDelegatedSet.put(key,new ArrayList<>());
               AfterDelegatedSet.get(key).add(methodNode);
            }
        }

    }

    /**
     * 当切点是方法的时候，切割分离方法名称和全限定类名称
     * @param name
     * @return
     */
    private static String[] cutName(String name)
    {
        StringBuilder res=new StringBuilder("");
        String[] segString=name.split("\\.");
        for(int i=0;i< segString.length-1;i++)
        {
            res.append(segString[i]);
            if(i<segString.length-2)
                res.append(".");
        }
        return new String[]{res.toString(), segString[segString.length - 1]};

    }

    /**
     * 全包名截取Bean的名称
     * @param FullName
     * @return
     */
    private static String getBeanName(String FullName)
    {
        String[] name=FullName.split("\\.");
        return name[name.length-1];
    }

    public static void ProductBean()
    {


            for (String beanName : beanDefintionHashMap.keySet()) {
                BeanDefintion beanDefintion = beanDefintionHashMap.get(beanName);
//                beanDefintion.setBeanName(beanName);
//                如果是单例变成生产工厂
                if (beanDefintion.getScope().equals("SingleTon")) {

                    CreateBean(beanDefintion,true);

                }
            }
//        System.out.println("s");

    }
    private static Object CreateBean(BeanDefintion beanDefintion,Boolean sinleton) {
        //判断是不是在二级缓存中存在
        try {
            //如果在一级直接返回
            if(Container.singletonObjects.containsKey(beanDefintion.getBeanName())&&sinleton)
                return Container.singletonObjects.get(beanDefintion);
            else if(Container.singletonFactory.containsKey(beanDefintion.getBeanName()))
            {
                return Container.earlySingletonObjects.get(beanDefintion.getBeanName());

            }else {
                if(beanDefintion.getClazz().isInterface())
                   return null;
                //调用后置处理器
                //调用后置处理器方法

                Object instance = beanDefintion.getClazz().getDeclaredConstructor().newInstance();

                DynamicBeanFactory dynamicBeanFactory = new DynamicBeanFactory();
                dynamicBeanFactory.setInstance(instance);
                dynamicBeanFactory.setBeanDefintion(beanDefintion);
                //查看是否存在切面并放入工厂中，在工厂中准备代理
                if(BeforeDelegatedSet.containsKey(beanDefintion.getClazz().getName()))
                {
                    dynamicBeanFactory.setDelegated(true);
                    dynamicBeanFactory.setBeforemethodCache(BeforeDelegatedSet.get(beanDefintion.getClazz().getName()));
                }
                if(AfterDelegatedSet.containsKey(beanDefintion.getClazz().getName()))
                {
                    dynamicBeanFactory.setDelegated(true);
                    dynamicBeanFactory.setAftermethodCache(AfterDelegatedSet.get(beanDefintion.getClazz().getName()));
                }

                //扔到三级缓存
                Container.singletonFactory.put(beanDefintion.getBeanName(), dynamicBeanFactory);
                Object targetBean=populate(beanDefintion.getBeanName());

                if(Container.earlySingletonObjects.containsKey(beanDefintion.getBeanName()))
                    Container.earlySingletonObjects.remove(beanDefintion.getBeanName());
                if(Container.singletonFactory.containsKey(beanDefintion.getBeanName()))
                    Container.singletonFactory.remove(beanDefintion.getBeanName());

                //扔到一级
                Container.singletonObjects.put(beanDefintion.getBeanName(),targetBean);


                //后置处理器

                //加入Controllermap引用
                if(beanDefintion.isController())
                    Container.controllerMap.put(beanDefintion.getBeanName(),Container.singletonObjects.get(beanDefintion.getBeanName()));


                if(instance instanceof BeanNameAware)
                {
                    ((BeanNameAware)instance).SetBeanName(beanDefintion.getBeanName());
                }
                for(BeanPostProcessor processor:BeanPostProcessorList)
                {

                    Container.singletonObjects.put(beanDefintion.getBeanName(),processor.postProcessBeforeInitialization(instance,beanDefintion.getBeanName()));

                }
                if(instance instanceof InitializingBean)
                {
                   ((InitializingBean) instance).afterPropertiesSet();
                }
                for(BeanPostProcessor processor:BeanPostProcessorList)
                {
                    Container.singletonObjects.put(beanDefintion.getBeanName(),processor.postProcessAfterInitialization(instance,beanDefintion.getBeanName()));

                }

//                System.out.println("s");
            }




        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(!sinleton)
        {
            Object obj=Container.singletonObjects.get(beanDefintion.getBeanName());
            //不是单例删除
            Container.singletonObjects.remove(beanDefintion.getBeanName());
            return obj;
        }


        return null;
    }

    /**
     * 获取属性，填充属性
     */
    private static Object populate(String BeanName)
    {
        try {
                Class<?> bean=null;
                if(Container.singletonFactory.containsKey(BeanName))
                    bean=Container.singletonFactory.get(BeanName).getInstance().getClass();
                else if(Container.earlySingletonObjects.containsKey(BeanName))
                    bean=Container.earlySingletonObjects.get(BeanName).getClass();
                for (Field delclaredField : bean.getDeclaredFields())
                {
                    if(!delclaredField.isAnnotationPresent(Autowired.class))
                        continue;
                    String[] name=delclaredField.getType().toString().split("\\.");
                    String benName=name[name.length-1];
                    String a=delclaredField.getAnnotation(Autowired.class).value();
                    if(!delclaredField.getAnnotation(Autowired.class).value().equals(""))
                        benName=delclaredField.getAnnotation(Autowired.class).value();
                    if(Container.singletonFactory.containsKey(benName))
                    {
                        Container.earlySingletonObjects.put(benName,Container.singletonFactory.get(benName).getTarget());
                        Container.singletonFactory.remove(benName);
                    }
                    Object fBean = getBean(benName);
                    delclaredField.setAccessible(true);
                    Object aa=Container.singletonFactory.get(BeanName);
                    if(Container.singletonFactory.containsKey(BeanName)) {
                        delclaredField.set(Container.singletonFactory.get(BeanName).getInstance(), fBean);

                    }
                    else if(Container.earlySingletonObjects.containsKey(BeanName))
                        delclaredField.set(Container.earlySingletonObjects.get(BeanName),fBean);
                }
                if(Container.singletonFactory.containsKey(BeanName)) {
                    Object res=Container.singletonFactory.get(BeanName).getTarget();

                    return res;
                }
                else if(Container.earlySingletonObjects.containsKey(BeanName))
                {
                    Object res=Container.earlySingletonObjects.get(BeanName);

                    return  res;
                }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static Object getBean(String BeanName)
    {//没有判断是不是单例，后期改进
        if(beanDefintionHashMap.get(BeanName).getScope().equals("SingleTon"))
        {
            if (Container.singletonObjects.containsKey(BeanName))
                return Container.singletonObjects.get(BeanName);
            else {
                return CreateBean(beanDefintionHashMap.get(BeanName),true);
            }
        }else
        {
            return CreateBean(beanDefintionHashMap.get(BeanName),false);
        }
    }






    /**
     *
     *
     * @param packagePath 扫描路径
     * @return 所有类的class对象
     */
    public static Set<Class<?>> LoadAllClass(String packagePath)
    {


        URL resource=classLoader.getResource(packagePath.replace(".","/"));
        ArrayList<File> files=new ArrayList<>();
        DFSgetCureentDir(new File(resource.getFile()),files);
        Class<?>clazz;
        for (File file:files)
        {
            String fileName= file.getAbsolutePath();
            //+8是为了跳过classes这个字符串
            String className=fileName.substring(fileName.indexOf("classes")+8,fileName.indexOf(".class")).replace(splitOP,".");
            try {
                clazz=classLoader.loadClass(className);
                classesHashSet.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classesHashSet;
    }

    /**
     * 递归遍历所有的文件夹
     * @param file
     * @param fileArray
     */

    private static void DFSgetCureentDir(File file, ArrayList<File> fileArray)
    {
        File[] name=file.listFiles();
        if(name.length==0)
            return ;
        for(File every:name)
        {
            if(every.isFile())
            { //是文件
                fileArray.add(every);
            }else
            {
                //是目录
                DFSgetCureentDir(every,fileArray);
            }
        }
    }



}
