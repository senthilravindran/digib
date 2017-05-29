package api;

import static spark.Spark.get;

public class ProcessPayment {
	public static void main(String[] args) {
		// get("/processpayment", (req, res) ->
		// com.vp.payment.services.ProcessPayment.execute());

	

		get("/processpayment",
				(req, res) -> com.vp.payment.services.ExecuteINTLPayment.executePayment());
	}
}