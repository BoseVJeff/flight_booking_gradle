module io.github.bosev.flight_booking_gradle {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires net.synedra.validatorfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.bootstrapfx.core;
	requires com.oracle.database.jdbc;
	requires java.sql;
	requires java.naming;

	opens io.github.bosev.flight_booking_gradle to javafx.fxml;
	exports io.github.bosev.flight_booking_gradle;
}