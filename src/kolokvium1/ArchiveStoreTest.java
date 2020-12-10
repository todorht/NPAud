package kolokvium1;

import java.text.SimpleDateFormat;
import java.util.*;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

// вашиот код овде


class ArchiveStore {

    List<Archive> archiveList;
    StringBuilder log;

    public ArchiveStore() {
        this.archiveList = new ArrayList<>();
        this.log = new StringBuilder();
    }

    void archiveItem(Archive item, Date date){
        item.setDateArchived(date);
        archiveList.add(item);

        log.append(String.format("Item %d archived at %s\n", item.id, date));
    }

    void openItem(int id, Date date) throws NonExistingItemException {
        Archive archive = archiveList.stream().filter(d -> d.id == id).findFirst().orElseThrow(()-> new NonExistingItemException(id));
        try{
            LockedArchive lockedArchive = (LockedArchive) archive;
            if(lockedArchive.dateToOpen.compareTo(date) > 0){
                log.append(String.format("Item %d cannot be opened before %s\n", id, lockedArchive.dateToOpen));
            }
            else{
                log.append(String.format("Item %d opened at %s\n", id, date));
            }
        }
        catch (Exception e){
            SpecialArchive specialArchive = (SpecialArchive) archive;
            if(specialArchive.opened >= specialArchive.maxOpen){
                log.append(String.format("Item %d cannot be opened more than %s times\n", id, specialArchive.maxOpen));
            }
            else{
                log.append(String.format("Item %d opened at %s\n", id, date));
                specialArchive.opened++;
            }
        }


    }

    String getLog(){
        return log.toString();
    }
}

class Archive {
    int id;
    Date dateArchived;

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }

    public Archive(int id) {
        this.id = id;
        dateArchived = null;
    }
}

class LockedArchive extends Archive {
    Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen){
        super(id);
        this.dateToOpen = dateToOpen;
    }
}

class SpecialArchive extends Archive {
    int maxOpen;
    int opened;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
    }
}

class NonExistingItemException extends Exception{
    public NonExistingItemException(int id) {
        super(String.format("Item with id %d doesn't exist.", id));
    }
}