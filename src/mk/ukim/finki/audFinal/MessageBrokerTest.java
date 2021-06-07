package mk.ukim.finki.audFinal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class PartitionDoesNotExistException extends RuntimeException{
    public PartitionDoesNotExistException(String topic, Integer partition){
        super(String.format("The topic %s does not have a partition with number %d", topic, partition));
    }
}

class Message implements Comparable<Message>{
    private final LocalDateTime timestamp;
    private final String message;
    private Integer partition;
    private final String key;

    public Message(LocalDateTime timestamp, String message, Integer partition, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.partition = partition;
        this.key = key;
    }

    public Message(LocalDateTime timestamp, String message, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.key = key;
    }

    @Override
    public String toString() {
        return "Message{" + "timestamp=" + timestamp + ", message='" + message + '\'' + '}';
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Integer getPartition() {
        return partition;
    }

    public String getKey() {
        return key;
    }

    @Override
    public int compareTo(Message o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}

class Topic{
    private final String topicName;
    private Integer partitionsCount;
    private Map<Integer, TreeSet<Message>> messagesByPartition;

    public Topic(String topicName, Integer partitionsCount) {
        this.topicName = topicName;
        this.partitionsCount = partitionsCount;
        this.messagesByPartition = new TreeMap<>();
        IntStream.range(1,partitionsCount+1)
                .forEach(i->this.messagesByPartition.put(i, new TreeSet<>()));
    }

    public void addMessage(Message message){
        Integer messagePartition = message.getPartition();
        if(messagePartition==null){
            messagePartition = Math.abs(message.getKey().hashCode() % partitionsCount) + 1;
        }

        if(!messagesByPartition.containsKey(messagePartition))
            throw new PartitionDoesNotExistException(this.topicName, messagePartition);

        this.messagesByPartition.computeIfPresent(messagePartition, (key, value) -> {
            if(value.size() == MessageBroker.capacityPerTopic)
                value.remove(value.first());
            value.add(message);
            return value;
        });
    }

    public void changeNumberOfPartition(int newPartitionNumber){
        if(newPartitionNumber<partitionsCount) throw new UnsupportedOperationException("TEST TEST");

        int difference = newPartitionNumber - this.partitionsCount;
        int size = this.messagesByPartition.size();

        IntStream.range(1, difference+1)
                .forEach(i -> this.messagesByPartition.putIfAbsent(size + i,new TreeSet<>()));
        this.partitionsCount = newPartitionNumber;
    }

    @Override
    public String toString() {
        return String.format("Topic: %10s Partitions: %5d\n%s",
                this.topicName,
                this.partitionsCount,
                this.messagesByPartition.entrySet()
                        .stream()
                        .map(entry-> String.format(" %d : Count of messages: %5d\n%s",
                                entry.getKey(),
                                entry.getValue().size(),
                                !entry.getValue().isEmpty() ? "Messages:\n" + entry.getValue()
                                                            .stream().map(Message::toString)
                                                            .collect(Collectors.joining("\n"))
                                                            : "")
                        ).collect(Collectors.joining("\n")));
    }
}

class MessageBroker{
    private static LocalDateTime minimumDate;
    public static Integer capacityPerTopic;
    private Map<String, Topic> topicMap;

    public MessageBroker(LocalDateTime minimumDate, Integer capacityPerTopic) {
        MessageBroker.minimumDate = minimumDate;
        MessageBroker.capacityPerTopic = capacityPerTopic;
        this.topicMap = new TreeMap<>();
    }

    public void addTopic(String topic, int partitionsCounts){
        this.topicMap.put(topic, new Topic(topic, partitionsCounts));
    }

    public void addMessage(String topic, Message message){
        if(message.getTimestamp().isBefore(minimumDate)) return;
        this.topicMap.get(topic).addMessage(message);
    }

    public void changeTopicSettings(String topic, int partitionsCounts){
        this.topicMap.get(topic).changeNumberOfPartition(partitionsCounts);
    }

    @Override
    public String toString() {
        return String.format("Broker with %2d topics:\n%s",
                this.topicMap.size(),
                this.topicMap.values()
                        .stream()
                        .map(Topic::toString)
                        .collect(Collectors.joining("\n")));
    }
}

class MessageBrokersTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String date = sc.nextLine();
        LocalDateTime localDateTime =LocalDateTime.parse(date);
        Integer partitionsLimit = Integer.parseInt(sc.nextLine());
        MessageBroker broker = new MessageBroker(localDateTime, partitionsLimit);
        int topicsCount = Integer.parseInt(sc.nextLine());

        //Adding topics
        for (int i=0;i<topicsCount;i++) {
            String line = sc.nextLine();
            String [] parts = line.split(";");
            String topicName = parts[0];
            int partitionsCount = Integer.parseInt(parts[1]);
            broker.addTopic(topicName, partitionsCount);
        }

        //Reading messages
        int messagesCount = Integer.parseInt(sc.nextLine());

        System.out.println("===ADDING MESSAGES TO TOPICS===");
        for (int i=0;i<messagesCount;i++) {
            String line = sc.nextLine();
            String [] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length==4) {
                String key = parts[3];
                try {
                    broker.addMessage(topic, new Message(timestamp,message,key));
                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessage(topic, new Message(timestamp,message,partition,key));
                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER ADDITION OF MESSAGES===");
        System.out.println(broker);

        System.out.println("===CHANGE OF TOPICS CONFIGURATION===");
        //topics changes
        int changesCount = Integer.parseInt(sc.nextLine());
        for (int i=0;i<changesCount;i++){
            String line = sc.nextLine();
            String [] parts = line.split(";");
            String topicName = parts[0];
            Integer partitions = Integer.parseInt(parts[1]);
            try {
                broker.changeTopicSettings(topicName, partitions);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("===ADDING NEW MESSAGES TO TOPICS===");
        messagesCount = Integer.parseInt(sc.nextLine());
        for (int i=0;i<messagesCount;i++) {
            String line = sc.nextLine();
            String [] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length==4) {
                String key = parts[3];
                try {
                    broker.addMessage(topic, new Message(timestamp,message,key));
                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessage(topic, new Message(timestamp,message,partition,key));
                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER CONFIGURATION CHANGE===");
        System.out.println(broker);


    }
}
