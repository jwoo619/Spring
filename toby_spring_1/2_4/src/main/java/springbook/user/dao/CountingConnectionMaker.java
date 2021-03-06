package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class CountingConnectionMaker implements ConnectionMaker {
	int counter = 0;
	private DataSource dataSource;
	
	public void CountingConnectionMaker(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public Connection makeConnection() throws ClassNotFoundException , SQLException{
		this.counter++;
		return dataSource.getConnection();
	}
	
	public int getCounter(){
		return this.counter;
	}

}
