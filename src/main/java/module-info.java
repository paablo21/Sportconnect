module org.example.sportconnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.mariadb.jdbc;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.example.sportconnect to javafx.fxml, org.hibernate.orm.core;
    exports org.example.sportconnect;

    exports org.example.sportconnect.controller;
    opens org.example.sportconnect.controller to javafx.fxml, org.hibernate.orm.core;

    exports org.example.sportconnect.dao;
    opens org.example.sportconnect.dao to javafx.fxml, org.hibernate.orm.core;

    exports org.example.sportconnect.model;
    opens org.example.sportconnect.model to javafx.fxml, org.hibernate.orm.core;

    exports org.example.sportconnect.service;
    opens org.example.sportconnect.service to javafx.fxml, org.hibernate.orm.core;

    exports org.example.sportconnect.util;
    opens org.example.sportconnect.util to javafx.fxml, org.hibernate.orm.core;
}
