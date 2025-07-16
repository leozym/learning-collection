package com.zym.foundation;

import com.zym.model.ObjectTestModel;

public class ObjectTest {
    public static void main(String[] args) {

//        testHashChanged();
        testHashSame();

    }

    //测试修改值后hashcode是否变化
    private static void testHashChanged(){

        ObjectTestModel objectTestModel = new ObjectTestModel();
        int hashEmpty = objectTestModel.hashCode();
        System.out.println("hashEmpty = " + hashEmpty);
        int sysHashEmpty = System.identityHashCode(objectTestModel);
        System.out.println("sysHashEmpty = " + sysHashEmpty);

        objectTestModel.setId("1");
        objectTestModel.setName("test");
        objectTestModel.setAge(12);
        int hashFull = objectTestModel.hashCode();
        System.out.println("hashFull = " + hashFull);
        int sysHashFull = System.identityHashCode(objectTestModel);
        System.out.println("sysHashFull = " + sysHashFull);

        objectTestModel.setId("2");
        int hashFullChanged = objectTestModel.hashCode();
        System.out.println("hashFullChanged = " + hashFullChanged);
        int sysHashFullChanged = System.identityHashCode(objectTestModel);
        System.out.println("sysHashFullChanged = " + sysHashFullChanged);
    }

    //测试两个相同值的对象的hash值
    private static void testHashSame(){
        ObjectTestModel objectTestModel = new ObjectTestModel();
        ObjectTestModel objectTestModel2 = new ObjectTestModel();

        int hashEmpty = objectTestModel.hashCode();
        int hashEmpty2 = objectTestModel2.hashCode();

        System.out.println("hashEmpty = " + hashEmpty);
        System.out.println("hashEmpty2 = " + hashEmpty2);


        objectTestModel.setId("1");
        objectTestModel.setName("test");
        objectTestModel.setAge(12);

        objectTestModel2.setId("1");
        objectTestModel2.setName("test");
        objectTestModel2.setAge(12);

        int hashFull = objectTestModel.hashCode();
        System.out.println("hashFull = " + hashFull);
        System.out.println("hashFull name = " + objectTestModel.getName().hashCode());
        int hashFull2 = objectTestModel2.hashCode();
        System.out.println("hashFull2 = " + hashFull2);
        System.out.println("hashFull2 name = " + objectTestModel2.getName().hashCode());


        //lombok的@Data注解重写了equals方法，返回true
        //不重写equals时，调用Object的equals， return (this == obj); 返回false
        System.out.println("equals() = " + objectTestModel.equals(objectTestModel2));
        System.out.println("== = " + (objectTestModel == objectTestModel2));

    }
}

