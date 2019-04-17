public class Test {
    public static void main(String[] args) {
        String testString = "123,l;l";



        boolean valid;
        valid = validateLine(testString);

        System.out.println(valid);





        //System.out.println(validateString(testString));

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
