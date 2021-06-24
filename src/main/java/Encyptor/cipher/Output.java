
package Encyptor.cipher;

//useless class only here because I need a new return type

public class Output {
    private byte[] iv;
    private byte[] salt;
    public Output(byte[] IV, byte[] SALT){
        iv = IV;
        salt = SALT;
    }
    public byte[] retIv(){
        return iv;
    }
    public byte[] retSalt(){
        return salt;
    }
}
