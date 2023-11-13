
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
//**************************************************************



    public static JUMPER[] jump=new JUMPER[20000];

    public static String rl="";// уникальная последовательность переходов
    public static int[] reloca = new int[100];// массив - уникальная последовательность переходов
    //   public static int[] reloca2 = new int[100];// массив - уникальная последовательность переходов
    public static RC TR=new RC();

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
    static void cls_rgr(int [][] e)
    {
        for (int i=e.length-1;i>=0;i--)
        {
            for (int j=0;j<=e[i].length-1;j++)
            {
                e[i][j]=0;
            }

        }
    }



    static int dehex(String HEX)//из двоичной в десятичную
    { return Integer.parseInt(HEX,2);}
    static String hex(int dex)// из десятичной в двоичную
    {return Integer.toBinaryString(dex);}

    //***************** из массива в десятичное *********
    static int ACP(boolean[] analog)
    {
        String stroka="";
        for (int i=7; i>=0;i--){
            if (analog[i]==false){stroka+="0";}
            else {stroka+="1";}
        }
        return Integer.parseInt(stroka,2);
    }

    //****************** из десятичного в массив ************
    static boolean[] CAP(int dex){
        boolean[] analog=new  boolean[8];
        String hex=Integer.toBinaryString(dex);
        for (int i=7; i>=0;i--){
            if (hex.charAt(i)=='1'){analog[i]=true;}}

        return analog ;
    }
    //********** фуська вывода на экран массива направлений (для отладки) *******
    static void tablo(boolean[] analog){
        for (int i=7; i>=0;i--){
            System.out.println(i+" == "+analog[i]);
        }
        System.out.println("");
        if (analog[7])System.out.print(".*.");
        else System.out.print(".◦.");
        if (analog[0])System.out.println("*.");
        else System.out.println("◦.");
        if (analog[6])System.out.print("*...");
        else System.out.print("◦...");
        if (analog[1])System.out.println("*");
        else System.out.println("◦");
        System.out.println("..@..");
        if (analog[5])System.out.print("*...");
        else System.out.println("◦...");
        if (analog[2])System.out.println("*");
        else System.out.println("◦");
        if (analog[4])System.out.print(".*.");
        else System.out.print(".◦.");
        if (analog[3])System.out.println("*.");
        else System.out.println("◦.");



    }


    static int GEN_ID(int a,int b,int numb)// **** генератор уникального id *****
    {
        int id=a+(b*100)+(numb*10000);//xy_x1y1_номер записи
        return id;
    }

    static String SHAGI(boolean[] ss)
    {String shag="";
        for (int ii=7; ii>=0;ii--){
            if (ss[ii]==false){shag+="0";}
            else {shag+="1";}
        }
        return shag;}

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
        //ir+=1;
        reloc+=(char)pos;
        actual[x][y]=HOD;
        reloca[HOD]=pos;

        //if (HOD==12){relocs[2]=reloc ;System.out.println("x="+x+" y="+y+" "+pos+"  "+(char)pos);}

        //System.out.println("x= "+x+"   y= "+y+"   "+pos);

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
       /* int m =9; ///размерность массива
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++) {

                int r=(i-1)*m+j;
                System.out.print("[" + nn(r)+"]");
            }
            System.out.println();
        }
        /*ekran(actual);
        actual[1][2]=77;
        ekran(actual);

        shag[7]=true;
        shag[6]=true;
        shag[5]=true;
        shag[4]=true;
        shag[3]=true;
        shag[2]=true;
        shag[1]=true;
        shag[0]=true;
        System.out.println (dehex("00000011"));
        System.out.println (hex(2));
        System.out.println (SHAGI(shag));
        System.out.println (dehex(SHAGI(shag)));*/

        System.out.println("x=" + x + "   y=" + y);
        for (int i = 0; i <= MAXIMUM; i++) {RGR();
            if (komplekt==true)break;}

/*        int x7,y7,pos7;
        pos7=1;
        x7=pos7;
        y7=pos7;
        for (int i=1;i<=25;i++) {
            pos7=i;
            y7=(pos7-1)/5+1;
            x7=pos7-((y7-1)*5);
            System.out.println("поле==>" + pos7 + "  x=" + x7 + "  y=" + y7 );
        }*/
        //*************  отчёт  **********
        komplekt=false;
        System.out.println("точка входа "+startpos);
        System.out.println("проверено "+ir+" комбинаций");
        System.out.println("собрано "+result+" порядков");
        long timeWorkCode = System.currentTimeMillis() - start;
        System.out.println("затрачено времени=>  " + time_format_rgr(timeWorkCode));
        System.out.println("---= T H E    E N D =---");
    }




/*        String tst11="1p3956789";
        System.out.println("===="+tst11.charAt(1));
        if (tst11.charAt(3)=='9')System.out.println("=== TEST OK ===");*/

/*        shag[7]=false;
        shag[6]=true;
        shag[5]=true;
        shag[4]=false;
        shag[3]=true;
        shag[2]=true;
        shag[1]=true;
        shag[0]=true;

        tablo(shag);*/
/*        String ok="";
    for (int ik=0;ik<110;ik++){
        System.out.println(ik+" ==> "+(char)ik);
        ok+=(char)ik;
    }
    System.out.println(ok);
        for (int ik=0;ik<110;ik++){
            System.out.println(ik+" ==> "+(int) ok.charAt(ik));
            //ok+=(char)ik;
        }*/

}

class RC{ // ************* КЛЮЧЕВАЯ ТОЧКА *********
    int ID; // хз, надо или нет?
    boolean status; // актуальна или нет ключевая точка
    int NX,lastx,lasty;// номер хода//предыдущие координаты
    int fd,bd,ud; // свободные//занятые//провереные направления ♞
    int ROAD [][][];// путь к КТ - [номер хода], [x], [y]

    void display(){System.out.printf("\nключевая точка %d\n" +
            "статус=> %b \nномер хода=> %d",ID,status,NX);}

}

class JUMPER{// ********** ТУПИКОВЫЕ ПЕРЕХОДЫ ***********
    int numb;// номер хода
    int a,b;// точки входа--> выхода
    boolean status; //если true, то не тупик
    int ID;


}