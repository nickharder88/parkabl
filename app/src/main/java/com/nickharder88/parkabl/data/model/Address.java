package com.nickharder88.parkabl.data.model;

import com.nickharder88.parkabl.data.dto.AddressDTO;

public class Address {
        public static String collection = "addresses";

        public String id;
        public AddressDTO data;

        public Address(String id, AddressDTO data) {
            this.id = id;
            this.data = data;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            boolean comma = false;
            if (data.houseNum != null) {
                if (data.street != null) {
                    comma = true;
                    sb.append(data.houseNum);
                    sb.append(" ");
                    sb.append(data.street);
                }
            } else if (data.apartmentNum != null) {
                if (data.street != null) {
                    comma = true;
                    sb.append(data.apartmentNum);
                    sb.append(" ");
                    sb.append(data.street);
                }
            } else if (data.street != null) {
                comma = true;
                sb.append(data.street);
            }

            if (data.city != null) {
                if (comma) {
                    sb.append(", ");
                }

                comma = true;
                sb.append(data.city);
            }

            if (data.state != null) {
                if (comma) {
                    sb.append(", ");
                }
                comma = true;

                if (data.postal != null) {
                    sb.append(data.state);
                    sb.append(" ");
                    sb.append(data.postal);
                } else {
                    sb.append(data.state);
                }
            }

            if (data.country != null) {
                if (comma) {
                    sb.append(", ");
                }

                sb.append(data.country);
            }

            return sb.toString();
        }
}
