package com.xkdgx.ljh.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;

import javax.jms.*;

public class ActiveMQ {
    /**
     * 生产者
     */
    @Test
    public void testProducer() throws JMSException {
        //java连接activeMQ  ip
        String brokeURL = "tcp://39.98.81.138:61616";

        //1.创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokeURL);

        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();

        //3.创建会话
        /*
         *  @Param (boolean)b : 代表第二个参数是否生效开启事务
         *  @Param(int)i :  自动发送回执
         * */
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //4.创建生产者
        Destination destination = new ActiveMQQueue("javaQueue");
        MessageProducer producer = session.createProducer(destination);

        //5.创建消息
        TextMessage textMessage = session.createTextMessage("hello world 554 world");

        //6.使用生产者发送消息
        producer.send(textMessage);

        //7.提交
        session.commit();

        //8,关闭
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 消费者1
     */
    @Test
    public void testConsumer() throws JMSException {
        //activeMQ连接java ip
        String brokerURL = "tcp://39.98.81.138:61616";

        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);

        //2.创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动连接
        connection.start();

        //3.创建会话
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //4.创建消费者
        //Destination destination = new ActiveMQQueue("myFirst_javaQueue");
        Destination destination = new ActiveMQQueue("javaQueue");
        MessageConsumer consumer = session.createConsumer(destination);

        //5.使用消费者接收消息
        TextMessage message = (TextMessage) consumer.receive();
        String msg = message.getText();
        System.out.println("队列消费消息:" + msg);

        //6.提交
        session.commit();

        //7.关闭
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * 消费者2
     */
    @Test
    public void testConsumer1() throws JMSException {
        //activeMQ连接java ip
        String brokerURL = "tcp://39.98.81.138:61616";

        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);

        //2.创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动连接
        connection.start();

        //3.创建会话
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //4.创建消费者
        //Destination destination = new ActiveMQQueue("myFirst_javaQueue");
        Destination destination = new ActiveMQQueue("javaQueue");
        MessageConsumer consumer = session.createConsumer(destination);

        while (true) {
            //5.使用消费者接收消息
            TextMessage message = (TextMessage) consumer.receive();
            if (message != null) {
                String msg = message.getText();
                System.out.println("队列消费消息:" + msg);

                //6.提交
                session.commit();
            } else {
                break;
            }
        }
    }

    /**
     * 发布者
     */
    @Test
    public void testPublish() throws JMSException {
        //java连接activeMQ  ip
        String broker = "tcp://39.98.81.138:61616";

        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(broker);

        //2.创建连接
        TopicConnection topicConnection = activeMQConnectionFactory.createTopicConnection();
        //启动连接
        topicConnection.start();

        //3.创建会话
        TopicSession topicSession = topicConnection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);

        //4.创建发布者
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("javaTopic");
        TopicPublisher publisher = topicSession.createPublisher(activeMQTopic);

        //5.创建消息
        /*----------------------------------*/
        /*          ObjectMessage           */
        /*----------------------------------*/
        //ObjectMessage objectMessage = topicSession.createObjectMessage(new Student("1","554",20,new Date()));


        /*----------------------------------*/
        /*            MapMessage            */
        /*----------------------------------*/
        MapMessage mapMessage = topicSession.createMapMessage();
        mapMessage.setBoolean("sex", true);
        mapMessage.setChar("c", 'A');
        mapMessage.setString("str", "L-JiaHui");
        mapMessage.setBytes("byte", new byte[12]);
        //6.使用发布者发送消息
        publisher.send(mapMessage);

        //7.提交
        topicSession.commit();

        //8.关闭
        publisher.close();
        topicSession.close();
        topicConnection.close();
    }

    /**
     * 订阅者1
     */
    @Test
    public void testSubscribe() throws JMSException {
        //java 连接activeMQ  ip
        String broker = "tcp://39.98.81.138:61616";
        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(broker);

        //2.创建连接
        TopicConnection topicConnection = activeMQConnectionFactory.createTopicConnection();
        //启动连接
        topicConnection.start();

        //3.创建会话
        TopicSession topicSession = topicConnection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);

        //4.创建订阅者
        ActiveMQTopic topic = new ActiveMQTopic("javaTopic");
        TopicSubscriber subscriber = topicSession.createSubscriber(topic);

        while (true) {
            //5.使用订阅者接收消息
            /*----------------------------------*/
            /*          ObjectMessage           */
            /*----------------------------------*/
            //ObjectMessage message = (ObjectMessage) subscriber.receive();

            /*----------------------------------*/
            /*            MapMessage            */
            /*----------------------------------*/
            MapMessage message = (MapMessage) subscriber.receive();
            if (message != null) {
                System.out.println(message.getBoolean("sex"));
                System.out.println(message.getChar("c"));
                System.out.println(message.getString("str"));
                System.out.println(message.getBytes("byte").toString());
                //System.out.println("订阅者1"+student);

                //6.提交
                topicSession.commit();
            } else {
                break;
            }

        }

        //7.关闭
        /*
        subscriber.close();
        topicSession.close();
        topicConnection.close();
        */
    }

    /**
     * 订阅者2
     */
    @Test
    public void testSubscribes() throws JMSException {
        //java 连接activeMQ  ip
        String broker = "tcp://39.98.81.138:61616";
        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(broker);

        //2.创建连接
        TopicConnection topicConnection = activeMQConnectionFactory.createTopicConnection();
        //启动连接
        topicConnection.start();

        //3.创建会话
        TopicSession topicSession = topicConnection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);

        //4.创建订阅者
        ActiveMQTopic topic = new ActiveMQTopic("javaTopic");
        TopicSubscriber subscriber = topicSession.createSubscriber(topic);

        while (true) {
            //5.使用订阅者接收消息
            /*----------------------------------*/
            /*          ObjectMessage           */
            /*----------------------------------*/
            //ObjectMessage message = (ObjectMessage) subscriber.receive();

            /*----------------------------------*/
            /*            MapMessage            */
            /*----------------------------------*/
            MapMessage message = (MapMessage) subscriber.receive();
            if (message != null) {
                System.out.println(message.getBoolean("sex"));
                System.out.println(message.getChar("c"));
                System.out.println(message.getString("str"));
                System.out.println(message.getBytes("byte").toString());

                //6.提交
                topicSession.commit();
            } else {
                break;
            }

        }

        //7.关闭
        /*
        subscriber.close();
        topicSession.close();
        topicConnection.close();
        */
    }
}