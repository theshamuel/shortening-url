package com.theshamuel.shrturl.commons.base.dao.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.theshamuel.shrturl.commons.base.dao.BaseRepositoryTest;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;


public abstract class BaseRepositoryImplTest implements BaseRepositoryTest {


    private static final String DB_NAME = "shrturlDBTest";
    protected static MongoCollection collection;
    private static MongoClient mongo;
    protected static MongoTemplate template;
    private static MongodForTestsFactory factory = null;
    private static MongoDatabase db;

    @BeforeClass
    public static void initDb() throws IOException {
        factory = MongodForTestsFactory.with(Version.Main.PRODUCTION);
        mongo = factory.newMongo();
        db = mongo.getDatabase(DB_NAME);
    }

    public static void initCollection(String collectionName) {
        collection = db.getCollection(collectionName);
    }

    @AfterClass
    public static void closeDb() {
        if (factory != null)
            factory.shutdown();
    }

    @Before
    public void setUp() throws Exception {
        template = new MongoTemplate(mongo, DB_NAME);
        setMongo();
    }


}
