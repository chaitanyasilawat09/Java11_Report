package test;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TestSubString {

    public static void main(String[] args) throws InterruptedException {
//        var driver = new ChromeDriver();
//        driver.get("https://jira.shl.systems/browse/SD-144911");
//        Thread.sleep(10000);
//        print_Common_SubString_from_Given_array();
//        check_no_palindrom_or_Not();
        find_small_and_larg_no_given_Array();
    }

    private static void find_small_and_larg_no_given_Array() {

    int [] arr = {43,43,5,7,5,12,3,4,5,6,8776,5,77,8,987,1};
        ArrayList<Integer> list = (ArrayList<Integer>) Arrays.stream(arr).boxed().collect(Collectors.toList());

        int min =list.get(0);
        int max =list.get(1);
        for (int i : list){
            if (max<i){
                min = max;
                max = i;
            }
         else {
                if (min>i){
                    min = i;
                }
            }
        }
        System.out.println(min);
        System.out.println(max);

    }

    private static void check_no_palindrom_or_Not() {


        int pal = 123454321;
//        String pal = "abcdecba";
        char[] ch = String.valueOf(pal).toCharArray();
        int count =0;
        int i = 0;
//        int len = ch.length/2;
        int arrLength = ch.length;
        for ( i = 0; i<arrLength/2 ; i++){

            if(ch[i] == ch[ch.length-i-1])
                count++;
            else {
                System.out.println("Not plndrm");
                break;
            }
        }

        System.out.println("count " + count);
        System.out.println("i " + i);



//        int no = 235478;
//        int reverse = 0;
//        while(no!=0){
//
//            int reminder = no%10;
//            reverse = reverse * 10 +reminder;
//            no = no/10;
//
//        }
//        System.out.println(reverse);
    }

    public static void print_Common_SubString_from_Given_array() {
//        String [] arr = {"apply","supply","ply","butterply","Multiply"};
        String [] arr = {"men","women","comen","menfor","tenmensen"};
        int mainLength = arr.length;
        Arrays.sort(arr,Comparator.comparing(String::length));
        String first = arr[0];
        String repString = "";
        int len = first.length();
        for(int i =0; i< len; i++){
            for (int j =i+1; j<=len; j++){
                String subStting = first.substring(i, j);
                int k =1;
                for (k =1; k<mainLength; k++){
                    if (!arr[k].contains(subStting))
                        break;
                }
                if (k==mainLength && repString.length()<subStting.length())
                    repString = subStting;
            }
        }
        System.out.println(repString);
    }
}
