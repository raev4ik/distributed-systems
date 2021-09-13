package DistributedMap;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.io.Serializable;

public class HazelcastClientNodeDMPessLock {
    public static void main( String[] args ) throws Exception {
        HazelcastInstance hz = HazelcastClient.newHazelcastClient();
        IMap<String, Value> map = hz.getMap( "mapPessLock" );
        String key = "1";
        if (map.get(key) == null) {map.put( key, new Value());}
        System.out.println( "Starting" );
        for ( int k = 0; k < 1000; k++ ) {
            map.lock( key );
            try {
                Value value = map.get( key );
                Thread.sleep( 10 );
                value.amount++;
                map.put( key, value );
            } finally {
                map.unlock( key );
            }
        }
        System.out.println( "Finished! Result = " + map.get( key ).amount );
        hz.shutdown();
    }

    static class Value implements Serializable {
        public int amount;
    }
}
