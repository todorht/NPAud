package mk.ukim.finki.aud8;

import java.util.*;

class DuplicateNumberException extends Exception{
    String number;

    public DuplicateNumberException(String number){
        super(String.format("Duplicate number: %s", number));
    }


}

class Contact implements Comparable<Contact>{
    String name;
    String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, number);
    }

    @Override
    public int compareTo(Contact o) {
        int res = this.name.compareTo(o.name);
        return res == 0 ? this.number.compareTo(o.number) : res;
    }

    List<String> getSubNumbers(){
        List<String> result = new ArrayList<>();

        for (int i = 3; i<=this.number.length();i++){
            for (int j = 0; j<=this.number.length()-i;j++){
                result.add(number.substring(j,j+i));
            }
        }
        return result;
    }
}

class PhoneBook{
    Map<String, String> nameByPhoneNumber;
    Map<String, Set<Contact>> contactBySubnumber;
    Map<String, Set<Contact>> contactByName;

    PhoneBook(){
        this.nameByPhoneNumber = new HashMap<>();
        this.contactBySubnumber = new HashMap<>();
        this.contactByName = new HashMap<>();
    }


    public void addContact(String name, String number) throws DuplicateNumberException {
        if(nameByPhoneNumber.containsKey(number)) throw new DuplicateNumberException(number);

        Contact c = new Contact(name, number);

        nameByPhoneNumber.put(number,name);

        contactByName.putIfAbsent(name, new TreeSet<>());
        contactByName.get(name).add(new Contact(name,number));

        for(String subNumber : c.getSubNumbers()){
            contactBySubnumber.putIfAbsent(subNumber, new TreeSet<>());
            contactBySubnumber.get(subNumber).add(c);
        }


    }

    public void contactsByNumber(String number) {
        if(contactBySubnumber.containsKey(number)){
            contactBySubnumber.get(number).forEach(System.out::println);
        }else System.out.println("NOT FOUND");
    }

    public void contactsByName(String name) {
        if(contactByName.containsKey(name)){
            contactByName.get(name).forEach(System.out::println);
        }else System.out.println("NOT FOUND");
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде

