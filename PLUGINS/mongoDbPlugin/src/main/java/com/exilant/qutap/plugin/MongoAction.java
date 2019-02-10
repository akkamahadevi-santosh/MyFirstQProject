 package com.exilant.qutap.plugin;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoAction {

	static MongoClient mongo;
	static DB db;
	static DBCollection col;
	static BasicDBObject document;
	String mesg;

	public String mongoconnection(String host, int port) throws UnknownHostException {

		mongo = new MongoClient(host, port);		
		mongo.addOption(10000);
		mesg = host+"/ "+port;
		return mesg;
	}

	public String getDB(String dbName)  {
		db = mongo.getDB(dbName);
		mesg = "" + db;
		return mesg;
	}

	public String getCollection(String collection) {

		col = db.getCollection(collection);
		String mesg = "" + col;
		return mesg;

	}

	public long insert(String key, String value) {
		mongo.addOption(10000);
		document = new BasicDBObject();
		document.put(key, value);
		document.put("createdDate", new Date());
		col.insert(document);

		long count = col.getCount();
		return count;

	}

	public List<String> search(String key, String value) {
		
		mongo.addOption(10000);
		
		List<String> searchedval = new ArrayList<String>();
		BasicDBObject allQuery = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject();
		allQuery.put(key, value);
		fields.put(key, value);

		DBCursor cursor = col.find(fields, allQuery);
		while (cursor.hasNext()) {
			searchedval.add(cursor.next().toString());
		}
		return searchedval;

	}

	public List<String> update(String oldval, String newval) {
		
		mongo.addOption(10000);
		
		List<String> updatedval = new ArrayList<String>();

		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.append("$set", new BasicDBObject().append(oldval, newval));

		BasicDBObject searchQuery = new BasicDBObject();
		
        
		col.updateMulti(searchQuery, updateQuery).toString();

		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			updatedval.add(cursor.next().toString());
		}
		return updatedval;

	}

	public String close() {
		
		try {

			mongo.close();
			mesg="Connection Terminated";

		} catch (Exception e) {
			mesg="Connection not terminated";
			e.printStackTrace();

		}
		return mesg;
	}

}
