package repoTests;

import model.Account;
import model.Developer;

import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) {
        String testString = "123,l;l";



        boolean valid;
        valid = validateLine(testString);

        System.out.println(valid);


        boolean[] booleans = {true, true, true, true, true};

        boolean multBoolean = true;

        for (boolean b: booleans
             ) {
            multBoolean &= b;
        }

        System.out.println(multBoolean);

        System.out.println(Account.class.getFields().length);




       // System.out.println(Account.class.getDeclaredFields().length);

        Field[] declaredFields = Account.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            System.out.println("DeclaredFields:" + declaredFields[i].getName());
        }

    }

    static boolean validateLine(String line){
        String[] words = line.split(",");
        try{
        return validateId(words[0])&&validateSkill(words[1]);}
        catch (Exception e) {
            return false;
        }
    }

    static boolean validateId(String string){

        try {
            Long.parseLong(string);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    static boolean validateSkill(String string){
        return !string.equals("");
    }



}
