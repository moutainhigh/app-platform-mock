package com.bill99.mam.platform;

import com.google.common.base.Optional;
import org.junit.Test;

public class MainTest {
    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//        System.out.println(DateTime.now().toString("yyyyMMddHHmmssSSS"));
//        System.out.println(ThreadLocalRandom.current().nextLong(9999));
//        System.out.println(CommonUtil.random(10));
//        String heads = "headers=X-99Bill-TraceId: 1524811735434\n" +
//                "X-99Bill-PlatformCode: 10012203599\n"+
//                "X-99Bill-Signature: 10012203599\n";
//        String newheads = heads.substring(8);
//        System.out.println(newheads);
//        System.out.println(Splitter.on("\n").split(newheads));
//
//        Map<String, String> map = Splitter.on("\n").omitEmptyStrings().withKeyValueSeparator(":").split(newheads);
//
//        map = Maps.filterKeys(map,new Predicate<String>(){
//            @Override
//            public boolean apply(@Nullable String input) {
//                return !input.startsWith("X-99Bill-Signature");
//            }
//
//            @Override
//            public boolean test(@Nullable String input) {
//                return false;
//            }
//        });
//
//        System.out.println(map);


        String str = "4545";

        String defaultString = Optional.fromNullable(str).or("1213");
        Long num = null;
        Long num1 = Optional.fromNullable(num).or(1l);
        System.out.println(Optional.fromNullable(str).or("1213"));
    }



}
