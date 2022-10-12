// 依赖crypto-js

const mode = {
    // CryptoJS.MD5必须转为字符串！
    // 密钥偏移量，ECB模式不需要
    // iv: CryptoJS.enc.Utf8.parse((""+CryptoJS.MD5("salt")).slice(8, 24)),
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7,
}

// AES加密
// 文件需使用btoa()将字节流转为Base64处理

// 加密方法
function aesEncrypt(key, data) {
    // CryptoJS.MD5必须转为字符串！
    key = CryptoJS.enc.Utf8.parse(key);
    let srcs = CryptoJS.enc.Utf8.parse(data);
    let encrypted = CryptoJS.AES.encrypt(srcs, key, mode);
    return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
}
// 解密方法
function aesDecrypt(key, data) {
    // CryptoJS.MD5必须转为字符串！
    key = CryptoJS.enc.Utf8.parse(key);
    let encryptedHexStr = CryptoJS.enc.Base64.parse(data);
    let srcs = CryptoJS.enc.Base64.stringify(encryptedHexStr);
    let decrypt = CryptoJS.AES.decrypt(srcs, key, mode);
    return decrypt.toString(CryptoJS.enc.Utf8);
}

function base64ToArrayBuffer(base64) {
    var binary_string = window.atob(base64);
    var len = binary_string.length;
    var bytes = new Uint8Array(len);
    for (var i = 0; i < len; i++) {
        bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
}