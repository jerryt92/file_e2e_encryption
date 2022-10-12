package com.tjl;

import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileNotFoundException;

public class TestMain {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());
        System.out.println(ResourceUtils.getURL("classpath:").getPath());
        System.out.println(System.getProperty("user.dir") + File.separator + "uploadfiles"); // 项目根目录
    }
}
