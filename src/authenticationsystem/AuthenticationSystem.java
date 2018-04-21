package authenticationsystem;
//Steven Miller - IT 145-Q1302
//1-3 Application Activity
//28 Nov 2016

import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.IOException; 
import java.util.Scanner; 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationSystem {
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String userName = "";                   //User entered username
        String passWord = "";                   //User entered password
        String[] roles = new String[3];         //User roles array
        FileInputStream fileByteStream = null;  //File input stream for credentials
        Scanner credScnr = null;                //Credential scanner
        int i = 0;                              //Loop variable for reading credentials
        int count = 0;                          //Loop variable for password attempts

        //Create user role object, prepare to store user role
        UserRole roleData = new UserRole();
        roles = roleData.getRoleInfo();
        
        //Prompt for login credentials, limit to three attempts
        while (count < 3) {
            
            //Create dialog boxes to get user input
            userName = JOptionPane.showInputDialog("Enter username: ");
            passWord = JOptionPane.showInputDialog("Enter password (close dialog box to exit: ");

            //Statement to account for user canceling out of program (1 of 2)
            if ((userName != null) && (userName.length() > 0)) {
                
                //MD5 hash to convert password to hashed password
                String original = passWord;  //Pull in user password
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(original.getBytes());
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                    for (byte b : digest) {
                        sb.append(String.format("%02x", b & 0xff));
                    }

                //Open credentials file
                fileByteStream = new FileInputStream("credentials file.txt");
                credScnr = new Scanner(fileByteStream).useDelimiter("\t+|\\n");

                //Loop credential file data to store info into arrays
                String[] validData = new String[6];
                for (i = 0; i < 6; ++i) {
                    validData[i] = credScnr.nextLine();
                }

                //Close credentials file
                fileByteStream.close();

                //Loop user name and digest through array to validate user
                for (i = 0; i < 6; i++) {
                    if (validData[i].contains(userName) && validData[i].contains(sb.toString())) {
                        count = 3;

                        //Display role data based on user role
                        if (validData[i].contains("zookeeper")) {
                            Object []buttons = {"Logout", "Login Again"};
                            int j = JOptionPane.showOptionDialog(null, roles[0], 
                                    "Zookeeper Data",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, 
                                    null,
                                    buttons, buttons[0]);
                            if (j == 1) {
                                count = -1;  
                            }
                        }
                        else if (validData[i].contains("admin")) {
                            Object []buttons = {"Logout", "Login Again"};
                            int j = JOptionPane.showOptionDialog(null, roles[1], 
                                    "Admin Data",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, 
                                    null,
                                    buttons, buttons[0]);
                            if (j == 1) {
                                count = -1;  
                            }
                        }
                        else if (validData[i].contains("veterinarian")) {
                            Object []buttons = {"Logout", "Login Again"};
                            int j = JOptionPane.showOptionDialog(null, roles[2], 
                                    "Veterinarian Data",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, 
                                    null,
                                    buttons, buttons[0]);
                            if (j == 1) {
                                count = -1;  
                            }
                        }
                    }
                }
            }
            
            //Statement to account for user canceling out of program (2 of 2)
            else {
                count = 3;
            }
           
            //Increment count if invalid user    
            count++;

            //Display error if three invalid login attempts and exit
            if (count == 3) {
                JOptionPane.showMessageDialog(null, "Invalid user, Exiting program.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}