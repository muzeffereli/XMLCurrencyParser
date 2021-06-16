package com.company.xml_currency_parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Valute> valutesOfBank = new ArrayList<>();
    private static List<Valute> valutesOfCur = new ArrayList<>();
    private static List<Valute> valutes = new ArrayList<>();

    public static void main(String[] args) throws JAXBException, MalformedURLException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String date = new SimpleDateFormat("dd.MM.yyyy")
                .format(Calendar.getInstance()
                        .getTime());

        String url = "https://www.cbar.az/currencies/" + date + ".xml";
        ValCurs v = (ValCurs) unmarshaller.unmarshal(new URL(url));

        valutesOfCur.add(
                new Valute("AZN", "1", "1 Azerbaycan Manati", 1.0));

        for (int i = 0; i < v.getValTypes().size(); i++) {
            if(i==0) {
                valutesOfBank.addAll(
                        v.getValTypes().get(i)
                                .getValutes());
            }else{
                valutesOfCur.addAll(
                        v.getValTypes().get(i)
                                .getValutes());
            }


        }
        for(Valute v1:valutesOfCur){
            if(v1.getNominal().equals("100")){
                v1.setValue(v1.getValue()/100);
            }
        }

        valutes.addAll(valutesOfBank);
        valutes.addAll(valutesOfCur);

        Scanner scan=new Scanner(System.in);
       beginLoop:
       while(true){
           while(true){
               System.out.println("Which currencies do you want to see?");
               System.out.println("1."+v.getValTypes().get(0).getType());
               System.out.println("2."+v.getValTypes().get(1).getType());
               System.out.println("3.All currencies.");
               System.out.println("4.Exit.");

               System.out.print("Enter command:");

               int cmd=scan.nextInt();
               if(cmd==1){
                   for (Valute valute : valutesOfBank)
                       System.out.println(valute);
                   break;
               }else if(cmd==2){
                   for (Valute valute : valutesOfCur)
                       System.out.println(valute);
                   break;
               }else if(cmd==3){
                   for (Valute valute : valutes)
                       System.out.println(valute);
                   break;
               } else if (cmd == 4) {
                   System.out.println("Exiting program...");
                   break beginLoop;
               } else {
                   System.out.println("There is no such a command.Try again.");
               }

           }

           main:
           while(true){
               run(valutes);
               while(true){
                   System.out.print("Do you want to return main menu?(yes,no):");
                   String ans=scan.next();
                   if(ans.equalsIgnoreCase("yes")){
                       System.out.println("Returning menu...\n");
                       break main;
                   }else if(ans.equalsIgnoreCase(("no"))){
                       continue main;
                   }else{
                       System.out.println("There is no such a command.Try again.");
                   }
               }
           }
       }

    }

    public static Valute valuteExist(List<Valute> valutes, String code) {
        Valute val = null;
        for (Valute valute :
                valutes)
            if (valute.getCode().equalsIgnoreCase(code)) {
                val = valute;
                break;
            }
        return val;
    }

    public static void run(List<Valute> v) {
        Scanner in = new Scanner(System.in);

        System.out.print("FROM (code): ");
        String codeFrom = in.next();
        String codeTo;

        Valute fromVal = valuteExist(v, codeFrom);

        if (fromVal == null)
            System.out.println("Incorrect valute code !");
        else {
            while(true) {
                System.out.print("TO (code): ");
                codeTo = in.next();

                Valute toVal = valuteExist(v, codeTo);
                if (toVal == null) {
                    System.out.println("Incorrect valute code !");
                    continue;
                }
                else {
                    System.out.print("Enter amount of " + fromVal.getCode() + ": ");
                    double amount = in.nextDouble();


                    DecimalFormat df = new DecimalFormat("0.00");
                    String result = df.format(
                            (fromVal.getValue() * amount)
                                    / toVal.getValue());

                    System.out.println(amount + " " + fromVal.getCode() + " = "
                            + result + " " + toVal.getCode() + "\n");
                    break;
                }
            }


        }
    }
}
