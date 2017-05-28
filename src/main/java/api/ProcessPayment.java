package api;

import static spark.Spark.*;

import com.vp.payment.entities.PaymentMessage;
import com.vp.payment.entities.PaymentReceiver;
import com.vp.payment.entities.PaymentSender;

public class ProcessPayment {
	public static void main(String[] args) {
		// get("/processpayment", (req, res) ->
		// com.vp.payment.services.ProcessPayment.execute());

	

		get("/processpayment",
				(req, res) -> com.vp.payment.services.ExecuteINTLPayment.executePayment());
	}
}