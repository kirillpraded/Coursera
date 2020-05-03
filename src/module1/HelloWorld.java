package module1;

public class HelloWorld {
    public static void selectionSort(){
        int[] arraylist = {2,6,43,66,12};
        int small = arraylist[0];
        for (int i = 0; i < arraylist.length-1; i++){
            if(arraylist[i]<small){small = arraylist[i];}
            int template = small;
            small = arraylist[i];
            arraylist [i] = template;
        }
        for (int i : arraylist){System.out.println(i);}
    }

    public static void main(String[] args){

        selectionSort();
    }
}