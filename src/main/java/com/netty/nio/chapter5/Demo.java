package com.netty.nio.chapter5;

//import com.google.protobuf.InvalidProtocolBufferException;
//import com.netty.nio.chapter5.sample4.PersonModel;

public class Demo {

//    public static void main(String[] args) throws InvalidProtocolBufferException {
//        PersonModel.Person.Builder builder = PersonModel.Person.newBuilder();
//        builder.setId(1);
//        builder.setName("jihite");
//        builder.setEmail("jihite@jihite.com");
//
//        PersonModel.Person person = builder.build();
//        System.out.println("before:" + person);
//
//        System.out.println("===Person Byte:");
//        for (byte b : person.toByteArray()) {
//            System.out.print(b);
//        }
//        System.out.println("================");
//
//        byte[] byteArray = person.toByteArray();
//        PersonModel.Person p2 = PersonModel.Person.parseFrom(byteArray);
//        System.out.println("after id:" + p2.getId());
//        System.out.println("after name:" + p2.getName());
//        System.out.println("after email:" + p2.getEmail());
//    }
}
