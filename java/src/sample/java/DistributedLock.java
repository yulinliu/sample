package sample.java;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.jgroups.JChannel;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.blocks.locking.LockService;

public class DistributedLock {

	private static Logger logger = Logger.getLogger(DistributedLock.class);
	
	public static void main(String[] args) {
		String channelName = "Sample";
		String lockName = "dk";
		
		try {
			JChannel channel = new JChannel("jgroups-lock.xml");
			
			ClusterMembershipListener listener = new ClusterMembershipListener();
			RpcDispatcher dispatcher = new RpcDispatcher(channel, null, listener, null);
			
			LockService lock_service = new LockService(channel);
			Lock lock = lock_service.getLock(lockName);
			
			channel.connect(channelName);
			
			while(true){
				if(lock.tryLock(3, TimeUnit.SECONDS)){
					lock.lock();
					
					logger.info(channel.getAddressAsString()+" Distributed Lock Now");
					
					Thread.sleep(3000);
					lock.unlock();
					
				}else{
					logger.info(channel.getAddressAsString()+" Can't Lock Now");
					Thread.sleep(1000);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
