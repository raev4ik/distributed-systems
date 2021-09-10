package DistributedMap;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.io.Serializable;

public class HazelcastClientNodeDMNoLock {
    public static void main( String[] args ) throws Exception {
        HazelcastInstance hz = HazelcastClient.newHazelcastClient();
        IMap<String, Value> map = hz.getMap( "mapNoLock" );
        String key = "1";
        map.put( key, new Value() );
        System.out.println( "Starting" );
        for ( int k = 0; k < 1000; k++ ) {
            if ( k % 100 == 0 ) System.out.println( "At: " + k );
            Value value = map.get( key );
            Thread.sleep( 10 );
            value.amount++;
            map.put( key, value );
        }
        System.out.println( "Finished! Result = " + map.get(key).amount );
        hz.shutdown();
    }

    static class Value implements Serializable {
        public int amount;
    }
}
