package com.tjl.controller;

import com.tjl.util.AESUtil;
import com.tjl.util.RSAUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Base64;

@RestController
public class Controller {
    // 文件保存路径
    private final String FILE_PATH = System.getProperty("user.dir") + File.separator + "uploadfiles" + File.separator;
    // 文件加密用公私钥（每次启动服务时自动生成）
    private final KeyPair KEY_PAIR = RSAUtil.getRSAKeyPair();
    /**
     * 上传文件
     */
    @GetMapping("/get-public-key")
    public String getPublicKey() throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(RSAUtil.getPublicKeyBytes(KEY_PAIR.getPublic()));
    }
    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file")MultipartFile file,@RequestParam(value = "key") String key) throws IOException, GeneralSecurityException {
        key = new String(RSAUtil.decrypt(Base64.getDecoder().decode(key), KEY_PAIR.getPrivate()));
        System.out.println("================================================================================================");
        System.out.println("本次传输对称密钥为：" + key);
        System.out.println("文件保存路径：" + FILE_PATH);
        System.out.println("================================================================================================");
        File outFile = new File(FILE_PATH + file.getOriginalFilename());
        FileUtils.writeByteArrayToFile(outFile, Base64.getDecoder().decode(AESUtil.aesDecrypt(file.getBytes(), key.getBytes())));
        return "ok.";
    }
}
