package com.example.audit.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {

    @Autowired
    private UserRepository repository;

    public UserDao() {
    }
    public UserDao(UserRepository userRepository1) {
        this.repository = userRepository1;
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);



    public UserEntity insert(UserEntity entity) {
        LOGGER.info("Entity Saved");
//        return repository.save(entity);
        return repository.save(entity);
    }

    public List<UserEntity> getAll() {
        LOGGER.info("All data fetched");
        return repository.findAll();

    }

    public UserEntity insert1() {
        return repository.save(new UserEntity("Vaishnavy", "Upadhyay", 23, "admin"));
    }


}
