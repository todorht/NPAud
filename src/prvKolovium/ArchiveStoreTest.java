//package prvKolovium;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class ArchiveStoreTest {

    public static void main(String[] args) throws ParseException {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113,10,7);
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

class NonExistingItemException extends RuntimeException {
    int id;
    public NonExistingItemException(int id){
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Item with id %d doesn't exist", id);
    }
}

class Archive{
    int id;
    Date dateArchived;

    public Archive(int id, Date dateArchived) {
        this.id = id;
        this.dateArchived = dateArchived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }
}

class LockedArchive extends Archive{

    Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id, null);
        this.dateToOpen = dateToOpen;
    }

    public Date getDateToOpen() {
        return dateToOpen;
    }
}

class SpecialArchive extends Archive{
    int maxOpen;
    int opened;

    public SpecialArchive(int id,int maxOpen) {
        super(id, null);
        this.maxOpen = maxOpen;
        this.opened = 0;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    public void setMaxOpen(int maxOpen) {
        this.maxOpen = maxOpen;
    }

    public int getOpened() {
        return opened;
    }

    public void setOpened(int opened) {
        this.opened = opened;
    }
}

class ArchiveStore{
    List<Archive> archives;
    StringBuilder log;

    public ArchiveStore() {
        this.archives=new ArrayList<>();
        this.log = new StringBuilder();
    }

    public void archiveItem(Archive archive, Date date){
        archive.setDateArchived(date);
        archives.add(archive);
        log.append(String.format("Item %d archived at %s\n", archive.getId(), date.toString()));
    }

    public void openItem(int id, Date date){
        Archive archive = archives.stream().filter(a->a.getId()==id).findFirst().orElseThrow(()->new NonExistingItemException(id));
        try {
            SpecialArchive specialArchive = (SpecialArchive) archive;
            if(specialArchive.getOpened()==specialArchive.getMaxOpen()) {
                log.append(String.format("Item %d cannot be opened more than %d times\n", specialArchive.getId(),specialArchive.getMaxOpen() ));
            }else {
                log.append(String.format("Item %d opened at %s\n", specialArchive.getId(),date));
                specialArchive.setOpened(specialArchive.getOpened() + 1);
            }
        }catch (Exception e) {
            LockedArchive lockedArchive = (LockedArchive) archive;
            if (date.before(lockedArchive.getDateToOpen())){
                log.append(String.format("Item %d cannot be opened before %s\n", lockedArchive.getId(), lockedArchive.getDateToOpen()));
            }else {
                log.append(String.format("Item %d opened at %s\n", lockedArchive.getId(),date));
            }
        }
    }

    public String getLog(){
        return log.toString();
    }
}
