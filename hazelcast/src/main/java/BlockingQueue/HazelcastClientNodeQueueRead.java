package BlockingQueue;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastClientNodeQueueRead {
    public static void main( String[] args ) throws Exception {
        HazelcastInstance hz = HazelcastClient.newHazelcastClient();
        IQueue<Integer> queue = hz.getQueue( "queue" );
        while ( true ) {
            int item = queue.take();
            System.out.println( "Consumed: " + item );
            if ( item == -1 ) {
                queue.put( -1 );
                break;
            }
            Thread.sleep( 5000 );
        }
        System.out.println( "Consumer Finished!" );
        hz.shutdown();
    }
}
