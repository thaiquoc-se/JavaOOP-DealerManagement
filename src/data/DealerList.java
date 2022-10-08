/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import tools.MyTool;

/**
 *
 * @author Thai
 */
public class DealerList extends ArrayList<Dealer> {

    private static final String PHONEPATTERN = "\\d{9}|\\d{11}";
    private String dataFile = "";
    boolean changed = false;

    public DealerList() {
        initWithFile();
        loadDealerFromFile();
    }

    public void initWithFile() {
        Config cR = new Config();
        dataFile = cR.getDealerFile();
    }

    public void loadDealerFromFile() {
        try {
            FileReader fr = new FileReader(dataFile);
            BufferedReader bf = new BufferedReader(fr);
            String details;
            while ((details = bf.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(details, ",");
                String ID = stk.nextToken();
                String name = stk.nextToken();
                String address = stk.nextToken();
                String phone = stk.nextToken();
                boolean continuing = Boolean.parseBoolean(stk.nextToken());
                this.add(new Dealer(ID, name, address, phone, continuing));
            }
            bf.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    ///////////////////
    public void addDealer() {
        String ID;
        int index;
        do {
            ID = MyTool.regexString("ID of new dealer(Dxxx): ", "Wrong.Input again.", "^[D]\\d{3}$");
            index = checkID(ID);
            if (index >= 0) {
                System.out.println("ID is duplicated.Input again.");
            }
        } while (index >= 0);
        String name = MyTool.getString("Name of new dealer: ", "Not blank or empty.");
        String address = MyTool.getString("Address of new dealer: ", "Not blank or empty.");
        String phone = MyTool.regexString("Phone number: ", "Phone is 9 or 11 digit.", PHONEPATTERN);
        boolean continuing = true;
        this.add(new Dealer(ID, name, address, phone, continuing));
        System.out.println("New dealer has been added.");
        changed = true;
    }

    public int checkID(String id) {
        if (this.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    public void searchDealer() {
        String ID = MyTool.getString("Enter ID dealer to search: ", "Not blank or empty.");
        int index = checkID(ID);
        if (index >= 0) {
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |CONTINUING|");
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            this.get(index).showInfor();
            System.out.println("+----------+----------+--------------------+---------------+----------+");
        } else {
            System.out.println("Dealer " + ID + " not found!");
        }
    }

    public void removeDealer() {
        String ID = MyTool.getString("Enter ID dealer to remove: ", "Not blank or empty.");
        int index = checkID(ID);
        if (index >= 0) {
            this.get(index).setContinuing(false);
            System.out.println("Successfully.");
            changed = true;
        } else {
            System.out.println("Dealer " + ID + " not found!");
        }
    }

    public void updateDealer() {
        String ID = MyTool.getString("Enter ID dealer to update: ", "Not blank or empty.");
        int index = checkID(ID);
        if (index >= 0) {
            System.out.println("new name, ENTER for omitting: ");
            String newName = MyTool.sc.nextLine().trim();
            if (!newName.isEmpty()) {
                this.get(index).setName(newName);
                changed = true;
            }
            System.out.println("new address, ENTER for omitting: ");
            String newAddress = MyTool.sc.nextLine().trim();
            if (!newAddress.isEmpty()) {
                this.get(index).setAddr(newAddress);
                changed = true;
            }
            System.out.println("new phone, ENTER for omitting: ");
            String newPhone = MyTool.sc.nextLine().trim();
            if (!newPhone.isEmpty()) {
                if (newPhone.matches(PHONEPATTERN)) {
                    this.get(index).setPhone(newPhone);
                    changed = true;
                } else {
                    System.out.println("Wrong fommat phone.");
                    newPhone = MyTool.regexString("new phone,ENTER for omitting: ", "Wrong format phone.", PHONEPATTERN);
                    this.get(index).setPhone(newPhone);
                    changed = true;
                }
            }
            if (changed == true) {
                System.out.println("The dealer's information has been updated.");
            } else {
                System.out.println("The dealer's information not changed.");
            }

        } else {
            System.out.println("Dealer " + ID + " not found!");
        }
    }

    public void printAllDealers() {
        if (this.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
            Collections.sort(this);
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |CONTINUING|");
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            for (int i = 0; i < this.size(); i++) {
                this.get(i).showInfor();
            }
            System.out.println("+----------+----------+--------------------+---------------+----------+");
        }
    }

    public void printContinuingDealers() {
        if (this.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |CONTINUING|");
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).isContinuing() == true) {
                    this.get(i).showInfor();
                }
            }
            System.out.println("+----------+----------+--------------------+---------------+----------+");
        }
    }

    public void printUnContinuingDealers() {
        if (this.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |CONTINUING|");
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).isContinuing() == false) {
                    this.get(i).showInfor();
                }
            }
            System.out.println("+----------+----------+--------------------+---------------+----------+");
        }
    }

    public void writeDealerToFile() {
        if (changed) {
            try {
                File f = new File(dataFile);
                FileWriter fw = new FileWriter(f);
                PrintWriter pw = new PrintWriter(fw);
                for (int i = 0; i < this.size(); i++) {
                    pw.println(this.get(i).toString());
                }
                pw.close();
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            changed = false;
            System.out.println("Save to file sucessfully.");
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

}
