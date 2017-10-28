package com.darkwinter.bookfilms;

import java.io.Serializable;

/**
 * Created by DarkWinter on 10/27/17.
 */

public class Cinemas implements Serializable {

        private String id;
        private String Address;
        private String Income;

        public Cinemas() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getIncome() {
            return Income;
        }

        public void setIncome(String income) {
            Income = income;
        }
}
