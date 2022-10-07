package com.Project1.models;

import java.util.Calendar;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@NoArgsConstructor
@Table
public class VerificationToken {
    private static final int EXPIRATION_TIME = 10;

    @PrimaryKey@CassandraType(type = CassandraType.Name.INT)
    private int id;
    @CassandraType(type = CassandraType.Name.TEXT)
    private String token;
    @CassandraType(type = CassandraType.Name.DATE)
    private Date expirationTime;

    /** @Transient prevents this user object from mapping */
    @Transient
    private User user;
    public VerificationToken(User user, String token) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }
    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
