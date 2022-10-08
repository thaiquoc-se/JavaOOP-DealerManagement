/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mng;

import data.Account;
import data.Config;
import data.DealerList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import tools.MyTool;

/**
 *
 * @author Thai
 */
public class Login {
    private static String accFile;
    private static ArrayList<Account> listAcc = new ArrayList();

    public static void setupAccFile() {
        Config cR = new Config();
        accFile = cR.getAccountFile();
        readData();
    }

    public static void readData() {
        try {
            FileReader fr = new FileReader(accFile);
            BufferedReader bf = new BufferedReader(fr);
            String details;
            while ((details = bf.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(details, ",");
                String accName = stk.nextToken();
                String password = stk.nextToken();
                String role = stk.nextToken();
                listAcc.add(new Account(accName, password, role));
            }
            bf.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Account inputAccount() {
        String name = MyTool.getString("Your account name: ", "Not blank or empty.Input again.");
        String password = MyTool.getString("Your password: ", "Not blank or empty.Input again.");
        String role = MyTool.getString("Your role: ", "Not blank or empty.Input again.");
        Account acc = new Account(name, password, role);
        return acc;
    }

    public static boolean checkLogin(Account acc) {
        if (listAcc.isEmpty()) {
            return false;
        }
        for (int i = 0; i < listAcc.size(); i++) {
            if (listAcc.get(i).getAccName().equalsIgnoreCase(acc.getAccName())
                    && listAcc.get(i).getPwd().equals(acc.getPwd())
                    && listAcc.get(i).getRole().equalsIgnoreCase(acc.getRole())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Account acc = inputAccount();
        setupAccFile();
        boolean checkLogin = checkLogin(acc);
        if (checkLogin) {
            if (acc.getRole().equalsIgnoreCase("ACC-1")) {
                DealerList dList = new DealerList();
                int choice;
                Menu menu = new Menu("Managing dealers: ");
                menu.addNewOption("   1-Add new dealer.");
                menu.addNewOption("   2-Search a dealer.");
                menu.addNewOption("   3-Remove a dealer.");
                menu.addNewOption("   4-Update a dealer.");
                menu.addNewOption("   5-Print all dealers.");
                menu.addNewOption("   6-Print continuing dealers.");
                menu.addNewOption("   7-Print Un-continuing dealers.");
                menu.addNewOption("   8-Write to file");
                menu.addNewOption("   9-Others.Exit...");
                do {
                    menu.printMenu();
                    choice = menu.getChoice();
                    switch (choice) {
                        case 1:
                            dList.addDealer();
                            break;
                        case 2:
                            dList.searchDealer();
                            break;
                        case 3:
                            dList.removeDealer();
                            break;
                        case 4:
                            dList.updateDealer();
                            break;
                        case 5:
                            dList.printAllDealers();
                            break;
                        case 6:
                            dList.printContinuingDealers();
                            break;
                        case 7:
                            dList.printUnContinuingDealers();
                            break;
                        case 8:
                            dList.writeDealerToFile();
                            break;
                        case 9:
                            System.out.println("Bye,bye.See you next time.");
                            break;
                    }
                } while (choice != 9);
            } else {
                System.out.println("Developed afterward.");
            }
        } else {
            System.out.println("Your account does not exist in the system.");
        }

    }
}
