
package Encyptor.cipher;

//useless class only here because I need a new return type


//might add password and other stuff here in the future depending on how usefull that will be 
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
