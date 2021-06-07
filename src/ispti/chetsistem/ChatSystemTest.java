package ispti.chetsistem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

class NoSuchRoomException extends RuntimeException{
    NoSuchRoomException(String roomName){
        System.out.printf("%s not found!!", roomName);
    }
}

class NoSuchUserException extends RuntimeException{

    NoSuchUserException(String userName){
        super(String.format("%s not found!!",userName));
    }

}

class User{
    private String username;

    public User(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
}

class ChatRoom{

    private String chatRoomName;
    private List<User> userList;

    public ChatRoom(String name){
        this.chatRoomName=name;
        this.userList = new ArrayList<>();
    }

    public void addUser(String username){
        this.userList.add(new User(username));
    }

    public void addUser(User user){
        this.userList.add(user);
    }

    public void removeUser(String username){
        if(!this.hasUser(username)){
            throw new NoSuchUserException(username);
        }
    }

    public boolean hasUser(String username){
        return this.userList.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public int numUsers(){
        return this.userList.size();
    }

    public List<User> getUserList(){
        return this.userList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(chatRoomName+"\n");
        if(this.userList.isEmpty()) sb.append("EMPTY");
        this.userList.forEach(user -> sb.append(user.getUsername()).append("\n"));
        return sb.toString();
    }
}

class ChatSystem{
    private Map<String, ChatRoom> chatRoomMap;
    private List<User> userList;

    public ChatSystem(){
        this.chatRoomMap = new TreeMap<>();
        this.userList = new ArrayList<>();
    }

    public void addRoom(String roomName){
        this.chatRoomMap.putIfAbsent(roomName,new ChatRoom(roomName));
    }

    public void removeRoom(String roomName){
        this.chatRoomMap.remove(roomName);
    }

    public ChatRoom getRoom(String roomName){
        if(this.chatRoomMap.containsKey(roomName)){
            return this.chatRoomMap.get(roomName);
        }else throw new NoSuchRoomException(roomName);
    }

    public void register(String userName){

        //to do
    }

    public void registerAndJoin(String userName, String roomName) {
        User user = new User(userName);
        this.userList.add(user);
        this.getRoom(roomName).addUser(user);

    }

    public void joinRoom(String userName, String roomName){
        if(this.chatRoomMap.containsKey(roomName)){
            this.chatRoomMap.get(roomName).addUser(userName);
        }
    }

    public void leaveRoom(String username, String roomName){
        if(this.chatRoomMap.containsKey(roomName)){
            this.chatRoomMap.get(roomName).removeUser(username);
            this.userList.removeIf(user -> user.getUsername().equals(username));
        }
    }

}

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs,params);
                    }
                }
            }
        }
    }

}
