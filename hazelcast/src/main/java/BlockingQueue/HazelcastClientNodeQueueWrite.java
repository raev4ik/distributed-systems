package BlockingQueue;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastClientNodeQueueWrite {
    public static void main( String[] args ) throws Exception {
        HazelcastInstance hz = HazelcastClient.newHazelcastClient();
        IQueue<Integer> queue = hz.getQueue( "queue" );
        for ( int k = 1; k < 100; k++ ) {
            queue.put( k );
            System.out.println( "Producing: " + k );
            Thread.sleep(1000);
        }
        queue.put( -1 );
        System.out.println( "Producer Finished!" );
        hz.shutdown();
    }
}
