package authenticationsystem;
//Steven Miller - IT 145-Q1302
//1-3 Application Activity
//28 Nov 2016
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException; 

public class UserRole {
    FileInputStream fileByteStream = null;              //File input stream for roles
    Scanner roleScnr = null;                            //Role scanner
    private String[] roleInfo = new String[3];          //User roles array
    
    //Open role files and store in roles array, then close files
    public String[] getRoleInfo() throws FileNotFoundException, IOException {
        
        //Zookeeper role
        fileByteStream = new FileInputStream("zookeeper file.txt");
        roleScnr = new Scanner(fileByteStream).useDelimiter("\\Z");
        roleInfo[0] = roleScnr.next();
        fileByteStream.close();
        
        //Admin role
        fileByteStream = new FileInputStream("admin file.txt");
        roleScnr = new Scanner(fileByteStream).useDelimiter("\\Z");
        roleInfo[1] = roleScnr.next();
        fileByteStream.close();
        
        //Veterinarian role 
        fileByteStream = new FileInputStream("veterinarian file.txt");
        roleScnr = new Scanner(fileByteStream).useDelimiter("\\Z");
        roleInfo[2] = roleScnr.next();
        fileByteStream.close();
        
        return roleInfo;
    }
}
