package com.example.vigenerecipher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    private EditText message;
    private EditText keyword;
    private TextView encryptedMessage;
    private Button encryptButton;
    private String holdingString;
    private String holdingString2;
    private Button decryptButton;
    private TextView encryptedText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        encryptButton = findViewById(R.id.encryptButton);
        message = findViewById(R.id.MessageEN);
        keyword = findViewById(R.id.Keyword);
        encryptedMessage = findViewById(R.id.textEn);
        decryptButton = findViewById(R.id.decryptButton);



        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                holdingString = message.getText().toString();

                holdingString2 = keyword.getText().toString();
                int i = holdingString.length();
                char[] encryptArray ;
                encryptArray  = encrypt(holdingString, holdingString2);
                System.out.println(String.valueOf(encryptArray));
                encryptedMessage.setText( String.valueOf(encryptArray));


                Toast.makeText(getApplicationContext(),    String.valueOf(encryptArray) ,Toast.LENGTH_LONG).show();
            }
        });
        //AT THE MOMENT THESE FUNCTIONS ONLY WORK CORRECTLY IN CAPS LOCK
        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holdingString = message.getText().toString();

                holdingString2 = keyword.getText().toString();



                char [] newArray = new char[holdingString.length()];
                for(int r = 0 ; r < holdingString.length() ; r++)

                {
                    newArray[r] = holdingString.charAt(r);
                }



                char[] decryptArray;
                char[] encryptArray  = encrypt(holdingString, holdingString2);// this is where I need to remove the call to encryptArray and change the parameter type in decrypt to String or ...
                //convert the String to a char array
                decryptArray  = decrypt(encryptArray, holdingString2); // fixed ...

                encryptedMessage.setText( String.valueOf(decryptArray));


                Toast.makeText(getApplicationContext(),    String.valueOf(decryptArray) ,Toast.LENGTH_LONG).show();

            }
        });


    }

    public static char[] encrypt(String plaintext, String keyword)
    {
        //Converting plaintext to char array



        System.out.println(plaintext);

        char msg;

        String textPlain = new String("");
        String textPlain2 = new String("");
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(plaintext);
        boolean isStringContainsSpecialCharacter = matcher.find();
        boolean setterForLength = false;
        for(int j = 0 ; j< plaintext.length() ; ++j){
            if(checkIfWhiteSpace(plaintext, j)) {
                setterForLength = true;
                textPlain = plaintext.replaceAll("\\s", "");
            }
            else if(isStringContainsSpecialCharacter){
                textPlain = plaintext.replaceAll("[^a-zA-Z]", "") ;
                setterForLength = true;
            }

        }
        int b;
        char msgArray [];
        if(setterForLength){
            b = textPlain.length();
            msgArray  = new char[b ];

            for(int i = 0 ; i < textPlain.length(); ++i)  {


                if(checkIfLowerCase(textPlain, i))  {
                    msg = checkAndConvertToUpperCase(textPlain.charAt(i));
                    System.out.println(msg+ "this is the line for debugging######2");
                    msgArray[i]  = msg;

                }
                else {
                    msg = textPlain.charAt(i) ;
                    msgArray[i]  = msg;
                    System.out.println(msg+ "this is the line for debugging###########3");
                }


            }

        }
        else{

            b = plaintext.length();
            msgArray  = new char[b ];

            for(int i = 0 ; i < plaintext.length(); ++i)  {


                if(checkIfLowerCase(plaintext, i))  {
                    msg = checkAndConvertToUpperCase(plaintext.charAt(i));
                    System.out.println(msg+ "this is the line for debugging######2");
                    msgArray[i]  = msg;

                }
                else {
                    msg = plaintext.charAt(i) ;
                    msgArray[i]  = msg;
                    System.out.println(msg+ "this is the line for debugging###########3");
                }


            }





        }

        int i,j;
        String newString = "";
        String secondstring = "";
        boolean newBoolean = false;

        // Creating new char arrays
        char key[] = new char[b];
        char encryptedMsg[] = new char[b];
        for(int p = 0 ; p< keyword.length() ; ++p){
            if(checkIfWhiteSpace(keyword, p) ||isStringContainsSpecialCharacter ) {
                newString =   keyword.replaceAll("\\s", "");
                newString=  keyword.replaceAll("[^a-zA-Z]", "") ;

                newBoolean = true;

            }




        }
        char cc;
        for(i = 0, j = 0; i < b; ++i, ++j)
        {
            if(j == keyword.length())
            {
                j = 0;
            }
            if(checkIfLowerCase(keyword, j))  {

                cc  = checkAndConvertToUpperCase(keyword.charAt(j));
                System.out.println(cc+ "this is the line for debugging######22323");
                key[i]  = cc;

            }






            else if(newBoolean) {

                if(j == newString.length())
                {
                    j = 0;
                }
                if(checkIfLowerCase(newString, j))  {

                    cc  = checkAndConvertToUpperCase(newString.charAt(j));
                    System.out.println(cc+ "this is the line for debugging######232");
                    key[i]  = cc;

                } else{
                    key[i] = newString.charAt(j);}
                System.out.println(key[i]+ "this is the line for debugging######211");



            }



            else{
                System.out.println(keyword.charAt(j)+ "this is the line for debugging######7777");

                key[i] = keyword.charAt(j); }
        }
        System.out.println(String.valueOf(key)+ "this is the line for debugging######4");
        System.out.println(String.valueOf(msgArray)+ "\nthis is the line for debugging######4");
        System.out.println(b+ "\nthis is the line for debugging######4");


        //encryption code
        for(i = 0; i < b; ++i)
            encryptedMsg[i] = (char) (((msgArray[i] + key[i]) % 26) + 'A');
        System.out.println(encryptedMsg);
        return encryptedMsg;
    }




    public static char[] decrypt(char [] encryptedMsg, String keyword)
    {
        //Converting plaintext to char array
        String plaintext = new String(encryptedMsg);
        System.out.println(plaintext);
        char msg;

        int msgLen = plaintext.length();


        // Creating new char arrays
        char key[] = new char[msgLen];
        char decryptedMsg[] = new char[msgLen];
        String textPlain = new String("");
        String textPlain2 = new String("");
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(plaintext);
        boolean isStringContainsSpecialCharacter = matcher.find();
        boolean setterForLength = false;
        for(int j = 0 ; j< plaintext.length() ; ++j){
            if(checkIfWhiteSpace(plaintext, j)) {
                setterForLength = true;
                textPlain = plaintext.replaceAll("\\s", "");
            } else if(isStringContainsSpecialCharacter){
                textPlain = plaintext.replaceAll("[^a-zA-Z]", "") ;
                setterForLength = true;
            }

        }
        int b;
        char msgArray [];
        if(setterForLength){
            b = textPlain.length();
            msgArray  = new char[b ];

            for(int i = 0 ; i < textPlain.length(); ++i)  {


                if(checkIfLowerCase(textPlain, i))  {
                    msg = checkAndConvertToUpperCase(textPlain.charAt(i));
                    System.out.println(msg+ "this is the line for debugging######2");
                    msgArray[i]  = msg;

                }
                else {
                    msg = textPlain.charAt(i) ;
                    msgArray[i]  = msg;
                    System.out.println(msg+ "this is the line for debugging###########3");
                }


            }

        }
        else{

            b = plaintext.length();
            msgArray  = new char[b ];

            for(int i = 0 ; i < plaintext.length(); ++i)  {


                if(checkIfLowerCase(plaintext, i))  {
                    msg = checkAndConvertToUpperCase(plaintext.charAt(i));
                    System.out.println(msg+ "this is the line for debugging######2");
                    msgArray[i]  = msg;

                }
                else {
                    msg = plaintext.charAt(i) ;
                    msgArray[i]  = msg;
                    System.out.println(msg+ "this is the line for debugging###########3");
                }


            }





        }

        int i,j;
        String newString = "";
        String secondstring = "";
        boolean newBoolean = false;

        // Creating new char arrays

        for(int p = 0 ; p< keyword.length() ; ++p){
            if(checkIfWhiteSpace(keyword, p) ||isStringContainsSpecialCharacter ) {
                newString =   keyword.replaceAll("\\s", "");
                newString=  keyword.replaceAll("[^a-zA-Z]", "") ;

                newBoolean = true;

            }




        }
        char cc;
        for(i = 0, j = 0; i < b; ++i, ++j)
        {
            if(j == keyword.length())
            {
                j = 0;
            }
            if(checkIfLowerCase(keyword, j))  {

                cc  = checkAndConvertToUpperCase(keyword.charAt(j));
                System.out.println(cc+ "this is the line for debugging######22323");
                key[i]  = cc;

            }






            else if(newBoolean) {

                if(j == newString.length())
                {
                    j = 0;
                }
                if(checkIfLowerCase(newString, j))  {

                    cc  = checkAndConvertToUpperCase(newString.charAt(j));
                    System.out.println(cc+ "this is the line for debugging######232");
                    key[i]  = cc;

                } else{
                    key[i] = newString.charAt(j);}
                System.out.println(key[i]+ "this is the line for debugging######211");



            }



            else{
                System.out.println(keyword.charAt(j)+ "this is the line for debugging######7777");

                key[i] = keyword.charAt(j); }
        }
        System.out.println(String.valueOf(key)+ "this is the line for debugging######4");
        System.out.println(String.valueOf(msgArray)+ "\nthis is the line for debugging######4");
        System.out.println(b+ "\nthis is the line for debugging######4");


        //encryption code
        for(i = 0; i < b; ++i)
            decryptedMsg[i] = (char) ( (( (encryptedMsg[i] - key[i])  +26) % 26) + 'A');
        System.out.println(encryptedMsg);
        return decryptedMsg;
    }







    public static char checkAndConvertToUpperCase(char a)  {
        char ch;
        int temp;
        temp = (int) a;
        if(temp>= 97 && temp<= 122) {
            temp = temp - 32;
            ch = (char) temp;
            return ch;
        }
        else{
            return a;
        }
    }
    public static char checkAndConvertToLowerCase(char a ) {
        char ch;
        int temp;
        temp = (int) a;

        if(temp>=65 && temp<=90) {

            temp = temp - 32;
            ch = (char) temp;
            return ch;
        }
        else{
            return a;
        }
    }
    public static boolean checkIfWhiteSpace(String a, int i) {
        int l = a.codePointAt(i);
        if(l==32) {
            return true;
        }
        else{
            return false;
        }
    }
    public static char removeWhiteSpace(char a ) {
        char ch;
        int temp;
        temp = (int) a;

        if(temp>=0 && temp<=32) {

            temp = 0;
            ch = (char) temp;
            return ch;
        }
        else{
            temp = 0;
            ch = (char) temp;
            return ch;

        }
    }
    public static boolean checkIfLowerCase(String a, int i ) {
        int l = a.codePointAt(i) ;
        if(l>= 97 && l<=122)   {
            return true;
        }
        else{
            return false;
        }
    }

}