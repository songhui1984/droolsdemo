package com.jsptpd.rjy.zyj.main;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class SoftDrink {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            KnowledgeBase knowledgeBase = readKnowledgeBase();
            StatefulKnowledgeSession statefulKnowledgeSession = knowledgeBase
                    .newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger knowledgeRuntimeLogger = KnowledgeRuntimeLoggerFactory
                    .newFileLogger(statefulKnowledgeSession, "test");
            Customer customer=new Customer("小黄", 50, 0, 0);
            statefulKnowledgeSession.insert(customer);
            statefulKnowledgeSession.fireAllRules();
            knowledgeRuntimeLogger.close();
            statefulKnowledgeSession.dispose();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory
                .newKnowledgeBuilder();
        knowledgeBuilder.add(ResourceFactory
                .newClassPathResource("SoftDrink.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors knowledgeBuilderErrors = knowledgeBuilder
                .getErrors();
        if (knowledgeBuilderErrors.size() > 0) {
            for (KnowledgeBuilderError knowledgeBuilderError : knowledgeBuilderErrors) {
                System.out.println(knowledgeBuilderError);
            }
            throw new IllegalArgumentException("KnowledgeBuilder创建失败！");
        }

        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addKnowledgePackages(knowledgeBuilder
                .getKnowledgePackages());
        return knowledgeBase;
    }

    public static class Customer {
        private String name = "";
        private int money = 0;
        private int emptyBottle = 0;
        private int drinkBottleSum = 0;

        public Customer(String name, int money, int emptyBottle,
                        int drinkBottleSum) {
            super();
            this.name = name;
            this.money = money;
            this.emptyBottle = emptyBottle;
            this.drinkBottleSum = drinkBottleSum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getEmptyBottle() {
            return emptyBottle;
        }

        public void setEmptyBottle(int emptyBottle) {
            this.emptyBottle = emptyBottle;
        }

        public int getDrinkBottleSum() {
            return drinkBottleSum;
        }

        public void setDrinkBottleSum(int drinkBottleSum) {
            this.drinkBottleSum = drinkBottleSum;
        }
    }
}