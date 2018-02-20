//==================================//
// AwtToSwing                       //
// Translate Awt code to Swing code //
// Author  : Loan Alouache          //
// Date    : 14 / 02 / 2018         //
// Version : 1.0                    //
//==================================//



import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AwtToSwing
{

    private static String[] tabAwt;
    private static String[] tabSwing;

    private static String sourceClassName;
    private static String destClassName;



    public static void main(String[] args)
    {
        //Parameters test
        if (args.length != 2)
        {
            System.out.println("USAGE : java AwtToSwing FICHIER_SOURCE FICHIER_DESTINATION");
            System.exit(1);
        }

        if (args[0].equals(args[1]))
        {
            System.out.println("ERREUR : Le fichier de destination doit avoir un nom différent du fichier source");
            System.exit(1);
        }



        //Datas
        final String fSource = args[0];
        final String fDest   = args[1];


        //Class name definition
        AwtToSwing.sourceClassName = fSource.split("\\.")[0];
        AwtToSwing.destClassName   = fDest.split("\\.")[0];


        String line;
        Scanner sc;
        FileWriter fw;
        PrintWriter pw;


        //Instructions
        AwtToSwing.tabAwt   = readData(  "awt.data"  );
        AwtToSwing.tabSwing = readData( "swing.data" );


        try
        {
            sc = new Scanner(new FileReader(fSource));
            fw = new FileWriter(fDest, true);
            pw = new PrintWriter(fw);

            System.out.println("© Créé par Loan Alouache\n");
            System.out.println("~ Début de la conversion...");

            while (sc.hasNext())
            {
                line = sc.nextLine();
                line = AwtToSwing.changeClassName(line);
                line = AwtToSwing.convert(line);
                pw.write(line + "\n");
            }

            fw.close();
            sc.close();


            System.out.println("~ Conversion réalisée avec succès !");
            System.out.println("(NB : Pensez à importer manuellement Swing)\n\timport javax.swing.*;");

        }
        catch (Exception e) { e.printStackTrace(); }
    }







    //============================================================================================//
    // CONVERT CURRENT LINE FROM AWT TO SWING                                                     //
    //============================================================================================//
    private static String convert(String lineSource)
    {
        String s = lineSource;

        for (int i = 0; i < AwtToSwing.tabAwt.length; i++)
            if (s.contains(AwtToSwing.tabAwt[i]))
            {
                char charBefore = '`';
                char charAfter  = '`';

                String toReplace = AwtToSwing.tabAwt[i];


                //Finding characters around
                if (s.indexOf(toReplace) > 0)
                    charBefore = s.charAt(s.indexOf(toReplace) - 1);

                if (s.indexOf(toReplace) + toReplace.length() < s.length())
                    charAfter = s.charAt(s.indexOf(toReplace) + toReplace.length());


                //If toReplace is not part of something else
                if (!AwtToSwing.isLetter(charBefore) && !AwtToSwing.isLetter(charAfter))
                    s = s.replace(AwtToSwing.tabAwt[i], AwtToSwing.tabSwing[i]);
            }

        return s;
    }



    //============================================================================================//
    // CHANGE CLASS NAME                                                                          //
    //============================================================================================//
    private static String changeClassName(String lineSource)
    {
        String s = lineSource;

        for (int i = 0; i < AwtToSwing.tabAwt.length; i++)
            if (s.contains(AwtToSwing.sourceClassName))
            {
                char charBefore = '`';
                char charAfter  = '`';

                String toReplace = AwtToSwing.sourceClassName;


                //Finding characters around
                if (s.indexOf(toReplace) > 0)
                    charBefore = s.charAt(s.indexOf(toReplace) - 1);

                if (s.indexOf(toReplace) + toReplace.length() < s.length())
                    charAfter = s.charAt(s.indexOf(toReplace) + toReplace.length());


                //If toReplace is not part of something else
                if (!AwtToSwing.isLetter(charBefore) && !AwtToSwing.isLetter(charAfter))
                    s = s.replace(AwtToSwing.sourceClassName, AwtToSwing.destClassName);
            }

        return s;
    }

    //============================================================================================//
    // READ AWT / SWING DATAS FROM FILE                                                           //
    //============================================================================================//
    private static String[] readData(String source)
    {
        String[] tab;
        int      nbLines, indLine;
        Scanner  sc;

        try
        {
            //Line counting
            sc = new Scanner(new FileReader(source));
            nbLines = 0;
            while (sc.hasNext())
            {
                sc.nextLine();
                nbLines++;
            }

            tab = new String[nbLines];

            //Array filling
            sc = new Scanner(new FileReader(source));
            indLine = 0;
            while (sc.hasNext())
                tab[indLine++] = sc.nextLine();

            return tab;
        }
        catch (Exception e) { e.printStackTrace(); }

        return null;
    }



    //============================================================================================//
    // CHECK IF CHARACTER IS LETTER                                                               //
    //============================================================================================//
    private static boolean isLetter(char c)
    {
        for (char i = 'A'; i <= 'Z'; i++)
            if (Character.toUpperCase(c) == i) return true;

        for (char i = '0'; i <= '9'; i++)
            if (Character.toUpperCase(c) == i) return true;

        return false;
    }
}
