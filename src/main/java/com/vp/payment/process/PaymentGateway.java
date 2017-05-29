package com.vp.payment.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.vp.payment.entities.PaymentMessage;

public class PaymentGateway {

	public String insertPayment(PaymentMessage msg) {
		Gson gson = new Gson();
		String returnMsg = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		//	return false;
		}
		Connection connection = null;
		String filePath = null;
		String paymentId = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/smartbankv2db", " ",
					" ");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
	//		return false;
		}

		if (connection != null) {
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			long millis = timestamp.getTime();
			paymentId = "PAY" + millis ;
	
			
			try {
				filePath = "/tmp/payment" + millis + ".ser";
				FileOutputStream fileOut = new FileOutputStream(filePath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(msg);
				out.close();
				fileOut.close();
			} catch (IOException e) {
				System.out.println("File Write Failed");
			}

			try {

				InputStream inputStream = new FileInputStream(new File(filePath));
				java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				String sql = "INSERT INTO payment_messages (payment_id, payment_msg,inserted_date, source_channel) values (?, ?, ?,?)";
				
				PreparedStatement statement = connection.prepareStatement(sql);
				
				statement.setString(1, paymentId);
				statement.setBlob(2, inputStream);
				statement.setTimestamp(3, date);
				statement.setString(4, "mobile");

				int row = statement.executeUpdate();
				if (row > 0) {
					System.out.println("Payment Inserted");
					returnMsg = gson.toJson(msg);
				}
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} else {
			System.out.println("Failed to make connection!");
		}

		return returnMsg;
	}

	public static void main(String[] argv) {

	}

}
