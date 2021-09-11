package com.baoliang.spring.Helper;

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
    /**
     * 将所有的BeanDefintion放入map中，挑选出Bean
     */
    public static void LoadAllBean() {
        for (Class<?> clazz : classesHashSet) {
            if (!clazz.isAnnotationPresent(Component.class))
                continue;
            BeanDefintion newBeanDefine = new BeanDefintion();
            newBeanDefine.setClazz(clazz);
            String BeanName=clazz.getName();
            BeanName=getBeanName(BeanName);
            if(!clazz.getAnnotation(Component.class).value().equals(""))
                BeanName=clazz.getAnnotation(Component.class).value();
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
                newBeanDefine.setScope(ScopeEnum.SingleTon.toString());
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
                                   String key= f.getAbsolutePath().replace("\\",".");
                                   key=key.substring(key.indexOf(annotationPath),key.indexOf(f.getName())+f.getName().length()-6);
                                   AddToAspects(clazz,key,false,"");
                                }

                            }else
                            {
                                String key= file.getAbsolutePath().replace("\\",".");
                                key=key.substring(key.indexOf(annotationPath),key.indexOf(file.getName())+file.getName().length()-6);
                                AddToAspects(clazz,key,false,"");
                            }
                        }

                    }
//                    AddToAspect(method, annotationPath, methodNode);

                }
            }
                beanDefintionHashMap.put(newBeanDefine.getBeanName(), newBeanDefine);
//            beanDefintionHashMap.put(getBeanName(clazz.getName()), newBeanDefine);
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
                if (beanDefintion.getScope().equals(ScopeEnum.SingleTon.toString())) {

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
            else if(Container.earlySingletonObjects.containsKey(beanDefintion.getBeanName()))
            {
                //只能在第二级填充
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
                populate(beanDefintion.getBeanName());
                //扔到二级
                Container.earlySingletonObjects.put(beanDefintion.getBeanName(),Container.singletonFactory.get(beanDefintion.getBeanName()).getTarget());
                Container.singletonFactory.remove(beanDefintion.getBeanName());
                //扔到一级
                Container.singletonObjects.put(beanDefintion.getBeanName(),Container.earlySingletonObjects.get(beanDefintion.getBeanName()));
                //后置处理器
                Container.earlySingletonObjects.remove(beanDefintion.getBeanName());
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
    private static void populate(String BeanName)
    {
        try {
                Class<?> bean=Container.singletonFactory.get(BeanName).getBeanDefintion().getClazz();
                for (Field delclaredField : bean.getDeclaredFields())
                {
                    if(!delclaredField.isAnnotationPresent(Autowired.class))
                        continue;
                    String[] name=delclaredField.getType().toString().split("\\.");
                    String benName=name[name.length-1];
                    String a=delclaredField.getAnnotation(Autowired.class).value();
                    if(!delclaredField.getAnnotation(Autowired.class).value().equals(""))
                        benName=delclaredField.getAnnotation(Autowired.class).value();
                    Object fBean = getBean(benName);
                    delclaredField.setAccessible(true);
                    delclaredField.set(Container.singletonFactory.get(BeanName).getInstance(), fBean);
                }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    public static Object getBean(String BeanName)
    {//没有判断是不是单例，后期改进
        if(beanDefintionHashMap.get(BeanName).getScope().equals(ScopeEnum.SingleTon))
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
            String className=fileName.substring(fileName.indexOf("classes")+8,fileName.indexOf(".class")).replace("\\",".");
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
