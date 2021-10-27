package com.bca.adam.model;

import java.util.UUID;

// @Entity
// @Table(name = "users")
// @Data
// @EqualsAndHashCode
// @ToString
// @NoArgsConstructor
public class User {

    // @Id
    // @GeneratedValue(generator = "UUID")
    // @Type(type = "pg-uuid")
    // @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    // @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // @Column(name = "username", nullable = false)
    private String username;

    // @Column(name = "name", nullable = false)
    private String name;

    // @Column(name = "hash_password", nullable = false)
    private String password;

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
}
