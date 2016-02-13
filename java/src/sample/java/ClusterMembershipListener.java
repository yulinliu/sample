package sample.java;

import org.apache.log4j.Logger;
import org.jgroups.Address;
import org.jgroups.MembershipListener;
import org.jgroups.View;

public class ClusterMembershipListener implements MembershipListener {

	private static Logger logger = Logger.getLogger(ClusterMembershipListener.class);
	
	@Override
	public void block() {
		logger.info("block");
	}

	@Override
	public void suspect(Address address) {
		logger.info("suspect: "+address.toString());
	}

	@Override
	public void unblock() {
		logger.info("unblock");
	}

	@Override
	public void viewAccepted(View view) {
		logger.info("members: "+view.getMembers());
	}
}
