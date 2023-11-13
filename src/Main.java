import java.util.*;// для работы с временем

public class Main {

    /*
⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠿⣿⣿⣿⣿⣿⡿⠟⣻⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣭⣁⡀⠄⠄⣀⣤⡶⠾⠛⠋⠄⢰⣿⣿⣿⣿⣿⣿
⣿⣿⣿⠿⠟⠛⠉⣩⡶⠛⠉⠄⠄⠄⠄⠄⠄⠄⢹⣿⣿⣿⣿⣿
⣿⡿⠟⠛⠓⣢⡾⠋⠄⠄⠄⠄⠄⠄⠄⠄⠐⠓⠄⢻⣿⣿⣿⣿
⣿⣿⡿⠦⣰⠏⠄⠄⠄⠄⠄⠄⠄⢲⣄⡀⠄⠄⠄⠄⢻⣿⣿⣿
⣿⡶⠦⢴⠏⠄⠄⠄⠄⠄⠄⠄⠄⢠⣿⣷⣄⠄⠄⠄⢀⢹⣿⣿
⣿⣶⢦⣿⠄⠄⠄⠄⠄⠄⠄⠄⠄⢸⣿⣿⣿⣿⣶⣄⠋⣸⣿⣿
⣿⣄⡼⠇⠄⠄⠄⠄⠄⠄⠄⠄⠄⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⠟⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠹⣿⣿⣿⣿⣿⣿⣿⣿
⣿⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢀⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣼⣿⣿⣿⣿⣿⣿⣿⣿   */


    public static int[][] actual=new int[6][6];
    public static byte startpos=13; // Т О Ч К А    В Х О Д А
    public static int MAXIMUM=4530000;
    public static int ir=0;// счётчик сохраненных переходов
    public static int[] irs=new int[27];// счётчик сохраненных переходов пошагово
    public static int x,y,HOD=1,result=0;
    public static String[][] relocs=new String[26][5000000];// уникальные последовательности переходов
    public static String reloc="";// уникальная последовательность переходов
    public static int pos; // позиция, на основе х и у || x=1 y=1 pos=1
    public static boolean mayak; // если false, то тупик
    public static boolean komplekt=false;// если true -> варианты закончились, финал
    public static String rl="";// уникальная последовательность переходов
//**************************************************************


    static String time_format_rgr(long milis){
        String T="";
        long sec=milis/1000;
        long minut=(sec)/60;
        long clock=(minut)/60;
        sec=sec%60;
        minut=minut%60;
        clock=clock%60;
        T=clock+" час.  "+minut+" мин.  "+sec+" сек.";
        return T;
    }
    static String nn_rgr(int r){    // добавляет 0 если число менее 10 6=06...5=05...4=04
        String nnn=Integer.toString(r);
        if (r<10)   {     nnn = "0"+nnn;}
        return nnn;
    }
    static void ekran_rgr(int [][] e)
    {
        System.out.println();
        for (int ii=5; ii>=1;ii--)
        {
            for (int jj=1; jj<=5;jj++)
            {
                System.out.print(nn_rgr(e[jj][ii])+" ");

            }
            System.out.println();
        }
    }
    static void cls_rgr(int [][] e) // *******  очистка экрана  *******
    {   for (int i=e.length-1;i>=0;i--) { for (int j=0;j<=e[i].length-1;j++)  { e[i][j]=0;  }  }  }


    static boolean scan(int napr){
        rl="";
        int x1=1,y1=1;
        boolean rz=true;// по умолчанию чистое поле
        if (napr==0){ x1=x+1;y1=y+2;}
        if (napr==1){ x1=x+2;y1=y+1;}
        if (napr==2){ x1=x+2;y1=y-1;}
        if (napr==3){ x1=x+1;y1=y-2;}
        if (napr==4){ x1=x-1;y1=y-2;}
        if (napr==5){ x1=x-2;y1=y-1;}
        if (napr==6){ x1=x-2;y1=y+1;}
        if (napr==7){ x1=x-1;y1=y+2;}

        pos=x1+((y1-1)*5);
        if (x1<1||x1>5||y1<1||y1>5)rz=false;
        else if (actual[x1][y1]>0){rz=false;}
        else {            rl = reloc + (char) pos;
            for (int i = 0; i <= (irs[HOD+1]+1); i++)
                /*     for (int i = 0; i <= (MAXIMUM); i++)*/
            {  if (rl.equals(relocs[HOD+1][i])) rz = false;   }
        }
        return rz;
    }

    static void progress(int napr){ // делаем ход, заполняя поле и меняем х и у
        int x1=1,y1=1;
        boolean rz=true;// по умолчанию чистое поле
        if (napr==0){ x1=x+1;y1=y+2;}
        if (napr==1){ x1=x+2;y1=y+1;}
        if (napr==2){ x1=x+2;y1=y-1;}
        if (napr==3){ x1=x+1;y1=y-2;}
        if (napr==4){ x1=x-1;y1=y-2;}
        if (napr==5){ x1=x-2;y1=y-1;}
        if (napr==6){ x1=x-2;y1=y+1;}
        if (napr==7){ x1=x-1;y1=y+2;}

        x=x1;
        y=y1;
        pos=x+((y-1)*5);// позиция для облегчения идентификации джампов

        HOD++;
        reloc+=(char)pos;
        actual[x][y]=HOD;


    }



    public  static void RGR()
    {
        reloc+=(char)1;
        mayak=true;
        for (int iii=0;iii<=100;iii++) {
            for (int i = 0; i <= 7; i++) {
                if (scan(i)) {
                    //System.out.println(HOD+"   "+pos);
                    progress(i);
                    //System.out.println(HOD-1+"   "+pos);

                    mayak=true;
                    break;
                }
                mayak=false; // ТУПИК
            }
            // цикл перебора направлений окончен
            if (mayak==false){
                break;
            }
        }
        if (HOD>24) {ekran_rgr(actual);System.out.println("             порядок №-"+ir);result++;}
        if (HOD<5) System.out.println(">---->критический>---->  "+ir+"   HOD>"+HOD);
        if (ir==10000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==15000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==20000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==30000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==40000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==50000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==180000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==80000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==100000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==150000) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}
        if (ir==MAXIMUM) {ekran_rgr(actual);System.out.println(">>отметка>> "+ir);}

        if (HOD==1) komplekt=true;
        cls_rgr(actual);
        //ekran_rgr(actual);
        relocs[HOD][irs[HOD]]=reloc;
        ir++;
        irs[HOD]++;
        reloc="";
        y=(startpos-1)/5+1;
        x=startpos-((y-1)*5);
        actual[x][y]=1;
        HOD=1;


    }


    public static void main(String[] args){
        long start = System.currentTimeMillis();
        boolean[] shag = new boolean[8]; //8 направлений
        int[][] morre = new int[10000][10];
        y = (startpos-1)/5+1;
        x = startpos - ((y - 1) * 5);
        actual[x][y] = 1;

        System.out.print("♞ МАГИЯ ЧИСЕЛ! ♞\n \n");
        System.out.println("x=" + x + "   y=" + y);

        for (int i = 0; i <= MAXIMUM; i++) {RGR();
            if (komplekt==true)break;}

        //*************  отчёт  **********
        komplekt=false;
        System.out.println("точка входа "+startpos);
        System.out.println("проверено "+ir+" комбинаций");
        System.out.println("собрано "+result+" порядков");
        long timeWorkCode = System.currentTimeMillis() - start;
        System.out.println("затрачено времени=>  " + time_format_rgr(timeWorkCode));
        System.out.println("---= T H E    E N D =---");
    }


}
