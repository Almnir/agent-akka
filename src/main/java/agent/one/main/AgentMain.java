package agent.one.main;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.typesafe.config.ConfigFactory;

/**
 * Created by alm on 21.06.14.
 */

class AgentMain {

    public static class Agent extends UntypedActor {

        public void onReceive(Object message) {
            if (message instanceof MessageClass) {
                System.out.println("hello: " + ((MessageClass) message).message);
            } else if (message instanceof CommandClass) {
                switch ((CommandClass) message) {
                    case STOP:
                        getContext().stop(getSelf());
                    default:
                }
            }
            else unhandled(message);
        }
    }

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("agentsystem", ConfigFactory.load("agent"));
//        final String path = "akka.tcp://agentsystem@127.0.0.1:5150/user/agent";
        final ActorRef agent = system.actorOf(Props.create(Agent.class), "agent");
        agent.tell(new MessageClass("Agent!"), ActorRef.noSender());

   }
}