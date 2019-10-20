package com.netty.nio.chapter10;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
//        List emptyList = Collections.EMPTY_LIST;
//        List<String> list = Collections.synchronizedList(new ArrayList<String>());
//        EnumSet
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        System.out.println(cis.equals(s));
        System.out.println(s.equals(cis));

    }
    static class CaseInsensitiveString{

        private final String s;

        public CaseInsensitiveString(String s) {
            if (s==null){
                throw new NullPointerException();
            }
            this.s = s;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CaseInsensitiveString){
                return s.equalsIgnoreCase(((CaseInsensitiveString)obj).s);
            }
            if (obj instanceof String){
            return s.equalsIgnoreCase((String)obj);
            }
            return false;
        }
    }

}
